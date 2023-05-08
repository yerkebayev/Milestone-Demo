package unist.ep.milestone2.service.impl;

import unist.ep.milestone2.model.Type;
import unist.ep.milestone2.repository.TypeRepository;
import unist.ep.milestone2.service.TypeService;

import java.util.List;
import java.util.Optional;

public class TypeServiceImpl implements TypeService {
    private final TypeRepository typeRepository;
    TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }
    @Override
    public List<Type> getAllClubTypes() {
        return typeRepository.findAll();
    }

    @Override
    public Optional<Type> getClubTypeById(Long id) {
        return typeRepository.findById(id);
    }

    @Override
    public Type getClubTypeByName(String name) {
        return typeRepository.getTypeByName(name);
    }

    @Override
    public Type addClubType(Type clubType) {
        return null;
    }

    @Override
    public Optional<Type> updateClubType(Type clubType) {
        return Optional.empty();
    }

    @Override
    public Long deleteClubTypeById(Long id) {
        return null;
    }
}
