package br.com.jagi.junitlearn.config;

import br.com.jagi.junitlearn.domain.User;
import br.com.jagi.junitlearn.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public void startDB() {
        User user1 = new User(null, "Jagi", "jagi@gmail.com", "123");
        User user2 = new User(null, "Iara", "iara@gmail.com", "123");

        userRepository.saveAll(List.of(user1, user2));
    }
}
