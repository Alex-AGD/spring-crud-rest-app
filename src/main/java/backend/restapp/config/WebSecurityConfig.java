package backend.restapp.config;

import backend.restapp.model.Person;
import backend.restapp.repo.PersonRepo;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                .mvcMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

    @Bean
    public PrincipalExtractor principalExtractor(PersonRepo personRepo) {
        return map -> {
            String id = (String) map.get("sub");

            Person person = personRepo.findById(id).orElseGet(() -> {
                Person newPerson = new Person();
                newPerson.setId(id);
                newPerson.setUsername((String) map.get("name"));
                newPerson.setEmail((String) map.get("email"));
                newPerson.setGender((String) map.get("gender"));
                newPerson.setLocale((String) map.get("locale"));
                newPerson.setUserPicture((String) map.get("picture"));
                return newPerson;
            });
            person.setLastVisitDate(LocalDateTime.now());
            return personRepo.save(person);
        };
    }
}
