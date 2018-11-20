package hu.waldorf.finance;

import hu.waldorf.finance.import_.Befizetes;
import hu.waldorf.finance.import_.BefizetesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MagnetImportTest {
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private BefizetesRepository befizetesRepository;

    @Test
    public void hello() throws Exception {
        String fileName = "haviKivonat_201809_1620010611564492.xml";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

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
            befizetes.setFeldolgozva(false);

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

                        if (d-osszeg >= 0.01) {
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
