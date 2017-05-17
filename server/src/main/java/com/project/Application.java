package com.project;

import com.project.user.User;
import com.project.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by Adas on 2017-03-28.
 */
@SpringBootApplication
@EnableNeo4jRepositories
public class Application {

    private final static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(
                           UserRepository userRepository) {
        return args -> {
            userRepository.deleteAll();
            User admin = new User("admin@admin.pl", "admin", "pawel", "noga");
            User pawel = new User("pawel@admin.pl", "pawel", "adas", "adam");
            User marek = new User("marek@admin.pl", "marek", "marek", "adam");
            admin.isFriendWith(pawel);
            admin.isFriendWith(marek);
            pawel.isFriendWith(marek);

            pawel.setLatitude(2.0);
            pawel.setLongitude(2.0);
            admin.setLatitude(1.0);
            admin.setLongitude(1.0);
            marek.setLatitude(3.0);
            marek.setLongitude(3.0);

            pawel.shareLocationWith(marek);
            admin.shareLocationWith(pawel);
            userRepository.save(admin);
            userRepository.save(pawel);
            userRepository.save(marek);
        };
    }

    @Configuration
    class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

        @Autowired
        UserRepository userRepository;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService());
        }

        @Bean
        UserDetailsService userDetailsService(){
            return new UserDetailsService() {
                @Override
                public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                    User person = userRepository.findByEmail(username);
                    if (person != null) {
                        return new org.springframework.security.core.userdetails.User(person.getEmail(),
                                person.getPassword(),
                                true, true, true, true,
                                AuthorityUtils.createAuthorityList("USER"));
                    } else {
                        throw new UsernameNotFoundException
                                ("Could not find the user '" +username + "'");
                    }
                }
            };
        }
    }

    @EnableWebSecurity
    @Configuration
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
            .authorizeRequests()
                    .antMatchers("/auth/**").permitAll()
                    .antMatchers("/api/**").authenticated()
            .and()
                    .httpBasic().and().
                    csrf().disable()
                  ;
        }

    }
}
