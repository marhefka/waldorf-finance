package hu.waldorf.finance.repository;

import com.google.inject.Inject;
import hu.waldorf.finance.generated.Tables;
import hu.waldorf.finance.generated.tables.records.BefizetesekRecord;
import hu.waldorf.finance.mapper.BefizetesekRecordMapper;
import hu.waldorf.finance.model.Befizetes;
import hu.waldorf.finance.model.FeldolgozasStatusza;
import org.jooq.DSLContext;

import java.util.List;

public class JooqBefizetesRepository implements BefizetesRepository {
    private final DSLContext dslContext;
    private final BefizetesekRecordMapper befizetesekRecordMapper;

    @Inject
    public JooqBefizetesRepository(DSLContext dslContext, BefizetesekRecordMapper befizetesekRecordMapper) {
        this.dslContext = dslContext;
        this.befizetesekRecordMapper = befizetesekRecordMapper;
    }

    @Override
    public List<Befizetes> findNemFeldolgozottak() {
        return dslContext.select()
                .from(Tables.BEFIZETESEK)
                .where(Tables.BEFIZETESEK.STATUSZ.eq(FeldolgozasStatusza.BEIMPORTALVA.name()))
                .orderBy(Tables.BEFIZETESEK.ID)
                .fetch()
                .map(befizetesekRecordMapper);
    }

    @Override
    public void deleteAll() {
        dslContext.deleteFrom(Tables.BEFIZETESEK).execute();
    }

    @Override
    public void save(Befizetes befizetes) {
        BefizetesekRecord record = befizetesekRecordMapper.unmap(befizetes);
        record.store();
    }

    @Override
    public Befizetes findById(int befizetesId) {
        return dslContext.fetchOne(Tables.BEFIZETESEK, Tables.BEFIZETESEK.ID.eq(befizetesId))
                .map(befizetesekRecordMapper);
    }
}
