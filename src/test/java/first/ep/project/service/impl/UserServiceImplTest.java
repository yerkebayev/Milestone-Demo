package first.ep.project.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import first.ep.project.model.ClubType;
import first.ep.project.model.User;
import first.ep.project.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");
        testUser.setEmail("test@test.com");
    }

    @Test
    void testGetUserByIdWhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        Optional<User> actualUser = userServiceImpl.getUserById(1L);

        assertEquals(Optional.of(testUser), actualUser);
    }

    @Test
    void testGetUserByIdWhenUserDoesNotExist() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<User> actualUser = userServiceImpl.getUserById(2L);

        assertEquals(Optional.empty(), actualUser);
    }

    @Test
    void testSaveUser() {
        when(userRepository.save(testUser)).thenReturn(testUser);

        User actualUser = userServiceImpl.saveUser(testUser);

        assertEquals(testUser, actualUser);
    }

    @Test
    void testDeleteUserByIdWhenUserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);

        Long actual = userServiceImpl.deleteUserById(1L);

        assertEquals(1L, actual);
    }

    @Test
    void testDeleteUserByIdWhenUserDoesNotExist() {
        when(userRepository.existsById(2L)).thenReturn(false);

        Long actual = userServiceImpl.deleteUserById(2L);

        assertEquals(-1L, actual);
    }

    @Test
    void testGetPreferredClubTypes() {
        ClubType clubType = new ClubType();
        clubType.setId(1L);
        clubType.setName("Test Club Type");
        List<ClubType> expectedClubTypes = Collections.singletonList(clubType);
        when(userRepository.getPreferredClubTypes(1L)).thenReturn(expectedClubTypes);

        List<ClubType> actualClubTypes = userServiceImpl.getPreferredClubTypes(testUser);
        assertEquals(expectedClubTypes, actualClubTypes);
    }

    @Test
    void testGetUserByEmail() {
        when(userRepository.getUserByEmail("test@test.com")).thenReturn(testUser);

        User actualUser = userServiceImpl.getUserByEmail("test@test.com");

        assertEquals(testUser, actualUser);
    }
}
