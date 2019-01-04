package hu.waldorf.finance.db;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class DSLContextProvider implements Provider<DSLContext> {
    private final ConnectionProvider connectionProvider;

    @Inject
    public DSLContextProvider(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public DSLContext get() {
        return DSL.using(connectionProvider.get(), SQLDialect.MYSQL);
    }
}
