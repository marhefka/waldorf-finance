package hu.waldorf.finance.service;

import hu.waldorf.finance.model.Befizetes;
import hu.waldorf.finance.model.BefizetesRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class ErstePDFImportService {
    // Matches for string that starts with YYYY.MM.DD.
    private static final String STARTS_WITH_DATE_REGEX ="^\\d{4}\\.(((0)[0-9])|((1)[0-2]))\\.([0-2][0-9]|(3)[0-1])\\..*$";
    // Matches for string that is YYYY.MM.DD.
    private static final String DATE_REGEX ="^\\d{4}\\.(((0)[0-9])|((1)[0-2]))\\.([0-2][0-9]|(3)[0-1])\\.$";
    // Matches for Strings
    private static final String NAME_REGEX="^[a-zA-Zá-Ő|\\s]*";

    @Autowired
    private BefizetesRepository befizetesRepository;

    public void importErsteDataFile(MultipartFile file) throws Exception {
        PDDocument document = PDDocument.load(file.getBytes());
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text=pdfStripper.getText(document);
        document.close();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date now = new Date();

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
            // TODO ERROR
        }

        String[] toBeParsed=Arrays.copyOfRange(lines, startLine-1,lines.length);
        process(toBeParsed);

        /*for (int i = 0; i < nodeList.getLength(); i++) {
            Node row = nodeList.item(i);

            XPathExpression expr2 = xPathfactory.newXPath().compile("Cell");
            NodeList nodeList2 = (NodeList) expr2.evaluate(row, XPathConstants.NODESET);

            String konyvelesDatuma = nodeList2.item(1).getTextContent();
            String partnerNeve = nodeList2.item(4).getTextContent().trim();
            String partnerSzamlaszama = nodeList2.item(5).getTextContent().trim();
            int osszeg = Integer.parseInt(nodeList2.item(6).getTextContent().trim());
            String terhelesVagyJovairas = nodeList2.item(7).getTextContent().trim();
            String kozlemeny = nodeList2.item(9).getTextContent().trim();

            if (!terhelesVagyJovairas.equals("J")) {
                continue;
            }

            Date dKonyvelesDatuma = simpleDateFormat.parse(konyvelesDatuma);

            Befizetes befizetes = new Befizetes();
            befizetes.setImportForras("Erste/" + file.getOriginalFilename());
            befizetes.setImportIdopont(now);
            befizetes.setKonyvelesiNap(dKonyvelesDatuma);
            befizetes.setBefizetoNev(partnerNeve);
            befizetes.setBefizetoSzamlaszam(partnerSzamlaszama);
            befizetes.setOsszeg(osszeg);
            befizetes.setKozlemeny(kozlemeny);
            befizetes.setStatusz(FeldolgozasStatusza.BEIMPORTALVA);
            befizetesRepository.save(befizetes);
        }*/
    }

    private void process(String[] toBeParsed) {
        Map<String, Befizetes> befizetesBuffer=new HashMap<>();

        for (String line : toBeParsed) {
            if(line.matches(STARTS_WITH_DATE_REGEX)){
                String partnerNeve=getPartnerNevFromHeading(line);
            }
        }
    }

    private String getPartnerNevFromHeading(String line) {
        line=line.replaceAll(DATE_REGEX,"");
        return line;
    }
}
