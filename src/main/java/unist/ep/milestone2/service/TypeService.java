package unist.ep.milestone2.service;

import unist.ep.milestone2.model.Type;
import java.util.List;
import java.util.Optional;

public interface TypeService {
    List<Type> getAllClubTypes();
    Optional<Type> getClubTypeById(Long id);
    Type getClubTypeByName(String name);
    Type addClubType(Type clubType);
    Optional<Type> updateClubType(Type clubType);
    Long deleteClubTypeById(Long id);
}
