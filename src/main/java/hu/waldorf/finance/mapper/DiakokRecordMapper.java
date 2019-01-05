package hu.waldorf.finance.mapper;

import com.google.inject.Inject;
import hu.waldorf.finance.generated.Tables;
import hu.waldorf.finance.generated.tables.records.DiakokRecord;
import hu.waldorf.finance.model.Diak;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordUnmapper;
import org.jooq.exception.MappingException;

public class DiakokRecordMapper implements RecordMapper<Record, Diak>, RecordUnmapper<Diak, DiakokRecord> {
    private final DSLContext dslContext;

    @Inject
    public DiakokRecordMapper(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public Diak map(Record record) {
        DiakokRecord diakokRecord = (DiakokRecord) record;

        Diak diak = new Diak();
        diak.setId(diakokRecord.getId());
        diak.setNev(diakokRecord.getNev());
        diak.setOsztaly(diakokRecord.getOsztaly());
        diak.setCsaladId(diakokRecord.getCsaladId());
        return diak;
    }

    @Override
    public DiakokRecord unmap(Diak source) throws MappingException {
        DiakokRecord record = dslContext.newRecord(Tables.DIAKOK);
        record.setId(source.getId());
        record.setNev(source.getNev());
        record.setOsztaly(source.getOsztaly());
        record.setCsaladId(source.getCsaladId());

        record.changed(Tables.DIAKOK.ID, false);
        return record;
    }
}
