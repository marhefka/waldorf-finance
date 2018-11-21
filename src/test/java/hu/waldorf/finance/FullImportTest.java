package hu.waldorf.finance;

import hu.waldorf.finance.service.DbDeleteService;
import hu.waldorf.finance.service.ErsteImportService;
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
    private ErsteImportService ersteImportService;

    @Autowired
    private MagnetImportService magnetImportService;

    @Test
    public void importEverything() throws Exception {
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);

        dbDeleteService.deleteDb();

        szerzodesImportService.importSzerzodesek(lookupTestResource("tamogatok.csv"));

        ersteImportService.importErsteDataFile(lookupTestResource("Erste10.xml"), "11994002-02405425-00000000");
        magnetImportService.importMagnetDataFile(lookupTestResource("haviKivonat_201809_1620010611564492.xml"));
        magnetImportService.importMagnetDataFile(lookupTestResource("haviKivonat_201810_1620010611564492.xml"));

        transactionManager.commit(transactionStatus);
    }

    private File lookupTestResource(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(filename).getFile());
    }
}
