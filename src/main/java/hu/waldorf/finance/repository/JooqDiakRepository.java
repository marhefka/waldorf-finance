package hu.waldorf.finance.repository;

import com.google.inject.Inject;
import hu.waldorf.finance.generated.Tables;
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
        return dslContext
                .select()
                .from(Tables.DIAKOK)
                .where(Tables.DIAKOK.CSALAD_ID.eq(csaladId))
                .fetch()
                .map(diakokRecordMapper);
    }

    @Override
    public void deleteAll() {
        dslContext.deleteFrom(Tables.DIAKOK);
    }

    @Override
    public void save(Diak diak) {
        dslContext.insertInto(Tables.DIAKOK)
                .columns(
                        Tables.DIAKOK.NEV,
                        Tables.DIAKOK.OSZTALY,
                        Tables.DIAKOK.CSALAD_ID)
                .values(
                        diak.getNev(),
                        diak.getOsztaly(),
                        diak.getCsaladId())
                .execute();
        diak.setId(dslContext.lastID().intValueExact());
    }

}
