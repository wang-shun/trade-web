package com.centaline.api.conf;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;



@AutoConfigureAfter(org.mybatis.spring.SqlSessionFactoryBean.class)
public class MybatisScan {
	
	private static Logger logger = LoggerFactory.getLogger(MybatisScan.class);
	
	@Autowired
	DataSource dataSource;

	@Bean
	@ConditionalOnMissingBean
	public SqlSessionFactory sqlSessionFactory() {
		try {
			SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
			sessionFactory.setDataSource(dataSource);
			sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource("classpath:/mapper/mappers.xml"));
			return sessionFactory.getObject();
		} catch (Exception e) {
			logger.error("Init Mybatis mapper error",e);
		}
		return null;
	}
	
	@Bean
	public MapperScannerConfigurer getMapperScannerConfigurer() {
		MapperScannerConfigurer msc = new MapperScannerConfigurer();
		msc.setBasePackage("com.centaline.trans.**.repository");
		msc.setAnnotationClass(com.centaline.trans.common.MyBatisRepository.class);
		return msc;
	}
}
