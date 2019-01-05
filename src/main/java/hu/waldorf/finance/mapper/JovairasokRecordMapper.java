package hu.waldorf.finance.mapper;

import com.google.inject.Inject;
import hu.waldorf.finance.generated.tables.records.JovairasRecord;
import hu.waldorf.finance.model.Jovairas;
import org.jooq.DSLContext;
import org.jooq.RecordUnmapper;

import java.sql.Date;

import static hu.waldorf.finance.generated.Tables.JOVAIRAS;

public class JovairasokRecordMapper implements RecordUnmapper<Jovairas, JovairasRecord> {
    private final DSLContext dslContext;

    @Inject
    public JovairasokRecordMapper(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public JovairasRecord unmap(Jovairas jovairas) {
        JovairasRecord record = dslContext.newRecord(JOVAIRAS);
        record.setId(jovairas.getId());
        record.setSzerzodesId(jovairas.getSzerzodesId());
        record.setMegnevezes(jovairas.getMegnevezes());
        record.setTipus(jovairas.getTipus().name());
        record.setOsszeg(jovairas.getOsszeg());
        record.setBefizetesId(jovairas.getBefizetesId());
        record.setKonyvelesiNap(new Date(jovairas.getKonyvelesiNap().getTime()));

        record.changed(JOVAIRAS.ID, false);
        return record;
    }
}
