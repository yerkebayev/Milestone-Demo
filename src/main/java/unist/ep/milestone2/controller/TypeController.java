package unist.ep.milestone2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unist.ep.milestone2.model.Type;
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
    public ResponseEntity<List<Type>> getTypes() {
        List<Type> types = typeService.getAllClubTypes();
        return new ResponseEntity<>(types, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Type> getTypeById(@PathVariable long id) {
        Optional<Type> optionalType = typeService.getClubTypeById(id);
        return optionalType.map(type -> new ResponseEntity<>(type, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Type> addType(@RequestBody Type type) {
        Type typeNew = typeService.saveClubType(type);
        return new ResponseEntity<>(typeNew, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Type> updateType(@PathVariable long id, @RequestBody Type type){
        Optional<Type> optionalType = typeService.getClubTypeById(id);
        if (optionalType.isPresent()) {
            Type t = optionalType.get();
            t.setName(type.getName());
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
