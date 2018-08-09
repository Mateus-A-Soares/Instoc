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
	
	/**
	 * 		Método que define as propriedades do Hibernate quanto
	 * ao banco de dados utilizado, como o dialeto, o tipo de banco, etc
	 * 
	 * @return objeto Properties com as propriedades do Hibernate
	 */
	public Properties getHibernateProperties() {
		
		Properties properties = new Properties();
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.hbm2ddl.auto", "create");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.setProperty("hibernate.connection.CharSet", "utf8");
		properties.setProperty("hibernate.connection.characterEncoding", "utf8");
		properties.setProperty("hibernate.connection.useUnicode", "true");
		return properties;
	}
	
	/**
	 * 		Bean que popula um objeto BasicDataSource com informações referentes ao banco,
	 * como a url, o driver e o login
	 * 
	 * @return DataSource já preenchido
	 */
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
	
	/**
	 *  	Bean que popula um objeto LocalSessionFactoryBean com o DataSource contendo
	 *  as informações de conexão com o banco, as propriedades configuradas para o Hibernate,
	 *  e o pacote com os modelos para o ORM.
	 *  
	 * @return LocalSessionFactoryBean já populado
	 */
	@Bean							
	public LocalSessionFactoryBean getSessionFactory() {
		
		LocalSessionFactoryBean factoryBean  = new LocalSessionFactoryBean();
		factoryBean.setDataSource(getDataSource());
		factoryBean.setHibernateProperties(getHibernateProperties());
		factoryBean.setPackagesToScan("br.com.lupus.models");
		return factoryBean;
	}

	/**
	 * 		Bean que popula um objeto HibernateTransactionManager, que cuida da conexão com
	 * o banco de dados,  com a LocalSessionFactory já populada com todas as informações que
	 * o Hibernate precisa pra iniciar uma conexão.
	 * 
	 * @return HibernateTrasactionManager pronto para iniciar uma conexão com o banco
	 */
	@Bean
	@Autowired
	public HibernateTransactionManager getTransictionManager() {
		
		HibernateTransactionManager transictionManager = new HibernateTransactionManager();
		transictionManager.setSessionFactory(getSessionFactory().getObject());
		return transictionManager;
	}
}
