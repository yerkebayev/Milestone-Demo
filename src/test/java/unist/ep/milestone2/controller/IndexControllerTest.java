package unist.ep.milestone2.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import unist.ep.milestone2.model.User;
import unist.ep.milestone2.service.UserService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IndexControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private IndexController indexController;

    @Test
    public void testLoginWithValidCredentials() {
        String email = "diamond@unist.ac.kr";
        String password = "ufc123";
        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setPassword(password);
        when(userService.getUserByEmail(email)).thenReturn(user);

        ResponseEntity<Long> response = indexController.login(email, password);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user.getId(), response.getBody());
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        String email = "diamond@unist.ac.kr";
        String password = "ufc123";
        when(userService.getUserByEmail(email)).thenReturn(null);

        ResponseEntity<Long> response = indexController.login(email, password);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(-1L, response.getBody());
    }

    @Test
    public void testAddUserWithNewEmail() {
        User user = new User();
        user.setEmail("aktobe@unist.ac.kr");
        user.setPassword("123hey");
        when(userService.getUserByEmail(user.getEmail())).thenReturn(null);
        when(userService.saveUser(user)).thenReturn(user);

        ResponseEntity<?> response = indexController.addUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testAddUserWithExistingEmail() {
        User user = new User();
        user.setEmail("diamond@unist.ac.kr");
        user.setPassword("ufc123");
        User existingUser = new User();
        existingUser.setEmail("diamond@unist.ac.kr");
        when(userService.getUserByEmail(user.getEmail())).thenReturn(existingUser);

        ResponseEntity<?> response = indexController.addUser(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("There is already a user with this email", response.getBody());
    }
}
