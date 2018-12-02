package hu.waldorf.finance.service;

import hu.waldorf.finance.model.BefizetesRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
public class ErstePDFImportService {
    @Autowired
    private BefizetesRepository befizetesRepository;

    // TODO: the szamlaszam might be in the pdf, so can be parsed out?
    public void importErsteDataFile(MultipartFile file, String szamlaszam) throws Exception {
        PDDocument document = PDDocument.load(file.getBytes());
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text=pdfStripper.getText(document);
        document.close();
        System.out.println(text);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date now = new Date();

        boolean commonInfoEnded=false;
        for (String line : text.split("\n")) {
            if(!commonInfoEnded && line.startsWith("Értéknap Partner / Megjegyzés Összeg")){
                commonInfoEnded=true;
            }
            else if(commonInfoEnded){
                // TODO parse the document
            }
        }

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
}
