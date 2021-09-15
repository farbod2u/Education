package ir.farbod.service.education.service;

import ir.farbod.service.education.entity.Education;
import ir.farbod.service.education.repository.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class EducationService {

    private EducationRepository repository;

    @Autowired
    public EducationService(EducationRepository repository) {
        this.repository = repository;
    }

    public List<Education> getAll() {
        return repository.findAll();
    }

    public Education get(Long id) throws Exception {
        Optional<Education> res = repository.findById(id);
        if (res.isEmpty())
            throw new Exception("Education with id " + id.toString() + " not found.");
        else
            return res.get();
    }

    public Education save(Education entity) {
        return repository.save(entity);
    }

    public void delete(Long id) throws Exception {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Education with id " + id.toString() + " not found.");
        }
    }

    @Transactional(rollbackOn = {Exception.class})
    public void update(Education entity) throws Exception {
        Optional<Education> origEntity = repository.findById(entity.getId());
        if (origEntity.isEmpty())
            throw new Exception("Education with id " + entity.getId().toString() + " not found.");
        else {
            Arrays.stream(origEntity.get().getClass().getFields()).iterator().forEachRemaining(c -> {
                if (c.getName().toLowerCase() != "id") {
                    try {
                        c.set(origEntity.get(), entity.getClass().getField(c.getName()).get(entity));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
