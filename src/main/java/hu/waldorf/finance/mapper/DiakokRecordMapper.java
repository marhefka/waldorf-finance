package hu.waldorf.finance.mapper;

import hu.waldorf.finance.generated.tables.records.DiakokRecord;
import hu.waldorf.finance.model.Diak;
import org.jooq.Record;
import org.jooq.RecordMapper;

public class DiakokRecordMapper implements RecordMapper<Record, Diak> {
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
}
