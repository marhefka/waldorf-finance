package hu.waldorf.finance.mapper;

import com.google.inject.Inject;
import hu.waldorf.finance.generated.Tables;
import hu.waldorf.finance.generated.tables.records.CsaladokRecord;
import hu.waldorf.finance.model.Csalad;
import org.jooq.DSLContext;
import org.jooq.RecordUnmapper;
import org.jooq.exception.MappingException;

public class CsaladokRecordMapper implements RecordUnmapper<Csalad, CsaladokRecord> {
    private final DSLContext dslContext;

    @Inject
    public CsaladokRecordMapper(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public CsaladokRecord unmap(Csalad source) throws MappingException {
        CsaladokRecord record = dslContext.newRecord(Tables.CSALADOK);
        record.setId(source.getId());// itt problema lesz, ha update-elni szeretnenk a csaladod, mert mindig insertet fog futtatni - ez a furcsasag azert van, mert csaladot ugy hozunk letre, hogy mi adjuk az id-t is neki
        return record;
    }
}
