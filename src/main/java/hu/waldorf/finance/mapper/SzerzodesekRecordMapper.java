package hu.waldorf.finance.mapper;

import com.google.inject.Inject;
import hu.waldorf.finance.generated.Tables;
import hu.waldorf.finance.generated.tables.records.SzerzodesekRecord;
import hu.waldorf.finance.model.Szerzodes;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordUnmapper;
import org.jooq.exception.MappingException;

import static hu.waldorf.finance.generated.Tables.SZERZODESEK;

public class SzerzodesekRecordMapper implements RecordMapper<Record, Szerzodes>, RecordUnmapper<Szerzodes, SzerzodesekRecord> {
    private final DSLContext dslContext;

    @Inject
    public SzerzodesekRecordMapper(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

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

    @Override
    public SzerzodesekRecord unmap(Szerzodes source) throws MappingException {
        SzerzodesekRecord record = dslContext.newRecord(Tables.SZERZODESEK);
        record.setId(source.getId());
        record.setTamogato(source.getTamogato());
        record.setMukodesiKoltsegTamogas(source.getMukodesiKoltsegTamogatas());
        record.setEpitesiHozzajarulas(source.getEpitesiHozzajarulas());
        record.setCsaladId(source.getCsaladId());
        record.setMukodesiKoltsegTamogasInduloEgyenleg(source.getMukodesiKoltsegTamogatasInduloEgyenleg());
        record.setEpitesiHozzajarulasInduloEgyenleg(source.getEpitesiHozzajarulasInduloEgyenleg());

        record.changed(SZERZODESEK.ID, false);
        return record;
    }
}
