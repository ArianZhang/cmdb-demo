package com.xbrother.common.config;

import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.xbrother.common.hibernate.interceptor.DataObjectInterceptor;
import com.xbrother.common.utils.PropertiesUtils;

@Configuration
public class DataSourceConfig {

	@Bean
	public DataSource dataSource(Environment environment) throws Exception {

		return new ComboPooledDataSource();

		// return new EmbeddedDatabaseBuilder()
		// .setName("crm")
		// .setType(EmbeddedDatabaseType.H2)
		// .build();

		// String user = environment.getProperty("ds.user"),
		// pw = environment.getProperty("ds.password"),
		// url = environment.getProperty("ds.url");
		// Class<Driver> driverClass = environment.getPropertyAsClass(
		// "ds.driverClass", Driver.class );
		//
		// BasicDataSource basicDataSource = new BasicDataSource();
		// basicDataSource.setDriverClassName( driverClass.getName() );
		// basicDataSource.setPassword( pw );
		// basicDataSource.setUrl( url );
		// basicDataSource.setUsername( user );
		// basicDataSource.setInitialSize( 5 );
		// basicDataSource.setMaxActive( 10 );
		// return basicDataSource;
		//

	}

	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
		final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setPackagesToScan(getPackagesToScan());
		Properties props = new Properties();
		props.putAll(hibernateProperties());
		sessionFactory.setHibernateProperties(props);
		sessionFactory.setEntityInterceptor(new DataObjectInterceptor());
		return sessionFactory;
	}

	// @Bean
	// public LocalContainerEntityManagerFactoryBean
	// localContainerEntityManagerFactoryBean(DataSource dataSource)
	// throws Exception {
	// LocalContainerEntityManagerFactoryBean em = new
	// LocalContainerEntityManagerFactoryBean();
	// em.setDataSource(dataSource);
	// em.setPackagesToScan(getPackagesToScan());
	// em.setPersistenceProvider(new HibernatePersistence());
	// em.setJpaPropertyMap(hibernateProperties());
	// return em;
	// }

	public String[] getPackagesToScan() {
		return new String[] { AppConfig.BASE_PACKAGES + ".common.entity", AppConfig.BASE_PACKAGES + ".rst.entity" };
	}

	public Map<String, String> hibernateProperties() {
		Properties properties = PropertiesUtils.getProperties("hibernate.properties");
		// Map<String, String> p = new HashMap<String, String>();
		// p.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "create");
		// p.put(org.hibernate.cfg.Environment.HBM2DDL_IMPORT_FILES,
		// "sql/initialize_all.sql");
		// p.put(org.hibernate.cfg.Environment.DIALECT,
		// MySQL5Dialect.class.getName());
		// p.put(org.hibernate.cfg.Environment.SHOW_SQL, "true");
		return PropertiesUtils.convert(properties);
	}

}
