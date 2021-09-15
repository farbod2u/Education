package ir.farbod.service.education.controller;

import ir.farbod.service.education.entity.Education;
import ir.farbod.service.education.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/education")
public class EducationController {

    private EducationService service;

    @Autowired
    public EducationController(EducationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Education>> getAll() {
        List<Education> res = service.getAll();
        return new ResponseEntity<List<Education>>(res, res.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Education> get(@PathVariable("id") Long id) {
        Education res = null;
        try {
            res = service.get(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(res, res != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/save")
    public ResponseEntity<Education> save(@RequestBody Education entity) {
        Education res = service.save(entity);
        return new ResponseEntity<>(res, res.getId() != null ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>("Education# " + id.toString() + " deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Education entity) {
        try {
            service.update(entity);
            return new ResponseEntity<String>("Education# " + entity.getId().toString() + " updated.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
