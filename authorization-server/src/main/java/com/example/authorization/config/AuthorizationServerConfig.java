//package com.example.authorization.config;
//
//import com.nimbusds.jose.jwk.JWKSet;
//import com.nimbusds.jose.jwk.RSAKey;
//import com.nimbusds.jose.jwk.source.JWKSource;
//import com.nimbusds.jose.proc.SecurityContext;
//import java.security.KeyPair;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.util.UUID;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
//import org.springframework.security.oauth2.core.oidc.OidcScopes;
//import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
//import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
//import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration(proxyBeanMethods = false)
//@PropertySource("properties.properties")
//public class AuthorizationServerConfig {
//  @Value("${keyStorePath}")
//  private static String keyStorePath;
//  @Value("${keyStorePass}")
//  private static String keyStorePass;
//
//  private static final KeyStoreKeyFactory keyFactory = new KeyStoreKeyFactory(new FileSystemResource(keyStorePath), keyStorePass.toCharArray());
//
//  private static RSAKey generateRsa() {
//    KeyPair keyPair = keyFactory.getKeyPair("mykey");
//    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//    return new RSAKey.Builder(publicKey)
//        .privateKey(privateKey)
//        .keyID(UUID.randomUUID().toString())
//        .build();
//  }
//
//  @Bean
//  @Order(Ordered.HIGHEST_PRECEDENCE)
//  public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
//    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
//    return http.formLogin(Customizer.withDefaults()).build();
//  }
//
//  @Bean
//  public RegisteredClientRepository registeredClientRepository() {
//    RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
//        .clientId("gateway")
//        .clientSecret("{noop}secret")
//        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//        .redirectUri("http://127.0.0.1:8080/login/oauth2/code/gateway")
//        .scope(OidcScopes.OPENID)
//        .scope("resource.read")
//        .build();
//
//    return new InMemoryRegisteredClientRepository(registeredClient);
//  }
//
//  @Bean
//  public JWKSource<SecurityContext> jwkSource() {
//    RSAKey rsaKey = generateRsa();
//    JWKSet jwkSet = new JWKSet(rsaKey);
//    return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
//  }
//
//  @Bean
//  public ProviderSettings providerSettings() {
//    return ProviderSettings.builder()
//        .issuer("http://localhost:9000")
//        .build();
//  }
//}