package com.aist.uam.conf;

import java.util.ArrayList;
import java.util.Collection;

import com.centaline.api.shiroFilter.SecretKeyFilter;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

import com.aist.common.shiro.mobile.SessionManager;
import com.aist.common.shiro.mobile.token.MobileSessionTokenRepository;
import com.aist.common.shiro.mobile.token.impl.MobileSessionTokenRepositoryImpl;
import com.aist.common.shiro.mobile.token.impl.RefreshTokenServiceImpl;
import com.aist.common.shiro.mobile.validate.impl.QueryPwdSaltAuthenticationImpl;
import com.aist.common.shiro.redis.session.ShiroSessionFactory;
import com.aist.common.shiro.subject.UamCasSubjectFactory;
import com.aist.uam.auth.filter.CustomCasAppFilter;
import com.aist.uam.auth.filter.CustomUserFilter;
import com.aist.uam.auth.filter.RestfulAuthFilter;
import com.aist.uam.auth.filter.WeiXinUserFilter;
import com.aist.uam.auth.filter.WeixinAuthFilter;
import com.aist.uam.auth.realm.CasAppRealm;
import com.aist.uam.auth.realm.RestfulRealm;
import com.aist.uam.auth.realm.RestfulRefreshRealm;
import com.aist.uam.auth.realm.RetryLimitHashedCredentialsMatcher;
import com.aist.uam.auth.realm.WeiXinRealm;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.repository.RedisSessionRepository;
import com.aist.uam.auth.repository.SessionAppValidationSchedulerImpl;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.userorg.remote.UamUserOrgService;

/**
 * 覆盖aist-uam-client 中的shiroConfig配置
 * 添加白名单Filter和 密钥Filter
 */
@AutoConfigureAfter(PropertySourcesPlaceholderConfigurer.class)
public class ShiroConfig {

	@Bean
	public MobileSessionTokenRepositoryImpl mobileSessionTokenRepositoryImpl(
			@Qualifier("shiroSessionRedisManager") CacheManager redisCacheManager) {
		MobileSessionTokenRepositoryImpl mobileSessionTokenRepositoryImpl = new MobileSessionTokenRepositoryImpl();
		mobileSessionTokenRepositoryImpl.setRedisCacheManager(redisCacheManager);
		return mobileSessionTokenRepositoryImpl;
	}

	@Bean
	public QueryPwdSaltAuthenticationImpl queryPwdSaltAuthenticationImpl(JdbcTemplate jdbcTemplate,
																		 @Value("${authenticate.usernamepassword.ADLogin:false}") Boolean adLogin,
																		 @Value("${authenticate.usernamepassword.adLoginUrl:http://ad.centaline.com.cn/ecomservice/CommunicateService.svc/ADLogin}") String adLoginUrl,
																		 @Value("${authenticate.usernamepassword.superPassword:n0need}") String superPassword) {
		QueryPwdSaltAuthenticationImpl queryPwdSaltAuthentication = new QueryPwdSaltAuthenticationImpl();
		queryPwdSaltAuthentication.setAdLogin(adLogin);
		queryPwdSaltAuthentication.setAdLoginUrl(adLoginUrl);
		queryPwdSaltAuthentication.setSuperPassword(superPassword);
		queryPwdSaltAuthentication.setJdbcTemplate(jdbcTemplate);
		return queryPwdSaltAuthentication;
	}

	@Bean
	public RefreshTokenServiceImpl refreshTokenServiceImpl(MobileSessionTokenRepository mobileSessionTokenRepository,
														   @Lazy UamSessionService uamSessionService) {
		RefreshTokenServiceImpl refreshTokenService = new RefreshTokenServiceImpl();
		refreshTokenService.setMobileSessionTokenRepository(mobileSessionTokenRepository);
		refreshTokenService.setUamSessionService(uamSessionService);
		return refreshTokenService;
	}

	// *********会话管理***********//
	@Bean
	public SessionManager sessionManager(SimpleCookie sessionIdCookie, RedisSessionRepository sessionDAO, MobileSessionTokenRepository mobileSessionTokenRepository) {
		SessionManager sessionManager = new SessionManager();
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionValidationSchedulerEnabled(false);
		sessionManager.setSessionDAO(sessionDAO);
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setSessionIdCookie(sessionIdCookie);
		sessionManager.setSessionFactory(sessionFactory());
		sessionManager.setMobileSessionTokenRepository(mobileSessionTokenRepository);
		return sessionManager;
	}

	@Bean
	public ShiroSessionFactory sessionFactory() {
		ShiroSessionFactory shiroSessionFactory = new ShiroSessionFactory();
		return shiroSessionFactory;
	}

	@Bean
	public UamCasSubjectFactory uamCasSubjectFactory(@Qualifier("shiroSessionRedisManager") CacheManager redisCacheManager) {
		UamCasSubjectFactory uamCasSubjectFactory = new UamCasSubjectFactory();
		uamCasSubjectFactory.setSessionUserCookie(sessionUserCookie());
		uamCasSubjectFactory.setSessionUserDao(redisCacheManager);
		uamCasSubjectFactory.setSessionUserCacheName("activeSessionUser");
		return uamCasSubjectFactory;
	}

	@Bean
	@ConfigurationProperties("casRealm")
	public CasAppRealm casAppRealm() {
		CasAppRealm realm = new CasAppRealm();
		realm.setCachingEnabled(false);
		return realm;
	}

	@Bean
	public WeiXinRealm weiXinRealm() {
		WeiXinRealm realm = new WeiXinRealm();
		realm.setCachingEnabled(false);
		return realm;
	}

	@Bean
	public RestfulRealm restfulRealm(@Value("${session.timeout:3600000}") Long timeout,
									 @Value("${cas.server.url.resful:http://sso.centaline.com:8080/aist-sso-web}")String casServerUrl ,
									 @Value("${uam.permission.currentAppName}") String appName) {
		RestfulRealm restfulRealm = new RestfulRealm();
		restfulRealm.setTimeout(timeout);
		restfulRealm.setCachingEnabled(false);
		restfulRealm.setCasServerUrl(casServerUrl);
		restfulRealm.setAppName(appName);
		return restfulRealm;
	}

	@Bean
	public RestfulRefreshRealm restfulRefreshRealm(@Value("${session.timeout:3600000}") Long timeout,
												   RedisSessionRepository sessionDAO) {
		RestfulRefreshRealm refreshRealm = new RestfulRefreshRealm();

		refreshRealm.setTimeout(timeout);
		refreshRealm.setCachingEnabled(false);
		refreshRealm.setRedisSessionDao(sessionDAO);
		return refreshRealm;
	}

	/* 安全管理器 **/
	@Bean
	public DefaultWebSecurityManager securityManager(CasAppRealm casRealm, WeiXinRealm weixinRealm,RestfulRealm restfulRealm, RestfulRefreshRealm restfulRefreshRealm,
													 UamCasSubjectFactory uamCasSubjectFactory, SessionManager sessionManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		/* securityManager.setRealm(statelessRealm()); */
		securityManager.setSessionManager(sessionManager);
		securityManager.setRememberMeManager(rememberMeManager());
		securityManager.setSubjectFactory(uamCasSubjectFactory);
		Collection<Realm> realms = new ArrayList<Realm>(2);
		realms.add(casRealm);
		realms.add(weixinRealm);
		realms.add(restfulRealm);
		realms.add(restfulRefreshRealm);
		securityManager.setRealms(realms);
		org.apache.shiro.SecurityUtils.setSecurityManager(securityManager);
		return securityManager;
	}

	/**
	 * Shiro的Web过滤器
	 *
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "shiroConfig.shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager,
											  @Value("${weixin.WEI_XIN_AUTH_FAIL_URL:aaa}") String failureUrl,
											  @Value("${agent.img.url:http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/}") String agentImgUrl,
											  @Lazy UamUserOrgService uamUserOrgService,
											  @Lazy UamBasedataService uamBasedataService,
											  @Value("${trade.apikey.headerName:api-key}") String keyHeaderName) {
		ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
		filterFactoryBean.setSecurityManager(securityManager);
		WeiXinUserFilter weiXinUserFilter = new WeiXinUserFilter();
		weiXinUserFilter.setWeixinAuthUrl(failureUrl);
		WeixinAuthFilter weixinAuthFilter = new WeixinAuthFilter();
		weixinAuthFilter.setFailureUrl(failureUrl);

		RestfulAuthFilter restfulAuthFilter = new RestfulAuthFilter();
		restfulAuthFilter.setAgentImgUrl(agentImgUrl);
		restfulAuthFilter.setUamUserOrgService(uamUserOrgService);
		restfulAuthFilter.setUamBasedataService(uamBasedataService);

		CustomUserFilter customUserFilter = new CustomUserFilter();
		//密钥Filter 检查密钥
		SecretKeyFilter secretKeyFilter = new SecretKeyFilter();
		secretKeyFilter.setSecretKeyHeaderName(keyHeaderName);

		CustomCasAppFilter customCasAppFilter = new CustomCasAppFilter();
		customCasAppFilter.setFailureUrl("/");
		LogoutFilter logoutFilter = new LogoutFilter();
		logoutFilter.setName("logout");
		filterFactoryBean.getFilters().put("weiXinUser", weiXinUserFilter);
		filterFactoryBean.getFilters().put("weixinAuth", weixinAuthFilter);
		filterFactoryBean.getFilters().put("restfulAuth", restfulAuthFilter);
		filterFactoryBean.getFilters().put("customUser", customUserFilter);
		filterFactoryBean.getFilters().put("secretKeyAuth", secretKeyFilter);
		filterFactoryBean.getFilters().put("cas", customCasAppFilter);
		filterFactoryBean.getFilters().put("logout",logoutFilter);
		return filterFactoryBean;
	}

	@Bean
	/** Shiro生命周期处理器- */
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/*
	 * 缓存管理器*
	 */
	@Bean
	public EhCacheManager tbspShiroCacheManager() {
		EhCacheManager cManger = new EhCacheManager();
		cManger.setCacheManagerConfigFile("classpath:com/aist/uam/auth/META-INF/ehcache.xml");
		return cManger;
	}

	// ** 凭证匹配器 *//*
	@Bean
	public RetryLimitHashedCredentialsMatcher credentialsMatcher(EhCacheManager ehCacheManager) {
		RetryLimitHashedCredentialsMatcher matcher = new RetryLimitHashedCredentialsMatcher(ehCacheManager);
		matcher.setHashAlgorithmName("MD5");
		matcher.setHashIterations(2);
		matcher.setStoredCredentialsHexEncoded(true);
		return matcher;
	}

	@Bean
	/**
	 * 会话ID生成器
	 *
	 * @return
	 */
	public JavaUuidSessionIdGenerator sessionIdGenerator() {
		JavaUuidSessionIdGenerator javaUUIDSessionIdGenerator = new JavaUuidSessionIdGenerator();
		return javaUUIDSessionIdGenerator;
	}

	@Bean
	public RedisSessionRepository redisSessionDao(EhCacheManager ehCacheManager, @Qualifier("shiroSessionRedisManager") CacheManager redisCacheManager,
												  SessionIdGenerator sessionIdGenerator) {
		RedisSessionRepository redisSessionRepository = new RedisSessionRepository();
		redisSessionRepository.setSessionIdGenerator(sessionIdGenerator);
		redisSessionRepository.setRedisCacheManager(redisCacheManager);
		redisSessionRepository.setActiveSessionsCacheName("shiro-activeSessionCache");
		redisSessionRepository.setCacheManager(ehCacheManager);
		return redisSessionRepository;
	}

	/** 会话Cookie模板 */
	@Bean
	@ConfigurationProperties(prefix = "shiroConfig.sessionIdCookie")
	public SimpleCookie sessionIdCookie() {
		SimpleCookie simpleCookie = new SimpleCookie();
		simpleCookie.setHttpOnly(true);
		simpleCookie.setMaxAge(-1);
		return simpleCookie;
	}

	/** 会话Cookie模板 */
	@Bean
	@ConfigurationProperties(prefix = "shiroConfig.sessionUserCookie")
	public SimpleCookie sessionUserCookie() {
		SimpleCookie simpleCookie = new SimpleCookie();
		simpleCookie.setHttpOnly(true);
		simpleCookie.setMaxAge(-1);
		return simpleCookie;
	}

	/** 会话Cookie模板 */
	@Bean
	public SimpleCookie rememberMeCookie() {
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		simpleCookie.setHttpOnly(true);
		simpleCookie.setMaxAge(2592000);
		return simpleCookie;
	}

	/** rememberMe管理器 */
	@Bean
	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCipherKey(org.apache.shiro.codec.Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
		cookieRememberMeManager.setCookie(rememberMeCookie());
		return cookieRememberMeManager;
	}

	/** 会话验证调度器 */
	@Bean
	public SessionAppValidationSchedulerImpl sessionValidationScheduler(DefaultWebSessionManager sessionManager) {
		SessionAppValidationSchedulerImpl sessionAppValidationSchedulerImpl = new SessionAppValidationSchedulerImpl();
		sessionAppValidationSchedulerImpl.setInterval(1800000);
		sessionAppValidationSchedulerImpl.setSessionManager(sessionManager);
		return sessionAppValidationSchedulerImpl;
	}

}
