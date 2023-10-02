package com.m2l.meta.config;

import com.m2l.meta.config.filter.CustomAuthenticationFilter;
import com.m2l.meta.config.filter.CustomAuthorizationFilter;
import com.m2l.meta.config.oauth.CustomOAuth2UserService;
import com.m2l.meta.config.oauth.OAuthLoginSuccessHandler;
import com.m2l.meta.config.oauth.UserPrincipal;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.method.HandlerMethod;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
@EnableMethodSecurity
public class SecurityConfiguration implements EnvironmentAware {

    public static String[] PUBLIC_URL = {"/login", "/logout", "/user/register", "/user/check", "/forgot-password", "/reset-password", "/v2/api-docs", "/socket/**",
            "/admin/user-password", "/admin/delete/{id}", "/verify-otp",
            "/configuration/ui",
            "/api-docs/**",
            "/swagger-ui-custom.html",
            "/favicon.ico",
            "/webjars/**"};
    @Autowired
    private CustomOAuth2UserService oauth2UserService;

    @Autowired
    private OAuthLoginSuccessHandler oauthLoginSuccessHandler;

    public static String[] IGNORE_URL = {};
    @Value("${allow.origins}")
    private String allowOrigins;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private Environment environment;

    @Override
    public void setEnvironment(final Environment environment) {
        this.environment = environment;
    }

    /**
     * Configure.
     *
     * @param http the http
     * @throws Exception the exception
     */
    @Bean
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        // Entry points
        // uncheck authorizeRequest
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(request -> {
                String authToken = request.getHeader("Authorization");
                return authToken == null;
            }).permitAll();
            auth.requestMatchers(PUBLIC_URL).permitAll();
            auth.anyRequest().authenticated();
//         http.authorizeRequests().anyRequest().permitAll();
        });
//        http.formLogin().successHandler(successHandler());
        http.oauth2Login()
                .userInfoEndpoint()
                .userService(oauth2UserService)
                .and()
                .successHandler(oauthLoginSuccessHandler);
        http.logout(logout -> {
            logout.addLogoutHandler(userLogoutHandler());
            logout.logoutSuccessHandler((request, response, authentication) ->
                                response.setStatus(HttpStatus.OK.value()))
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", HttpMethod.GET.toString()))
                    .clearAuthentication(true).deleteCookies();
        });
//        http.sessionManagement().maximumSessions(1)
//                .maxSessionsPreventsLogin(false)          // 4
//                .expiredUrl("/login?expired");        // 5
//                .sessionRegistry(getSessionRegistry());
        http.apply(customDsl());
        return http.build();
    }

    @Bean
    public UserLogoutHandler userLogoutHandler(){
        return new UserLogoutHandler();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        String[] allowOriginsArr = allowOrigins.split(",");
        List<String> allowOrigins = Arrays.asList(allowOriginsArr);
        configuration.setAllowedOriginPatterns(allowOrigins);
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public CustomAuthorizationFilter authorizationFilter() throws Exception {
        return new CustomAuthorizationFilter(environment.getProperty("app.login.jwtPrefix"), PUBLIC_URL);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(IGNORE_URL);
    }

    public MyCustomDsl customDsl() {
        return new MyCustomDsl();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter(authenticationManager);
            http.addFilter(authenticationFilter);
            http.addFilterBefore(authorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//            http.addFilterBefore(new CustomLogoutFilter(), SecurityContextPersistenceFilter.class);
        }
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("MARTIAL-ART BE API")
                        .description("Martial-art be sample application")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://ma-be.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Martial-art be Documentation")
                        .url("https://ma-be.org/docs"));
    }

    @Component
    public static class GlobalHeaderOperationCustomizer implements OperationCustomizer {
        @Override
        public Operation customize(Operation operation, HandlerMethod handlerMethod) {
            Parameter accessTokenHeader = new Parameter().in(ParameterIn.HEADER.toString()).name("access-token")
                    .description("Access Token: required").schema(new StringSchema()).required(false);
            Parameter refreshTokenHeader = new Parameter().in(ParameterIn.HEADER.toString()).name("refresh-token")
                    .description("Refresh Token").schema(new StringSchema()).required(false);
            Parameter bearerHeader = new Parameter().in(ParameterIn.HEADER.toString()).name("prefix")
                    .description("Bearer prefix: required").example("Bearer").schema(new StringSchema()).required(false);
            operation.addParametersItem(accessTokenHeader);
            operation.addParametersItem(refreshTokenHeader);
            operation.addParametersItem(bearerHeader);
            return operation;
        }
    }

}
