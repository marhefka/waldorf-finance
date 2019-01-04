package hu.waldorf.finance.mapper;

import hu.waldorf.finance.generated.Tables;
import hu.waldorf.finance.generated.tables.records.DiakokRecord;
import hu.waldorf.finance.model.Diak;
import org.jooq.Record;
import org.jooq.RecordMapper;

public class DiakokRecordMapper implements RecordMapper<Record, Diak> {
    @Override
    public Diak map(Record record) {
        DiakokRecord diakokRecord = (DiakokRecord) record;

        Diak diak = new Diak();
        diak.setId(diakokRecord.get(Tables.DIAKOK.ID));
        diak.setNev(diakokRecord.get(Tables.DIAKOK.NEV));
        diak.setOsztaly(diakokRecord.get(Tables.DIAKOK.OSZTALY));
        diak.setCsaladId(diakokRecord.get(Tables.DIAKOK.CSALAD_ID));
        return diak;
    }
}
