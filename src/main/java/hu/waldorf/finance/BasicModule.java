package hu.waldorf.finance;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import hu.waldorf.finance.db.ConnectionProvider;
import hu.waldorf.finance.db.DSLContextProvider;
import hu.waldorf.finance.db.TransactionSupporter;
import hu.waldorf.finance.repository.BefizetesRepository;
import hu.waldorf.finance.repository.CsaladRepository;
import hu.waldorf.finance.repository.DiakRepository;
import hu.waldorf.finance.repository.JooqBefizetesRepository;
import hu.waldorf.finance.repository.JooqCsaladRepository;
import hu.waldorf.finance.repository.JooqDiakRepository;
import hu.waldorf.finance.repository.JooqJovairasRepository;
import hu.waldorf.finance.repository.JooqSzerzodesRepository;
import hu.waldorf.finance.repository.JovairasRepository;
import hu.waldorf.finance.repository.SzerzodesRepository;
import hu.waldorf.finance.service.JovairasService;
import org.jooq.DSLContext;

import java.sql.Connection;

public class BasicModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Connection.class).toProvider(ConnectionProvider.class);
        bind(DSLContext.class).toProvider(DSLContextProvider.class);

        bind(BefizetesRepository.class).to(JooqBefizetesRepository.class);
        bind(CsaladRepository.class).to(JooqCsaladRepository.class);
        bind(DiakRepository.class).to(JooqDiakRepository.class);
        bind(JovairasRepository.class).to(JooqJovairasRepository.class);
        bind(SzerzodesRepository.class).to(JooqSzerzodesRepository.class);

        bindInterceptor(Matchers.only(JovairasService.class), Matchers.any(), new TransactionSupporter());
    }
}
