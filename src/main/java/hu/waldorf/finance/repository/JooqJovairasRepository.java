package hu.waldorf.finance.repository;

import com.google.inject.Inject;
import hu.waldorf.finance.model.Jovairas;
import org.jooq.DSLContext;

import java.sql.Date;

import static hu.waldorf.finance.generated.Tables.JOVAIRAS;

public class JooqJovairasRepository implements JovairasRepository {
    private final DSLContext dslContext;

    @Inject
    public JooqJovairasRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public void deleteAll() {
        dslContext.deleteFrom(JOVAIRAS);
    }

    @Override
    public void insert(Jovairas jovairas) {
        dslContext.insertInto(JOVAIRAS)
                .columns(JOVAIRAS.SZERZODES_ID,
                        JOVAIRAS.MEGNEVEZES,
                        JOVAIRAS.TIPUS,
                        JOVAIRAS.OSSZEG,
                        JOVAIRAS.BEFIZETES_ID,
                        JOVAIRAS.KONYVELESI_NAP)
                .values(jovairas.getSzerzodesId(),
                        jovairas.getMegnevezes(),
                        jovairas.getTipus().name(),
                        jovairas.getOsszeg(),
                        jovairas.getBefizetesId(),
                        new Date(jovairas.getKonyvelesiNap().getTime()))
                .execute();

        jovairas.setId(dslContext.lastID().intValueExact());
    }
}
