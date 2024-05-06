package com.example.CRMGym.controllers;

import com.example.CRMGym.models.Trainer;
import com.example.CRMGym.services.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    private static final Logger log = LoggerFactory.getLogger(TrainerController.class);

    @Autowired
    private TrainerService trainerService;

    @GetMapping
    public ResponseEntity<List<Trainer>> getAllTrainers() {
        log.debug("Received request to get all trainers");
        Map<Long, Trainer> trainersMap = trainerService.getAllTrainers();
        List<Trainer> trainers = new ArrayList<>(trainersMap.values());
        log.info("Returned {} trainers", trainers.size());
        return ResponseEntity.ok(trainers);
    }

    @PostMapping
    public ResponseEntity<Trainer> createTrainer(@RequestBody Trainer trainer) {
        log.debug("Received request to create a new trainer: {}", trainer);
        Trainer createdTrainer = trainerService.createTrainer(trainer);
        log.info("Successfully created trainer with ID: {}", createdTrainer.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTrainer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trainer> getTrainer(@PathVariable Long id) {
        log.debug("Received request to get trainer by ID: {}", id);

        Trainer trainer = trainerService.getTrainer(id);
        if (trainer == null) {
            log.warn("No trainer found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Returned trainer with ID: {}", id);

        return ResponseEntity.ok(trainer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trainer> updateTrainer(@PathVariable Long id, @RequestBody Trainer trainer) {
        log.debug("Received request to update trainer with ID: {}", id);
        Trainer updatedTrainer = trainerService.updateTrainer(id, trainer);
        if (updatedTrainer == null) {
            log.error("Failed to find trainer with ID: {} for update", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Successfully updated trainer with ID: {}", id);
        return ResponseEntity.ok(updatedTrainer);
    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteTrainer(@PathVariable Long id) {
//        boolean wasDeleted = trainerService.deleteTrainer(id);
//        if (wasDeleted) {
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.notFound().build();
//    }
}
