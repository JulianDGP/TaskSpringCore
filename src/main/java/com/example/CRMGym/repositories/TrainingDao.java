package com.example.CRMGym.repositories;

import com.example.CRMGym.models.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TrainingDao {

    private final Map<Long, Training> storage;

    @Autowired
    public TrainingDao(Map<Long, Training> storage){
        this.storage =storage;
    }

    public Training save(Training training){
        if (training.getId() == null || storage.containsKey(training.getId())) {
            throw new IllegalArgumentException("Training must have a unique ID or ID is already in use.");
        }
        storage.put(training.getId(), training);
        return training;
    }

    public Training findById(Long id){
        return storage.get(id);
    }
    public Map<Long, Training> findAll() {
        return new HashMap<>(storage);
    }

//    public Trainer update(Long id, Trainer trainer) {
//         if (!storage.containsKey(id)) {
//            throw new IllegalArgumentException("Cannot update non-existing training.");
//        }
//        training.setId(id);  // Asegura que el ID no cambie.
//        return storage.replace(id, training);
//    }

//    public Trainer delete(Long id) {
//        if (!storage.containsKey(id)) {
//            throw new IllegalArgumentException("Cannot delete non-existing training.");
//        }
//        return storage.remove(id);
//    }
//    }
}
