package com.xbrother.common.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Import(DataSourceConfig.class)
@EnableTransactionManagement
public class TransactionConfig {

//	@Bean
//	public PlatformTransactionManager transactionManagerPT(EntityManagerFactory entityManagerFactory) throws Exception {
//		return new JpaTransactionManager(entityManagerFactory);
//	}

	@Bean
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		final HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		return txManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

}
