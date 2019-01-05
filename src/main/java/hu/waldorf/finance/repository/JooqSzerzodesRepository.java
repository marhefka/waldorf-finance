package hu.waldorf.finance.repository;

import com.google.inject.Inject;
import hu.waldorf.finance.generated.Tables;
import hu.waldorf.finance.generated.tables.records.SzerzodesekRecord;
import hu.waldorf.finance.mapper.SzerzodesekRecordMapper;
import hu.waldorf.finance.model.Szerzodes;
import org.jooq.DSLContext;

import java.util.List;

public class JooqSzerzodesRepository implements SzerzodesRepository {
    private final DSLContext dslContext;
    private final SzerzodesekRecordMapper szerzodesekRecordMapper;

    @Inject
    public JooqSzerzodesRepository(DSLContext dslContext, SzerzodesekRecordMapper szerzodesekRecordMapper) {
        this.dslContext = dslContext;
        this.szerzodesekRecordMapper = szerzodesekRecordMapper;
    }

    @Override
    public void deleteAll() {
        dslContext.deleteFrom(Tables.SZERZODESEK).execute();
    }

    @Override
    public void store(Szerzodes szerzodes) {
        SzerzodesekRecord record = szerzodesekRecordMapper.unmap(szerzodes);
        record.store();

        if (szerzodes.getId() == null) {
            szerzodes.setId(dslContext.lastID().intValueExact());
        }
    }

    @Override
    public List<Szerzodes> findAll() {
        return dslContext.select()
                .from(Tables.SZERZODESEK)
                .fetch()
                .map(szerzodesekRecordMapper);
    }
}
