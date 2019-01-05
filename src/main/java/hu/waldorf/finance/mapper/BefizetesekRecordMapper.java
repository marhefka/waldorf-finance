package hu.waldorf.finance.mapper;

import com.google.inject.Inject;
import hu.waldorf.finance.generated.Tables;
import hu.waldorf.finance.generated.tables.records.BefizetesekRecord;
import hu.waldorf.finance.model.Befizetes;
import hu.waldorf.finance.model.FeldolgozasStatusza;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordUnmapper;
import org.jooq.exception.MappingException;

import java.sql.Timestamp;
import java.util.Date;

public class BefizetesekRecordMapper implements RecordMapper<Record, Befizetes>, RecordUnmapper<Befizetes, BefizetesekRecord> {
    private final DSLContext dslContext;

    @Inject
    public BefizetesekRecordMapper(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public Befizetes map(Record record) {
        BefizetesekRecord befizetesekRecord = (BefizetesekRecord) record;
        Befizetes befizetes = new Befizetes();
        befizetes.setId(befizetesekRecord.getId());
        befizetes.setImportForras(befizetesekRecord.getImportForras());
        befizetes.setImportIdopont(new Date(befizetesekRecord.getImportIdopont().getTime()));
        befizetes.setKonyvelesiNap(new Date(befizetesekRecord.getKonyvelesiNap().getTime()));
        befizetes.setBefizetoNev(befizetesekRecord.getBefizetoNev());
        befizetes.setBefizetoSzamlaszam(befizetesekRecord.getBefizetoSzamlaszam());
        befizetes.setOsszeg(befizetesekRecord.getOsszeg());
        befizetes.setKozlemeny(befizetesekRecord.getKozlemeny());
        befizetes.setStatusz(FeldolgozasStatusza.valueOf(befizetesekRecord.getStatusz()));
        return befizetes;
    }

    @Override
    public BefizetesekRecord unmap(Befizetes source) throws MappingException {
        BefizetesekRecord record = dslContext.newRecord(Tables.BEFIZETESEK);
        record.setId(source.getId());
        record.setImportForras(source.getImportForras());
        record.setImportIdopont(new Timestamp(source.getImportIdopont().getTime()));
        record.setKonyvelesiNap(new java.sql.Date(source.getKonyvelesiNap().getTime()));
        record.setBefizetoNev(source.getBefizetoNev());
        record.setBefizetoSzamlaszam(source.getBefizetoSzamlaszam());
        record.setOsszeg(source.getOsszeg());
        record.setKozlemeny(source.getKozlemeny());
        record.setStatusz(source.getStatusz().name());
        return record;

    }
}
