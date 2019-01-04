package hu.waldorf.finance;

import com.google.inject.Guice;
import com.google.inject.Injector;
import hu.waldorf.finance.db.ConnectionProvider;
import hu.waldorf.finance.db.TransactionManager;
import hu.waldorf.finance.service.DbDeleteService;
import hu.waldorf.finance.service.ErsteXMLImportService;
import hu.waldorf.finance.service.MagnetImportService;
import hu.waldorf.finance.service.SzerzodesImportService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;

public class FullImportTest {
    private Injector injector;
    private ConnectionProvider connectionProvider;
    private TransactionManager transactionManager;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new BasicModule());
        connectionProvider = injector.getInstance(ConnectionProvider.class);
        transactionManager = injector.getInstance(TransactionManager.class);
    }

    @After
    public void tearDown() throws Exception {
        connectionProvider.close();
    }

    @Test
    public void importEverything() throws Exception {
        DbDeleteService dbDeleteService = injector.getInstance(DbDeleteService.class);
        SzerzodesImportService szerzodesImportService = injector.getInstance(SzerzodesImportService.class);
        ErsteXMLImportService ersteXMLImportService = injector.getInstance(ErsteXMLImportService.class);
        MagnetImportService magnetImportService = injector.getInstance(MagnetImportService.class);

        dbDeleteService.deleteDb();

        szerzodesImportService.importSzerzodesek(lookupTestResource("tamogatok.csv"));

        File ersteXML = lookupTestResource("Erste10.xml");
        File magnetXML1 = lookupTestResource("haviKivonat_201809_1620010611564492.xml");
        File magnetXML2 = lookupTestResource("haviKivonat_201810_1620010611564492.xml");
        ersteXMLImportService.importErsteDataFile(Files.readAllBytes(ersteXML.toPath()), "Erste10.xml", "11994002-02405425-00000000");
        magnetImportService.importMagnetDataFile(Files.readAllBytes(magnetXML1.toPath()), "haviKivonat_201809_1620010611564492.xml");
        magnetImportService.importMagnetDataFile(Files.readAllBytes(magnetXML2.toPath()), "haviKivonat_201810_1620010611564492.xml");

        transactionManager.commit();
    }

    private File lookupTestResource(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(filename).getFile());
    }
}
