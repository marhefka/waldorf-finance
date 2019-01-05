package hu.waldorf.finance.service;

import com.google.inject.Inject;
import hu.waldorf.finance.model.Befizetes;
import hu.waldorf.finance.model.FeldolgozasStatusza;
import hu.waldorf.finance.repository.BefizetesRepository;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MagnetImportService {
    private final BefizetesRepository befizetesRepository;

    @Inject
    public MagnetImportService(BefizetesRepository befizetesRepository) {
        this.befizetesRepository = befizetesRepository;
    }

    public ImportResult importMagnetDataFile(byte[] data, String fileName) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new ByteArrayInputStream(data));

        XPathFactory xPathfactory = XPathFactory.newInstance();

        XPathExpression expr = xPathfactory.newXPath().compile("//Tranzakcio");
        NodeList nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd.");

        Date now = new Date();

        int success=0;
        for (int i = 0; i < nodeList.getLength(); i++) {
            boolean feldolgozando = true;
            Node row = nodeList.item(i);

            Befizetes befizetes = new Befizetes();
            befizetes.setImportIdopont(now);
            befizetes.setStatusz(FeldolgozasStatusza.BEIMPORTALVA);

            NodeList childNodes = row.getChildNodes();
            for (int j = 0; j < childNodes.getLength(); j++) {
                Node childNode = childNodes.item(j);
                if (childNode.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }

                String value = childNode.getTextContent();
                switch (childNode.getNodeName()) {
                    case "Tranzakcioszam":
                        befizetes.setImportForras("Magnet/" + fileName + "/" + value);
                        break;
                    case "Ellenpartner":
                        befizetes.setBefizetoNev(value);
                        break;
                    case "Ellenszamla":
                        befizetes.setBefizetoSzamlaszam(value);
                        break;
                    case "Osszeg":// devizanem check
                        double d = Double.parseDouble(value);
                        if (d <= 0.0) {
                            feldolgozando = false;
                            break;
                        }

                        int osszeg = (int) d;

                        if (d - osszeg >= 0.01) {
                            throw new RuntimeException("Filler ertek?: " + value);
                        }

                        befizetes.setOsszeg(osszeg);
                        break;
                    case "Kozlemeny":
                        befizetes.setKozlemeny(value);
                        break;
                    case "Terhelesnap":
                        befizetes.setKonyvelesiNap(simpleDateFormat.parse(value));
                        break;
                    case "Tipus":
                        if (!value.equals("Átutalás (IG2)")) {
                            feldolgozando = false;
                        }
                        break;
                    default:
                        break;
                }
            }

            if (feldolgozando) {
                befizetesRepository.store(befizetes);
                success++;
            }
        }

        return ImportResult.success(success);
    }
}
