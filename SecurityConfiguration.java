package com.itv.springdatarest;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;




@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
    prePostEnabled=true,
    jsr250Enabled = true,
    securedEnabled = true
    )
  

public class SecurityConfiguration {
    
   

    @Bean
    public SecurityFilterChain configure(HttpSecurity http)throws Exception
    {
        http
        .csrf().disable()
        .authorizeHttpRequests()
        .requestMatchers("/user_pets/**","/addresses/**")
         .permitAll()
         .requestMatchers("/products/**")
         .hasRole("HR")
         .requestMatchers("/myorders/**")
         .hasRole("IT")
        .requestMatchers(HttpMethod.POST,"/register").permitAll()
         .anyRequest().authenticated();
        //   .and()
        //   .authenticationProvider(daoAuthenticationProvider());
          
          return http.build();
      }    
    
    @Bean
    public UserDetailsService user()
        {
            UserDetails user1= User.builder()
                                    .username("mittal")
                                    .password(passwordEncoder().encode("mittal123"))
                                    .roles("HR")
                                    .build();
                                    UserDetails user2= User.builder()
                                    .username("kalpak")
                                    .password(passwordEncoder().encode("kalpak123"))
                                    .roles("IT")
                                    .build();
                                    UserDetails user3= User.builder()
                                    .username("admin")
                                    .password(passwordEncoder().encode("admin123"))
                                    .roles("HR","IT","ADMIN")
                                    .build();
                    
             return new InMemoryUserDetailsManager(user1,user2,user3);
        
    } 

    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    
//    @Autowired
//    private MyUserDetailsService detailsService;

//     @Bean
//     public DaoAuthenticationProvider daoAuthenticationProvider(){
//         DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//         provider.setUserDetailsService(this.detailsService);
//         provider.setPasswordEncoder(this.passwordEncoder());
//         return provider;
// }
}
