package com.example.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class LdapTemplateConfig {

    private final Logger log = LoggerFactory.getLogger(LdapTemplateConfig.class);

    @Bean(name = "ldapTemplate")
    // @Scope("singleton")
    public LdapTemplate ldapTemplate() {
        LdapTemplate ldapTemplate = new LdapTemplate(ldapContextSource());
        return ldapTemplate;
    }

    @Bean(name = "contextSource")
    // @Scope("singleton")
    public LdapContextSource ldapContextSource() {

        String url = "ldap://127.0.0.1:389";
        String base = "DC=intranet,DC=demo,DC=com";

        if (isConfigurationValid(url, base)) {
            LdapContextSource ldapContextSource = new LdapContextSource();
            ldapContextSource.setUrl(url);
            ldapContextSource.setBase(base);
            ldapContextSource.setUserDn(
                    "CN=Test User,OU=Common Users OU,OU=RDP enabled Users OU,DC=intranet,DC=demo,DC=com");
            ldapContextSource.setPassword("password");
            ldapContextSource.setReferral("follow");
            // lcs.setPooled(false);
            // lcs.setDirObjectFactory(DefaultDirObjectFactory.class);
            ldapContextSource.afterPropertiesSet();
            return ldapContextSource;
        }
        return null;
    }

    public boolean isConfigurationValid(String url, String base) {
        if ((url == null) || url.isEmpty() || (base == null) || base.isEmpty()) {
            log.error("Warning! Your LDAP server is not configured.");
            log.info("Did you configure your LDAP settings in your application.yml?");
            return false;
        } else {
            return true;
        }
    }
}