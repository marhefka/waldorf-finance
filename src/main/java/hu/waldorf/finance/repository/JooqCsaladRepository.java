package hu.waldorf.finance.repository;

import com.google.inject.Inject;
import hu.waldorf.finance.generated.Tables;
import hu.waldorf.finance.model.Csalad;
import org.jooq.DSLContext;

public class JooqCsaladRepository implements CsaladRepository {
    private final DSLContext dslContext;

    @Inject
    public JooqCsaladRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public void deleteAll() {
        dslContext.deleteFrom(Tables.CSALADOK).execute();
    }

    @Override
    public void save(Csalad csalad) {
        dslContext.insertInto(Tables.CSALADOK)
                .columns(Tables.CSALADOK.EMAIL1, Tables.CSALADOK.EMAIL2)
                .values((String) null, null)
                .execute();
        csalad.setId(dslContext.lastID().intValueExact());
    }
}
