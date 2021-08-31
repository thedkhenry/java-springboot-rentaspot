package com.henrysican.rentaspot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppUserDetailsService appUserDetailsService;

    @Autowired
    public AppSecurityConfig(AppUserDetailsService appUserDetailsService){
        this.appUserDetailsService = appUserDetailsService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(appUserDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(4));
        return provider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user", "/user/editProfile", "/user/saveProfile",
                                        "/reservations", "/reservations/reservation/{\\d+}", "/reservations/{action:[a-z]+}/{\\d+}/{\\d+}",
                                        "/hostinglist", "/create", "/update", "/edit/{\\d+}",
                                        "/review/**").hasAnyAuthority("ROLE_USER")
                .antMatchers("/reservations/**", "/user/**", "/search", "/location/{\\d+}").permitAll()
                .antMatchers("/","/home").permitAll()
                .and()
                .formLogin().loginPage("/login").usernameParameter("email").passwordParameter("password").loginProcessingUrl("/login/authenticate").defaultSuccessUrl("/").failureUrl("/login?error=true").permitAll()
                .and()
                .logout().invalidateHttpSession(true).clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").permitAll()
                .and().exceptionHandling().accessDeniedPage("/403");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/imgs/**");
    }
}
