package com.example.CRMGym.repositories;

import com.example.CRMGym.models.Trainee;
import com.example.CRMGym.services.TraineeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TraineeDao {

    private static final Logger log = LoggerFactory.getLogger(TraineeService.class);
    private final Map<Long, Trainee> storage;

    @Autowired
    public TraineeDao(Map<Long, Trainee> storage){
        this.storage =storage;
    }

    public Trainee save(Trainee trainee) throws IllegalArgumentException{

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
        storage.replace(id, trainee);
        return trainee;
    }

    public boolean delete(Long id) {
        if (!storage.containsKey(id)) {
            log.warn("Attempt to delete non-existing trainee with ID: {}", id);
            return false;
        }
        storage.remove(id);
        log.info("Trainee deleted successfully with ID: {}", id);
        return true;
    }

}
