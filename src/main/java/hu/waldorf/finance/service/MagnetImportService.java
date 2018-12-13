package hu.waldorf.finance.service;

import hu.waldorf.finance.model.Befizetes;
import hu.waldorf.finance.model.BefizetesRepository;
import hu.waldorf.finance.model.FeldolgozasStatusza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
public class MagnetImportService {
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private BefizetesRepository befizetesRepository;

    public void importMagnetDataFile(File file) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        XPathFactory xPathfactory = XPathFactory.newInstance();

        XPathExpression expr = xPathfactory.newXPath().compile("//Tranzakcio");
        NodeList nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd.");

        Date now = new Date();

        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);

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
                        befizetes.setImportForras("Magnet/" + file.getName() + "/" + value);
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
                befizetesRepository.save(befizetes);
            }
        }

        transactionManager.commit(transactionStatus);
    }

}
