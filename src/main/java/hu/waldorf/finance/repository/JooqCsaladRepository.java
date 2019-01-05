package hu.waldorf.finance.repository;

import com.google.inject.Inject;
import hu.waldorf.finance.generated.Tables;
import hu.waldorf.finance.generated.tables.records.CsaladokRecord;
import hu.waldorf.finance.mapper.CsaladokRecordMapper;
import hu.waldorf.finance.model.Csalad;
import org.jooq.DSLContext;

public class JooqCsaladRepository implements CsaladRepository {
    private final DSLContext dslContext;
    private final CsaladokRecordMapper csaladokRecordMapper;

    @Inject
    public JooqCsaladRepository(DSLContext dslContext, CsaladokRecordMapper csaladokRecordMapper) {
        this.dslContext = dslContext;
        this.csaladokRecordMapper = csaladokRecordMapper;
    }

    @Override
    public void deleteAll() {
        dslContext.deleteFrom(Tables.CSALADOK).execute();
    }

    @Override
    public void store(Csalad csalad) {
        CsaladokRecord csaladokRecord = csaladokRecordMapper.unmap(csalad);
        csaladokRecord.store();

        if (csalad.getId() == null) {
            csalad.setId(dslContext.lastID().intValueExact());
        }
    }
}
