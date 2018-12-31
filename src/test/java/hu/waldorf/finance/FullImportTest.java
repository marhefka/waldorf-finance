package hu.waldorf.finance;

import hu.waldorf.finance.service.DbDeleteService;
import hu.waldorf.finance.service.ErsteXMLImportService;
import hu.waldorf.finance.service.MagnetImportService;
import hu.waldorf.finance.service.SzerzodesImportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.File;
import java.nio.file.Files;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FullImportTest {
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private DbDeleteService dbDeleteService;

    @Autowired
    private SzerzodesImportService szerzodesImportService;

    @Autowired
    private ErsteXMLImportService ersteImportService;

    @Autowired
    private MagnetImportService magnetImportService;

    @Test
    public void importEverything() throws Exception {
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);

        dbDeleteService.deleteDb();

        szerzodesImportService.importSzerzodesek(lookupTestResource("tamogatok.csv"));

        File ersteXML=lookupTestResource("Erste10.xml");
        File magnetXML1=lookupTestResource("haviKivonat_201809_1620010611564492.xml");
        File magnetXML2=lookupTestResource("haviKivonat_201810_1620010611564492.xml");
        ersteImportService.importErsteDataFile(Files.readAllBytes(ersteXML.toPath()),"Erste10.xml", "11994002-02405425-00000000");
        magnetImportService.importMagnetDataFile(Files.readAllBytes(magnetXML1.toPath()),"haviKivonat_201809_1620010611564492.xml");
        magnetImportService.importMagnetDataFile(Files.readAllBytes(magnetXML2.toPath()),"haviKivonat_201810_1620010611564492.xml");

        transactionManager.commit(transactionStatus);
    }

    private File lookupTestResource(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(filename).getFile());
    }
}
