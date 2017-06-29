package lol.mifan.myblog.config;

import lol.mifan.myblog.auth.*;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.SimpleSessionFactory;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.session.NoSessionCreationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 米饭 on 2017-06-06.
 */
@Configuration
public class ShiroConfig {

    @Bean
    public CacheManager ehCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return ehCacheManager;
    }


    @Bean
    public HashService defaultHashService () {
        DefaultHashService defaultHashService = new DefaultHashService();
        defaultHashService.setHashIterations(2);
        return defaultHashService;
    }

    @Bean
    public PasswordService defaultPasswordService(HashService defaultHashService) {
        DefaultPasswordService defaultPasswordService = new DefaultPasswordService();
        defaultPasswordService.setHashService(defaultHashService);
        return defaultPasswordService;
    }

    @Bean
    public CredentialsMatcher passwordMatcher(PasswordService defaultPasswordService) {
//        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
//        matcher.setHashAlgorithmName("sha-256");
//        matcher.setHashIterations(2);

        PasswordMatcher pm = new PasswordMatcher();
        pm.setPasswordService(defaultPasswordService);
        return pm;
    }


    @Bean
    public Realm formRealm(CredentialsMatcher passwordMatcher) {
        FormRealm formRealm1 = new FormRealm();
        formRealm1.setCredentialsMatcher(passwordMatcher);
        formRealm1.setAuthenticationTokenClass(UsernamePasswordToken.class);
        formRealm1.setCachingEnabled(true);
        formRealm1.setAuthorizationCachingEnabled(true);
        formRealm1.setAuthorizationCacheName("authorization");
        return formRealm1;
    }

    @Bean
    public Realm tokenRealm() {
        TokenRealm tokenRealm = new TokenRealm();
        tokenRealm.setAuthenticationTokenClass(DigestToken.class);
        tokenRealm.setCachingEnabled(true);
        tokenRealm.setAuthorizationCachingEnabled(true);
        tokenRealm.setAuthorizationCacheName("authorization");
        return tokenRealm;
    }




    @Bean
    public SecurityManager defaultWebSecurityManager(CacheManager ehCacheManager,
                                                     Realm formRealm, Realm tokenRealm) {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(new DefaultWebSessionManager());
        securityManager.setCacheManager(ehCacheManager);
        List<Realm> realms = new ArrayList<>();
        realms.add(formRealm);
        realms.add(tokenRealm);
        securityManager.setRealms(realms);
        return securityManager;
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, Filter statelessAuthcFilter) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/");

        Map<String, String> filterChainDefinitionMap = new HashMap<>();
//        filterChainDefinitionMap.put("/tags/**", "authc");
//        filterChainDefinitionMap.put("/users/token", "saf,authc");
        filterChainDefinitionMap.put("/**", "noSessionCreation,saf");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);


        Map<String, Filter> filtersMap = new HashMap<>();
        filtersMap.put("saf", statelessAuthcFilter);
        shiroFilterFactoryBean.setFilters(filtersMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor AuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
