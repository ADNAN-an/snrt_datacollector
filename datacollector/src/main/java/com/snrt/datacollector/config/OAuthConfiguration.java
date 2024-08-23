package com.snrt.datacollector.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserDetailsService userCostumDetailsService;
	
	@Value("${snrt.properties.oauth2.username}")
	private String tokenUsername;
	@Value("${snrt.properties.oauth2.password}")
	private String tokenPassword;
	@Value("${snrt.properties.oauth2.token-expires-time}")
	private int tokenExpiresTime;
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.userDetailsService(userCostumDetailsService)
			.accessTokenConverter(tokenEnhancer())
			.authenticationManager(authenticationManager);	
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
				.inMemory()
					.withClient(this.tokenUsername)
					.secret(passwordEncoder.encode(this.tokenPassword))
					.authorizedGrantTypes("password")
					.scopes("read","write","trust")
					.accessTokenValiditySeconds(this.tokenExpiresTime);
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("isAuthenticated()");
	}

	
	@Bean
	public JwtAccessTokenConverter tokenEnhancer() {
	   JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	   return converter;
	}



}
