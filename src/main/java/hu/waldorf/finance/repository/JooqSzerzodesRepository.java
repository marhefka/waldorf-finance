package hu.waldorf.finance.repository;

import com.google.inject.Inject;
import hu.waldorf.finance.generated.Tables;
import hu.waldorf.finance.mapper.SzerzodesekRecordMapper;
import hu.waldorf.finance.model.Szerzodes;
import org.jooq.DSLContext;

import java.util.List;

public class JooqSzerzodesRepository implements SzerzodesRepository {
    private final DSLContext dslContext;
    private final SzerzodesekRecordMapper szerzodesekRecordMapper;

    @Inject
    public JooqSzerzodesRepository(DSLContext dslContext, SzerzodesekRecordMapper szerzodesekRecordMapper) {
        this.dslContext = dslContext;
        this.szerzodesekRecordMapper = szerzodesekRecordMapper;
    }

    @Override
    public void deleteAll() {
        dslContext.deleteFrom(Tables.SZERZODESEK).execute();
    }

    @Override
    public void save(Szerzodes szerzodes) {
        dslContext.insertInto(Tables.SZERZODESEK)
                .columns(
                        Tables.SZERZODESEK.TAMOGATO,
                        Tables.SZERZODESEK.MUKODESI_KOLTSEG_TAMOGAS,
                        Tables.SZERZODESEK.EPITESI_HOZZAJARULAS,
                        Tables.SZERZODESEK.CSALAD_ID,
                        Tables.SZERZODESEK.MUKODESI_KOLTSEG_TAMOGAS_INDULO_EGYENLEG,
                        Tables.SZERZODESEK.EPITESI_HOZZAJARULAS_INDULO_EGYENLEG
                )
                .values(
                        szerzodes.getTamogato(),
                        szerzodes.getMukodesiKoltsegTamogatas(),
                        szerzodes.getEpitesiHozzajarulas(),
                        szerzodes.getCsaladId(),
                        szerzodes.getMukodesiKoltsegTamogatasInduloEgyenleg(),
                        szerzodes.getEpitesiHozzajarulasInduloEgyenleg()
                )
                .execute();

        szerzodes.setId(dslContext.lastID().intValueExact());
    }

    @Override
    public List<Szerzodes> findAll() {
        return dslContext.select()
                .from(Tables.SZERZODESEK)
                .fetch()
                .map(szerzodesekRecordMapper);
    }
}
