package first.ep.project.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import first.ep.project.model.UserClubType;
import first.ep.project.repository.UserClubTypeRepository;

@ExtendWith(MockitoExtension.class)
class UserClubTypeServiceImplTest {

    @Mock
    private UserClubTypeRepository userClubTypeRepository;

    @InjectMocks
    private UserClubTypeServiceImpl userClubTypeServiceImpl;

    private UserClubType testUserClubType;

    @BeforeEach
    void setUp() {
        testUserClubType = new UserClubType();
        testUserClubType.setId(1L);
        testUserClubType.setUser_id(1L);
        testUserClubType.setClubType_id(1L);
    }

    @Test
    void testSaveUserClubType() {
        when(userClubTypeRepository.save(testUserClubType)).thenReturn(testUserClubType);

        UserClubType actualUserClubType = userClubTypeServiceImpl.saveUserClubType(testUserClubType);

        assertEquals(testUserClubType, actualUserClubType);
    }



}
