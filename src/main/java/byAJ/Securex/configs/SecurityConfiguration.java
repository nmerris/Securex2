package byAJ.Securex.configs;

import byAJ.Securex.SSUserDetailsService;
import byAJ.Securex.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    private SSUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new SSUserDetailsService(userRepository);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()

                // Librarians can access...
                // if you don't add the /**, any authenticated user will be able to go to /books/edit/3 or whatever
                .antMatchers("/books/add/**", "/books/edit/**", "/books/delete/**")
                    .access("hasRole('ROLE_LIBRARIAN')")

                // users can access...
                .antMatchers("/books/list")
                    .access("hasRole('ROLE_USER') or hasRole('ROLE_LIBRARIAN')")

                // anyone can access...
                .antMatchers("/", "/register")
                    .permitAll()

                // also anyone can access static folders
                .antMatchers( "/css/**", "/js/**")
                    .permitAll()

                .anyRequest().authenticated();


        // login/out
        http
                .formLogin().failureUrl("/login?error")
                // there is NO post route for /login, I tried and it never gets called, instead the defaultSuccessUrl route gets called
                .defaultSuccessUrl("/justloggedin")
                .and()
                .formLogin().loginPage("/login")
                    .permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/loggedout")
                    .permitAll();

        http
                .csrf().disable();

        http
                .headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsServiceBean());
//        auth.inMemoryAuthentication().withUser("user").password("pass").roles("USER");
    }
}
