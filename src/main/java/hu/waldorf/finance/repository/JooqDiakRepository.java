package hu.waldorf.finance.repository;

import com.google.inject.Inject;
import hu.waldorf.finance.generated.Tables;
import hu.waldorf.finance.generated.tables.records.DiakokRecord;
import hu.waldorf.finance.mapper.DiakokRecordMapper;
import hu.waldorf.finance.model.Diak;
import org.jooq.DSLContext;

import java.util.List;

public class JooqDiakRepository implements DiakRepository {
    private final DSLContext dslContext;
    private final DiakokRecordMapper diakokRecordMapper;

    @Inject
    public JooqDiakRepository(DSLContext dslContext, DiakokRecordMapper diakokRecordMapper) {
        this.dslContext = dslContext;
        this.diakokRecordMapper = diakokRecordMapper;
    }

    @Override
    public List<Diak> findByCsaladId(int csaladId) {
        return dslContext.fetch(Tables.DIAKOK, Tables.DIAKOK.CSALAD_ID.eq(csaladId))
                .map(diakokRecordMapper);
    }

    @Override
    public void deleteAll() {
        dslContext.deleteFrom(Tables.DIAKOK).execute();
    }

    @Override
    public void store(Diak diak) {
        DiakokRecord record = diakokRecordMapper.unmap(diak);
        record.store();

        if (diak.getId() == null) {
            diak.setId(dslContext.lastID().intValueExact());
        }
    }

}
