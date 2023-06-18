package unist.ep.milestone2.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Sort;
import unist.ep.milestone2.model.ClubType;
import unist.ep.milestone2.repository.TypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TypeServiceImplTest {
    private TypeServiceImpl typeService;

    @Mock
    private TypeRepository typeRepository;

    @BeforeEach
    public void setUp() {
        typeRepository = mock(TypeRepository.class);
        typeService = new TypeServiceImpl(typeRepository);
    }

    @Test
    public void testGetAllClubTypes_ReturnsListOfClubTypes() {
        // Arrange
        List<ClubType> expectedClubTypes = new ArrayList<>();
        expectedClubTypes.add(new ClubType(1L, "Type 1"));
        expectedClubTypes.add(new ClubType(2L, "Type 2"));

        when(typeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(expectedClubTypes);

        // Act
        List<ClubType> actualClubTypes = typeService.getAllClubTypes();

        // Assert
        assertEquals(expectedClubTypes, actualClubTypes);
        verify(typeRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Test
    public void testGetClubTypeById_WithExistingId_ReturnsClubType() {
        // Arrange
        Long typeId = 1L;
        ClubType expectedClubType = new ClubType(typeId, "Type 1");

        when(typeRepository.findById(typeId)).thenReturn(Optional.of(expectedClubType));

        // Act
        Optional<ClubType> actualClubType = typeService.getClubTypeById(typeId);

        // Assert
        assertEquals(Optional.of(expectedClubType), actualClubType);
        verify(typeRepository, times(1)).findById(typeId);
    }

    @Test
    public void testGetClubTypeById_WithNonExistingId_ReturnsEmptyOptional() {
        // Arrange
        Long typeId = 1L;

        when(typeRepository.findById(typeId)).thenReturn(Optional.empty());

        // Act
        Optional<ClubType> actualClubType = typeService.getClubTypeById(typeId);

        // Assert
        assertEquals(Optional.empty(), actualClubType);
        verify(typeRepository, times(1)).findById(typeId);
    }

}
