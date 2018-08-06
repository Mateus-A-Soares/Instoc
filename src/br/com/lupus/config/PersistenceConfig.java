package br.com.lupus.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <h1> Classe de configuração do Hibernate Framework </h1>
 * <p> Define configurações gerais do banco a ser utilizado</p>
 * <p> Define o pacote com os modelos a serem interpretados para o ORM.</p>
 * 
 * @author Mateus A.S
 */
@Configuration
@EnableTransactionManagement
public class PersistenceConfig {
	
	public Properties getHibernateProperties() {
		
		Properties properties = new Properties();
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.setProperty("hibernate.connection.CharSet", "utf8");
		properties.setProperty("hibernate.connection.characterEncoding", "utf8");
		properties.setProperty("hibernate.connection.useUnicode", "true");
		return properties;
	}
	
	@Bean
	public DataSource getDataSource() {
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/instock_db?"
				+ "serverTimezone=UTC&"
				+ "autoReconnect=true&"
				+ "useUnicode=true&"
				+ "createDatabaseIfNotExist=true&"
				+ "characterEncoding=utf-8");
		dataSource.setUsername("root");
		dataSource.setPassword("");
		return dataSource;
	}
	
	@Bean							
	public LocalSessionFactoryBean getSessionFactory() {
		
		LocalSessionFactoryBean factoryBean  = new LocalSessionFactoryBean();
		factoryBean.setDataSource(getDataSource());
		factoryBean.setHibernateProperties(getHibernateProperties());
		factoryBean.setPackagesToScan("br.com.lupus.models");
		return factoryBean;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager getTransictionManager() {
		
		HibernateTransactionManager transictionManager = new HibernateTransactionManager();
		transictionManager.setSessionFactory(getSessionFactory().getObject());
		return transictionManager;
	}
}
