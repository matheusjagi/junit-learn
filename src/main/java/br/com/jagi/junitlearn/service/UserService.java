package br.com.jagi.junitlearn.service;

import br.com.jagi.junitlearn.domain.User;
import br.com.jagi.junitlearn.repositories.UserRepository;
import br.com.jagi.junitlearn.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public UserDTO findById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado."));

        return mapper.map(user, UserDTO.class);
    }

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> mapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO create(UserDTO userDTO) {
        validaEmailCadastrado(userDTO);
        User user = userRepository.save(mapper.map(userDTO, User.class));
        return mapper.map(user, UserDTO.class);
    }

    private void validaEmailCadastrado(UserDTO userDTO) {
        Optional<User> user = userRepository.findByEmail(userDTO.getEmail());

        if (user.isPresent() && !user.get().getId().equals(userDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail já cadastrado.");
        }
    }

    public UserDTO update(UserDTO userDTO) {
        if (Objects.isNull(userDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado para atualização");
        }

        return create(userDTO);
    }

    public void delete(Integer userId) {
        findById(userId);
        userRepository.deleteById(userId);
    }
}
