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
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
public class ErsteXMLImportService {
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private BefizetesRepository befizetesRepository;

    public ImportResult importErsteDataFile(byte[] data, String fileName, String szamlaszam) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new ByteArrayInputStream(data));

        XPathFactory xPathfactory = XPathFactory.newInstance();

        XPathExpression expr = xPathfactory.newXPath().compile("//Worksheet[@Name='" + szamlaszam + "']//Row[position()>1]");
        NodeList nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date now = new Date();

        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);

        int success=0;
        for (int i = 0; i < nodeList.getLength(); i++) {
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
            befizetes.setImportForras("Erste/" + fileName);
            befizetes.setImportIdopont(now);
            befizetes.setKonyvelesiNap(dKonyvelesDatuma);
            befizetes.setBefizetoNev(partnerNeve);
            befizetes.setBefizetoSzamlaszam(partnerSzamlaszama);
            befizetes.setOsszeg(osszeg);
            befizetes.setKozlemeny(kozlemeny);
            befizetes.setStatusz(FeldolgozasStatusza.BEIMPORTALVA);
            befizetesRepository.save(befizetes);
            success++;
        }

        transactionManager.commit(transactionStatus);
        return ImportResult.success(success);
    }
}
