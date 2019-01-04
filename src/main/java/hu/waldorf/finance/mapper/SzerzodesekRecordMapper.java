package hu.waldorf.finance.mapper;

import hu.waldorf.finance.generated.Tables;
import hu.waldorf.finance.generated.tables.Szerzodesek;
import hu.waldorf.finance.generated.tables.records.SzerzodesekRecord;
import hu.waldorf.finance.model.Szerzodes;
import org.jooq.Record;
import org.jooq.RecordMapper;

public class SzerzodesekRecordMapper implements RecordMapper<Record, Szerzodes> {
    private final Szerzodesek SZERZODESEK = Tables.SZERZODESEK;

    @Override
    public Szerzodes map(Record record) {
        SzerzodesekRecord szerzodesekRecord = (SzerzodesekRecord) record;

        Szerzodes szerzodes = new Szerzodes();
        szerzodes.setId(szerzodesekRecord.get(SZERZODESEK.ID));
        szerzodes.setTamogato(szerzodesekRecord.get(SZERZODESEK.TAMOGATO));
        szerzodes.setMukodesiKoltsegTamogatas(szerzodesekRecord.get(SZERZODESEK.MUKODESI_KOLTSEG_TAMOGAS));
        szerzodes.setEpitesiHozzajarulas(szerzodesekRecord.get(SZERZODESEK.EPITESI_HOZZAJARULAS));
        szerzodes.setCsaladId(szerzodesekRecord.get(SZERZODESEK.CSALAD_ID));
        szerzodes.setMukodesiKoltsegTamogatasInduloEgyenleg(szerzodesekRecord.get(SZERZODESEK.MUKODESI_KOLTSEG_TAMOGAS_INDULO_EGYENLEG));
        szerzodes.setEpitesiHozzajarulasInduloEgyenleg(szerzodesekRecord.get(SZERZODESEK.EPITESI_HOZZAJARULAS_INDULO_EGYENLEG));
        return szerzodes;
    }
}
