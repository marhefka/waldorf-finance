package hu.waldorf.finance.mapper;

import hu.waldorf.finance.generated.tables.records.SzerzodesekRecord;
import hu.waldorf.finance.model.Szerzodes;
import org.jooq.Record;
import org.jooq.RecordMapper;

public class SzerzodesekRecordMapper implements RecordMapper<Record, Szerzodes> {
    @Override
    public Szerzodes map(Record record) {
        SzerzodesekRecord szerzodesekRecord = (SzerzodesekRecord) record;

        Szerzodes szerzodes = new Szerzodes();
        szerzodes.setId(szerzodesekRecord.getId());
        szerzodes.setTamogato(szerzodesekRecord.getTamogato());
        szerzodes.setMukodesiKoltsegTamogatas(szerzodesekRecord.getMukodesiKoltsegTamogas());
        szerzodes.setEpitesiHozzajarulas(szerzodesekRecord.getEpitesiHozzajarulas());
        szerzodes.setCsaladId(szerzodesekRecord.getCsaladId());
        szerzodes.setMukodesiKoltsegTamogatasInduloEgyenleg(szerzodesekRecord.getMukodesiKoltsegTamogasInduloEgyenleg());
        szerzodes.setEpitesiHozzajarulasInduloEgyenleg(szerzodesekRecord.getEpitesiHozzajarulasInduloEgyenleg());
        return szerzodes;
    }
}
