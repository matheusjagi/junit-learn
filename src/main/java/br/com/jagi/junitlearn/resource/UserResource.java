package br.com.jagi.junitlearn.resource;

import br.com.jagi.junitlearn.service.UserService;
import br.com.jagi.junitlearn.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> findById(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok().body(userService.findById(userId));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
        UserDTO user = userService.create(userDTO);
        return ResponseEntity.created(URI.create("/api/users/" + user.getId())).body(user);
    }

    @PutMapping
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok().body(userService.update(userDTO));
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Void> delete(@PathVariable("userId") Integer userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }
}
