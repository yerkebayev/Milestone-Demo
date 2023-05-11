package unist.ep.milestone2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unist.ep.milestone2.model.ClubType;
import unist.ep.milestone2.service.TypeService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/types")
public class TypeController {
    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ClubType>> getTypes() {
        List<ClubType> clubTypes = typeService.getAllClubTypes();
        return new ResponseEntity<>(clubTypes, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ClubType> getTypeById(@PathVariable Integer id) {
        Optional<ClubType> optionalType = Optional.ofNullable(typeService.getClubTypeById(id));
        return optionalType.map(type -> new ResponseEntity<>(type, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ClubType> addType(@RequestBody ClubType clubType) {
        ClubType clubTypeNew = typeService.saveClubType(clubType);
        return new ResponseEntity<>(clubTypeNew, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ClubType> updateType(@PathVariable Integer id, @RequestBody ClubType clubType){
        Optional<ClubType> optionalType = Optional.ofNullable(typeService.getClubTypeById(id));
        if (optionalType.isPresent()) {
            ClubType t = optionalType.get();
            t.setName(clubType.getName());
            typeService.saveClubType(t);
            return new ResponseEntity<>(t, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<String> deleteType(@PathVariable long id) {
        if (typeService.deleteClubTypeById(id) > 0) {
            return new ResponseEntity<>("Deleted...", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Type not found.", HttpStatus.NOT_FOUND);
        }
    }
}
