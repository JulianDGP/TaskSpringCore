package com.example.CRMGym.repositories;

import com.example.CRMGym.models.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TrainerDao {
    private final Map<Long, Trainer> storage;

    @Autowired
    public TrainerDao(Map<Long, Trainer> storage){
        this.storage =storage;
    }

    public Trainer save(Trainer trainer){
        if (trainer.getId() ==null || storage.containsKey(trainer.getId())){
            throw new IllegalArgumentException("Trainer must have a unique ID or ID is already in use.");
        }
        storage.put(trainer.getId(), trainer);
        return trainer;
    }

    public Trainer findById(Long id){
        return storage.get(id);
    }
    public Map<Long, Trainer> findAll() {
        return new HashMap<>(storage);
    }

    public Trainer update(Long id, Trainer trainer) {
        if (!storage.containsKey(id)) {
            throw new IllegalArgumentException("Cannot update non-existing trainer.");
        }
        trainer.setId(id);  // Asegura que el ID no cambie.
        return storage.replace(id, trainer);
    }

//    public Trainer delete(Long id) {
//        if (!storage.containsKey(id)) {
//            throw new IllegalArgumentException("Cannot delete non-existing trainer.");
//        }
//        return storage.remove(id);
//    }

}
