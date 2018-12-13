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
import java.util.Objects;
import java.util.regex.Pattern;

@Service
@Transactional
public class ErstePDFImportService {
    private static final String STARTS_WITH_DATE_REGEX = "^\\d{4}\\.(((0)[0-9])|((1)[0-2]))\\.([0-2][0-9]|(3)[0-1])\\..*$"; // unused?
    private static final String DATE_REGEX = "^\\d{4}\\.(((0)[0-9])|((1)[0-2]))\\.([0-2][0-9]|(3)[0-1])\\.*";
    private static final Pattern DATE_REGEX_PATTERN = Pattern.compile(DATE_REGEX);          // unused?
    private static final String NAME_REGEX = "^[a-zA-ZáéűúőóüöíÁÉŰÚŐÓÜÖÍ|\\-|\\s]*";
    private static final Pattern NAME_REGEX_PATTERN = Pattern.compile(NAME_REGEX);          // unused?
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd.");
    private static final List<String> CONTENT_COMING_INDICATOR_LINES = Arrays.asList(           // unused?
            "Tranzakció típusa 00100 BEJÖVŐ GIRO ÁTUTALÁS",
            "Tranzakció típusa BANKON BELÜLI ÁTUT. JÓVÁÍRÁSA",
            "Tranzakció típusa IB UTALÁS BANKON KÍVÜL"
    );

    @Autowired
    private BefizetesRepository befizetesRepository;

    public ImportResult importErsteDataFile(MultipartFile file) throws Exception {
        PDDocument document = PDDocument.load(file.getBytes());
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        document.close();

        String[] lines = text.split("\n");

        int startLine = 0;
        for (String line : lines) {
            startLine++;
            if (line.trim().startsWith("Értéknap Partner / Megjegyzés Összeg")) {
                startLine++;
                break;
            }
        }

        if (startLine == 0) {
            return ImportResult.error("invalidpdf");
        }

        String[] toBeParsed = Arrays.copyOfRange(lines, startLine - 1, lines.length);
        List<Befizetes> befizetesek = process(toBeParsed, file.getOriginalFilename());
        befizetesek.forEach(befizetes -> befizetesRepository.save(befizetes));
        return ImportResult.success(befizetesek.size());
    }

    private List<Befizetes> process(String[] toBeParsed, String originalFilename) throws ParseException {
        List<Befizetes> parsed = new ArrayList<>();

        Befizetes befizetesBeingParsed = null;

        for (String line : toBeParsed) {
            if (line.startsWith("Megbízó neve")) {
                if (befizetesBeingParsed != null) {
                    parsed.add(befizetesBeingParsed);
                }

                befizetesBeingParsed = new Befizetes();
                befizetesBeingParsed.setImportForras("Erste/" + originalFilename);
                befizetesBeingParsed.setImportIdopont(new Date());
                befizetesBeingParsed.setStatusz(FeldolgozasStatusza.BEIMPORTALVA);
                befizetesBeingParsed.setBefizetoNev(line.replace("Megbízó neve", "").trim());
            } else if (line.startsWith("Megbízó számlaszáma")) {
                Objects.requireNonNull(befizetesBeingParsed).setBefizetoSzamlaszam(line.replace("Megbízó számlaszáma", "").trim());
            } else if (line.startsWith("Könyvelés dátuma")) {
                Objects.requireNonNull(befizetesBeingParsed).setKonyvelesiNap(SIMPLE_DATE_FORMAT.parse(line.replace("Könyvelés dátuma", "").trim()));
            } else if (line.startsWith("Közlemény")) {
                Objects.requireNonNull(befizetesBeingParsed).setKozlemeny(line.replace("Közlemény", "").trim());
            } else if (line.startsWith("Jóváírás összege")) {
                String moneyString = line.replace("Jóváírás összege", "").replace("HUF", "").replace(",", ".").replace(" ", "").trim();
                int osszeg = Float.valueOf(moneyString).intValue();
                Objects.requireNonNull(befizetesBeingParsed).setOsszeg(osszeg);
            }
        }

        return parsed;
    }
}
