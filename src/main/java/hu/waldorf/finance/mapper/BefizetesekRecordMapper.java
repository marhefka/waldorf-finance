package hu.waldorf.finance.mapper;

import hu.waldorf.finance.generated.tables.records.BefizetesekRecord;
import hu.waldorf.finance.model.Befizetes;
import hu.waldorf.finance.model.FeldolgozasStatusza;
import org.jooq.Record;
import org.jooq.RecordMapper;

import java.util.Date;

public class BefizetesekRecordMapper implements RecordMapper<Record, Befizetes> {
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
}
