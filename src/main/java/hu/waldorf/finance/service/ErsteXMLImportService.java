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

public class ErsteXMLImportService {
    private final BefizetesRepository befizetesRepository;

    @Inject
    public ErsteXMLImportService(BefizetesRepository befizetesRepository) {
        this.befizetesRepository = befizetesRepository;
    }

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

        int success = 0;
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

        return ImportResult.success(success);
    }
}
