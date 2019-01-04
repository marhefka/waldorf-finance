package hu.waldorf.finance.repository;

import com.google.inject.Inject;
import hu.waldorf.finance.generated.Tables;
import hu.waldorf.finance.model.Szerzodes;
import org.jooq.DSLContext;

public class JooqSzerzodesRepository implements SzerzodesRepository {
    private final DSLContext dslContext;

    @Inject
    public JooqSzerzodesRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public void deleteAll() {
        dslContext.deleteFrom(Tables.SZERZODESEK);
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
}
