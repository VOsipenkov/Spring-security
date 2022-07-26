package ru.home.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/authenticated/**").authenticated()
            .and()
            .formLogin()
            .and()
            .logout().logoutSuccessUrl("/");
    }

    //    //    In memory users
    //    @Bean
    //    public UserDetailsService userDetailsService() {
    //        UserDetails user = User.builder()
    //            .username("user")
    //            .password("{bcrypt}$2a$12$7AD7WFM6eMa0vbnUrS6oze3enyn4Jr52lC50f3DcO.hycLUiVAwsW")
    //            .roles("USER")
    //            .build();
    //
    //        UserDetails admin = User.builder()
    //            .username("admin")
    //            .password("{bcrypt}$2a$12$7AD7WFM6eMa0vbnUrS6oze3enyn4Jr52lC50f3DcO.hycLUiVAwsW")
    //            .roles("ADMIN", "USER")
    //            .build();
    //
    //        return new InMemoryUserDetailsManager(user, admin);
    //    }

    //    In database persist
    @Bean
    public JdbcUserDetailsManager userDetailsService() {
        UserDetails user = User.builder()
            .username("user")
            .password("{bcrypt}$2a$12$7AD7WFM6eMa0vbnUrS6oze3enyn4Jr52lC50f3DcO.hycLUiVAwsW")
            .roles("USER")
            .build();

        UserDetails admin = User.builder()
            .username("admin")
            .password("{bcrypt}$2a$12$7AD7WFM6eMa0vbnUrS6oze3enyn4Jr52lC50f3DcO.hycLUiVAwsW")
            .roles("ADMIN", "USER")
            .build();

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        if (jdbcUserDetailsManager.userExists(user.getUsername())) {
            jdbcUserDetailsManager.deleteUser(user.getUsername());
        }
        if (jdbcUserDetailsManager.userExists(admin.getUsername())) {
            jdbcUserDetailsManager.deleteUser(admin.getUsername());
        }
        jdbcUserDetailsManager.createUser(user);
        jdbcUserDetailsManager.createUser(admin);
        return jdbcUserDetailsManager;
    }
}
