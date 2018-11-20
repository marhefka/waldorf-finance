package hu.waldorf.finance;

import hu.waldorf.finance.import_.CsaladRepository;
import hu.waldorf.finance.import_.DiakRepository;
import hu.waldorf.finance.import_.SzerzodesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ErsteImportTest {
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private CsaladRepository csaladRepository;

    @Autowired
    private DiakRepository diakRepository;

    @Autowired
    private SzerzodesRepository szerzodesRepository;

    @Test
    public void hello() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("Erste10.xml").getFile());

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        XPathFactory xPathfactory = XPathFactory.newInstance();

        XPathExpression expr = xPathfactory.newXPath().compile("//Worksheet[@Name='11994002-02405425-00000000']//Row");
        NodeList nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node row = nodeList.item(i);

            XPathExpression expr2 = xPathfactory.newXPath().compile("//Cell");
            NodeList nodeList2 = (NodeList) expr2.evaluate(row, XPathConstants.NODESET);

            Node cell = nodeList2.item(i);

            for (int j = 0; j < 7; j++) {
                Node cellX = cell.getChildNodes().item(j);
                System.out.println(cellX.getTextContent());
            }

            Node cell7 = cell.getChildNodes().item(7);
            String nodeValue = cell7.getFirstChild().getFirstChild().getNodeValue();
            if (!nodeValue.equals("J")) {
                // Jovairas
                continue;
            }

            System.out.println();
        }


        System.out.println();

//        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
//        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
//
//        transactionManager.commit(transactionStatus);
    }

}
