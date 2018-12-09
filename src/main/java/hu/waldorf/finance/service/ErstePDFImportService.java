package hu.waldorf.finance.service;

import hu.waldorf.finance.model.Befizetes;
import hu.waldorf.finance.model.BefizetesRepository;
import hu.waldorf.finance.model.FeldolgozasStatusza;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class ErstePDFImportService {
    private static final String STARTS_WITH_DATE_REGEX ="^\\d{4}\\.(((0)[0-9])|((1)[0-2]))\\.([0-2][0-9]|(3)[0-1])\\..*$";
    private static final String DATE_REGEX ="^\\d{4}\\.(((0)[0-9])|((1)[0-2]))\\.([0-2][0-9]|(3)[0-1])\\.*";
    private static final Pattern DATE_REGEX_PATTERN = Pattern.compile(DATE_REGEX);
    private static final String NAME_REGEX="^[a-zA-ZáéűúőóüöíÁÉŰÚŐÓÜÖÍ|\\-|\\s]*";
    private static final Pattern NAME_REGEX_PATTERN=Pattern.compile(NAME_REGEX);
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd.");
    private static final List<String> CONTENT_COMING_INDICATOR_LINES=Arrays.asList(
            "Tranzakció típusa 00100 BEJÖVŐ GIRO ÁTUTALÁS",
            "Tranzakció típusa BANKON BELÜLI ÁTUT. JÓVÁÍRÁSA",
            "Tranzakció típusa IB UTALÁS BANKON KÍVÜL"
    );

    @Autowired
    private BefizetesRepository befizetesRepository;

    public String importErsteDataFile(MultipartFile file) throws Exception {
        PDDocument document = PDDocument.load(file.getBytes());
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text=pdfStripper.getText(document);
        document.close();

        String[] lines = text.split("\n");

        int startLine=0;
        for (String line : lines) {
            startLine++;
            if(line.trim().startsWith("Értéknap Partner / Megjegyzés Összeg")){
                startLine++;
                break;
            }
        }

        if(startLine==0){
            return "invalidpdf";
        }

        String[] toBeParsed=Arrays.copyOfRange(lines, startLine-1,lines.length);
        List<Befizetes> befizetesek=process(toBeParsed, file.getOriginalFilename());
        //befizetesek.forEach(befizetes -> befizetesRepository.save(befizetes));
        return null;
    }

    private enum ProcessStage {START, HEADINGS_PARSING, CONTENT_PARSING_STARTED, CONTENT_PARSING}

    private List<Befizetes> process(String[] toBeParsed, String originalFilename) throws ParseException {
        List<Befizetes> parsed=new ArrayList<>();
        List<Befizetes> befizetesBuffer=new ArrayList<>();
        ProcessStage stage=ProcessStage.START;



        int contentIndex=0;

        for (String line : toBeParsed) {
            if(line.matches(STARTS_WITH_DATE_REGEX)){ // new heading stack starts
                if(stage==ProcessStage.CONTENT_PARSING){ // content is parsed for the prev headings/content, new heading blocks coming
                    parsed.addAll(befizetesBuffer);
                    befizetesBuffer.clear();
                    contentIndex=0;
                }

                stage=ProcessStage.HEADINGS_PARSING;
                Befizetes befizetesBasic = getBefizetesBasicFromHeading(line, originalFilename);
                befizetesBuffer.add(befizetesBasic);
            }
            else if(isContentComingIndicatorLine(line)) { // heading ends, info for the headings coming
                if(stage==ProcessStage.CONTENT_PARSING){ // a new content block starting
                    contentIndex++;
                }
                stage=ProcessStage.CONTENT_PARSING_STARTED;
            }
            else {
                Befizetes befizetes=befizetesBuffer.get(contentIndex);
                if(line.startsWith("Megbízó számlaszáma")){
                    befizetes.setBefizetoSzamlaszam(line.replace("Megbízó számlaszáma","").trim());
                }
                else if(line.startsWith("Közlemény")){
                    befizetes.setKozlemeny(line.replace("Közlemény","").trim());
                }

                stage=ProcessStage.CONTENT_PARSING;
            }
        }

        return parsed;
    }

    private boolean isContentComingIndicatorLine(String line){
        for (String contentComingIndicatorLine : CONTENT_COMING_INDICATOR_LINES) {
            if(line.startsWith(contentComingIndicatorLine)){
                return true;
            }
        }

        return false;
    }

    private Befizetes getBefizetesBasicFromHeading(String line, String originalFilename) throws ParseException {
        // heading is like: 2018.09.03. JOHN DOE 33 000,00 HUF
        Matcher dateMatcher=DATE_REGEX_PATTERN.matcher(line);
        if(!dateMatcher.find()){
            throw new RuntimeException("Invalid heading line (no date available): "+line);
        }

        String date=dateMatcher.group(0);
        String nameAndMoney=line.replace(date,"").trim();

        Matcher nameMatcher=NAME_REGEX_PATTERN.matcher(nameAndMoney);
        if(!nameMatcher.find()){
            throw new RuntimeException("Invalid heading line (no name available): "+line);
        }

        String name=nameMatcher.group(0).trim();
        String moneyString=nameAndMoney.replace(name,"").replace("HUF","").replace(",",".").replace(" ","").trim();
        int osszeg=Float.valueOf(moneyString).intValue();

        Date now = new Date();
        Befizetes befizetes = new Befizetes();
        befizetes.setImportForras("Erste/" + originalFilename);
        befizetes.setImportIdopont(now);
        befizetes.setKonyvelesiNap(SIMPLE_DATE_FORMAT.parse(date));
        befizetes.setBefizetoNev(name);
        befizetes.setBefizetoSzamlaszam("?"); // will be updated on-demand
        befizetes.setOsszeg(osszeg);
        befizetes.setKozlemeny("?"); // will be updated on-demand
        befizetes.setStatusz(FeldolgozasStatusza.BEIMPORTALVA);

        return befizetes;
    }
}
