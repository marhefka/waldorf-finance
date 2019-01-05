package hu.waldorf.finance.repository;

import com.google.inject.Inject;
import hu.waldorf.finance.generated.tables.records.JovairasRecord;
import hu.waldorf.finance.mapper.JovairasokRecordMapper;
import hu.waldorf.finance.model.Jovairas;
import org.jooq.DSLContext;

import static hu.waldorf.finance.generated.Tables.JOVAIRAS;

public class JooqJovairasRepository implements JovairasRepository {
    private final DSLContext dslContext;
    private final JovairasokRecordMapper jovairasokRecordMapper;

    @Inject
    public JooqJovairasRepository(DSLContext dslContext, JovairasokRecordMapper jovairasokRecordMapper) {
        this.dslContext = dslContext;
        this.jovairasokRecordMapper = jovairasokRecordMapper;
    }

    @Override
    public void deleteAll() {
        dslContext.deleteFrom(JOVAIRAS).execute();
    }

    @Override
    public void store(Jovairas jovairas) {
        JovairasRecord record = jovairasokRecordMapper.unmap(jovairas);
        record.store();

        if (jovairas.getId() == null) {
            jovairas.setId(dslContext.lastID().intValueExact());
        }
    }
}
