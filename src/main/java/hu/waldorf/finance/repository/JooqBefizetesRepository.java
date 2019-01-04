package hu.waldorf.finance.repository;

import com.google.inject.Inject;
import hu.waldorf.finance.generated.Tables;
import hu.waldorf.finance.model.Befizetes;
import org.jooq.DSLContext;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class JooqBefizetesRepository implements BefizetesRepository {
    private final DSLContext dslContext;

    @Inject
    public JooqBefizetesRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public List<Befizetes> findNemFeldolgozottak() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        dslContext.deleteFrom(Tables.BEFIZETESEK);
    }

    @Override
    public void save(Befizetes befizetes) {
        dslContext.insertInto(Tables.BEFIZETESEK)
                .columns(Tables.BEFIZETESEK.IMPORT_FORRAS,
                        Tables.BEFIZETESEK.IMPORT_IDOPONT,
                        Tables.BEFIZETESEK.KONYVELESI_NAP,
                        Tables.BEFIZETESEK.BEFIZETO_NEV,
                        Tables.BEFIZETESEK.BEFIZETO_SZAMLASZAM,
                        Tables.BEFIZETESEK.OSSZEG,
                        Tables.BEFIZETESEK.KOZLEMENY,
                        Tables.BEFIZETESEK.STATUSZ)
                .values(befizetes.getImportForras(),
                        new Timestamp(befizetes.getImportIdopont().getTime()),
                        new Date(befizetes.getKonyvelesiNap().getTime()),
                        befizetes.getBefizetoNev(),
                        befizetes.getBefizetoSzamlaszam(),
                        befizetes.getOsszeg(),
                        befizetes.getKozlemeny(),
                        befizetes.getStatusz().name())
                .execute();

        befizetes.setId(dslContext.lastID().intValueExact());
    }
}
