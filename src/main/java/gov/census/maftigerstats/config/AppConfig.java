package gov.census.maftigerstats.config;

import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("gov.census.maftigerstats")
@PropertySource({ "classpath:persistence-oracle.properties" })
@PropertySource({ "classpath:application.properties" })
public class AppConfig implements WebMvcConfigurer {

	@Autowired
	private Environment env;

	@Bean
	public DataSource myDataSource2() throws NamingException {
		
		DriverManagerDataSource myDataSource = new DriverManagerDataSource();
		myDataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		myDataSource.setUrl(env.getProperty("spring.datasource.url"));
		myDataSource.setUsername(env.getProperty("spring.datasource.username"));
		myDataSource.setPassword(env.getProperty("spring.datasource.password"));
		System.out.println("###### MyDataSource: " + myDataSource);
		return myDataSource;
	}

	private Properties getHibernateProperties() {

// 		set hibernate properties
		Properties props = new Properties();

		props.setProperty("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
		props.setProperty("hibernate.show_sql", env.getProperty("spring.jpa.hibernate.show_sql"));
		props.setProperty("hibernate.id.new_generator_mappings", env.getProperty("spring.jpa.properties.hibernate.id.new_generator_mappings"));
		return props;
	}

	@Bean("sessionFactory_T")
	public LocalSessionFactoryBean sessionFactory_T() throws NamingException {

// create session factorys
		LocalSessionFactoryBean sessionFactory_T = new LocalSessionFactoryBean();

// set the properties
		sessionFactory_T.setDataSource(myDataSource2());
		sessionFactory_T.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
		sessionFactory_T.setHibernateProperties(getHibernateProperties());

		return sessionFactory_T;
	}

	@Bean("transactionManager_T")
	@Autowired
	public HibernateTransactionManager transactionManager_T(SessionFactory sessionFactory_T) {

// setup transaction manager based on session factory
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory_T);

		return txManager;
	}

}