package br.com.jagi.junitlearn.resource;

import br.com.jagi.junitlearn.domain.User;
import br.com.jagi.junitlearn.service.UserService;
import br.com.jagi.junitlearn.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserResource {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> findById(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok().body(userService.findById(userId));
    }
}
