package hu.waldorf.finance.db;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class TransactionSupporter implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        TransactionManager transactionManager = new TransactionManager(ConnectionProvider.getInstance().get());

        try {
            Object proceed = invocation.proceed();
            transactionManager.commit();
            return proceed;
        } catch (Exception ex) {
            transactionManager.rollback();
            throw ex;
        }
    }
}
