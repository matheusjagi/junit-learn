package br.com.jagi.junitlearn.service;

import br.com.jagi.junitlearn.domain.User;
import br.com.jagi.junitlearn.repositories.UserRepository;
import br.com.jagi.junitlearn.service.dto.UserDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    public static final Integer ID      = 1;
    public static final String NAME     = "Jagi";
    public static final String EMAIL    = "jagi@gmail.com";
    public static final String PASSWORD = "123";
    public static final String USUARIO_NAO_ENCONTRADO = "Usuario não encontrado";
    public static final int INDEX_FIRST = 0;
    public static final String EMAIL_CADASTRADO = "E-mail já cadastrado";

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper mapper;

    private User user;

    private UserDTO userDTO;

    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
        mockMapper();
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        when(userRepository.findById(anyInt())).thenReturn(optionalUser);

        UserDTO response = userService.findById(ID);

        assertNotNull(response);
        assertEquals(UserDTO.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {
        when(userRepository.findById(anyInt())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, USUARIO_NAO_ENCONTRADO));

        try {
            userService.findById(ID);
        } catch (ResponseStatusException exception) {
            assertEquals(ResponseStatusException.class, exception.getClass());
            assertEquals(USUARIO_NAO_ENCONTRADO, exception.getReason());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserDTO> response = userService.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(UserDTO.class, response.get(INDEX_FIRST).getClass());
        assertEquals(ID, response.get(INDEX_FIRST).getId());
        assertEquals(NAME, response.get(INDEX_FIRST).getName());
        assertEquals(EMAIL, response.get(INDEX_FIRST).getEmail());
        assertEquals(PASSWORD, response.get(INDEX_FIRST).getPassword());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        when(userRepository.save(any())).thenReturn(user);

        UserDTO response = userService.create(userDTO);

        assertNotNull(response);
        assertEquals(UserDTO.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenCreateThenReturnAnDataIntegrityViolationException() {
        when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);

        try {
            optionalUser.ifPresent(user -> user.setId(2));
            userService.create(userDTO);
        } catch (ResponseStatusException exception) {
            assertEquals(ResponseStatusException.class, exception.getClass());
            assertEquals(EMAIL_CADASTRADO, exception.getReason());
        }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(userRepository.save(any())).thenReturn(user);

        UserDTO response = userService.create(userDTO);

        assertNotNull(response);
        assertEquals(UserDTO.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenUpdateThenReturnAnDataIntegrityViolationException() {
        when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);

        try {
            optionalUser.ifPresent(user -> user.setId(2));
            userService.create(userDTO);
        } catch (ResponseStatusException exception) {
            assertEquals(ResponseStatusException.class, exception.getClass());
            assertEquals(EMAIL_CADASTRADO, exception.getReason());
        }
    }

    @Test
    void deleteWithSuccess() {
        when(userRepository.findById(anyInt())).thenReturn(optionalUser);
        doNothing().when(userRepository).deleteById(anyInt());

        userService.delete(ID);

        verify(userRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteWithObjectNotFoundException() {
        when(userRepository.findById(anyInt())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, USUARIO_NAO_ENCONTRADO));

        try {
            userService.delete(ID);
        } catch (ResponseStatusException exception) {
            assertEquals(ResponseStatusException.class, exception.getClass());
            assertEquals(USUARIO_NAO_ENCONTRADO, exception.getReason());
        }
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }

    private void mockMapper() {
        when(mapper.map(any(), eq(UserDTO.class))).thenReturn(userDTO);
        when(mapper.map(any(), eq(User.class))).thenReturn(user);
    }
}