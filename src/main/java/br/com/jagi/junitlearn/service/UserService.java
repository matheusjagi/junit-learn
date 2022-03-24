package br.com.jagi.junitlearn.service;

import br.com.jagi.junitlearn.domain.User;
import br.com.jagi.junitlearn.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User findById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
