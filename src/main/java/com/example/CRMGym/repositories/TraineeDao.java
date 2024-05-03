package com.example.CRMGym.repositories;

import com.example.CRMGym.models.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TraineeDao {

    private final Map<Long, Trainee> storage;

    @Autowired
    public TraineeDao(Map<Long, Trainee> storage){
        this.storage =storage;
    }

    public Trainee save(Trainee trainee){

        if (trainee.getId() == null || storage.containsKey(trainee.getId())) {
            throw new IllegalArgumentException("Trainee must have a unique ID or ID is already in use.");
        }

        storage.put(trainee.getId(), trainee);
        return trainee;
    }

    public Trainee findById(Long id){
        return storage.get(id);
    }
    public Map<Long, Trainee> findAll() {

        //devolver lista de trainee
        return new HashMap<>(storage);  // Devuelve una copia para evitar modificaciones externas.
    }

    public Trainee update(Long id, Trainee trainee) {

        if (!storage.containsKey(id)) {
            throw new IllegalArgumentException("Cannot update non-existing trainee.");
        }
        trainee.setId(id);  // Asegura que el ID no cambie.
        return storage.replace(id, trainee);
    }

    public Trainee delete(Long id) {
        if (!storage.containsKey(id)) {
            throw new IllegalArgumentException("Cannot delete non-existing trainee.");
        }
        return storage.remove(id);
    }
}
