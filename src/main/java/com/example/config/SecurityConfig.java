package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;




import javax.annotation.Resource;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    	/*auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());*/
    }
   /* @Override
    public void configure(WebSecurity web) throws Exception {
    	System.out.println("--------------Security Config----------");
      web.ignoring()
        .antMatchers(HttpMethod.OPTIONS);
    }*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	//System.out.println("-------I----------");
    	System.out.println("-------------- Enter Security Configuere adapter----------");
    	/* http
           // your security config here
           .authorizeRequests()
           .antMatchers(HttpMethod.TRACE, "/**").denyAll()
           .antMatchers("/**").authenticated()
           .anyRequest().permitAll()
           .and().httpBasic()
           .and().headers().frameOptions().disable()
           .and().csrf().disable()
           .headers()
           // the headers you want here. This solved all my CORS problems! 
           .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "http://localhost:4200"))
           .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "POST, GET"))
           .addHeaderWriter(new StaticHeadersWriter("Access-Control-Max-Age", "3600"))
           .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Credentials", "true"))
           .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers", "*"));
    	 System.out.println("--------------Exit Security Configuere adapter----------");*/
    	// http.cors();
    	 http
         .addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class). //adds your custom CorsFilter
         
         authorizeRequests()
         .antMatchers(HttpMethod.OPTIONS, "/oauth/token").permitAll();
       // .csrf().disable()
        // .anonymous().disable()
         //.authorizeRequests()
         //.antMatchers("/**").permitAll().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
        // .antMatchers("/oauth/token").permitAll();
    	 System.out.println("-------------- Exit Security Configuere adapter----------");
}
           
    

   /* @Bean
    CORSFilter corsFilter() {
        CORSFilter filter = new CORSFilter();
        return filter;
    }
*/

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

   /* @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }*/
 
  /* @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
      //  config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }*/

 /*  @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
      //  config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }*/
 /* @Bean
   public FilterRegistrationBean corsFilterRegistration() {
	  System.out.println("-----------------Enter cors filter registration----------------");
	   UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       CorsConfiguration config = new CorsConfiguration();
       config.setAllowCredentials(true);
       //config.addAllowedOrigin("*");
       config.addAllowedHeader("*");
       config.addAllowedMethod("*");
    //  config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
       source.registerCorsConfiguration("/**", config);
       FilterRegistrationBean bean = new FilterRegistrationBean(new CORSFilter());
       bean.setName("cors");
       bean.addUrlPatterns("/*");
       bean.setOrder(0);
       System.out.println("-----------------Exit cors filter registration----------------");
       return bean;
       
   }*/
    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
    return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}
