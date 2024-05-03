package com.example.CRMGym.controllers;

import com.example.CRMGym.models.Trainer;
import com.example.CRMGym.services.TrainerService;
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

    @Autowired
    private TrainerService trainerService;

    @GetMapping
    public ResponseEntity<List<Trainer>> getAllTrainers() {
        Map<Long, Trainer> trainersMap = trainerService.getAllTrainers();
        List<Trainer> trainers = new ArrayList<>(trainersMap.values());
        return ResponseEntity.ok(trainers);
    }

    @PostMapping
    public ResponseEntity<Trainer> createTrainer(@RequestBody Trainer trainer) {
        Trainer createdTrainer = trainerService.createTrainer(trainer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTrainer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trainer> getTrainer(@PathVariable Long id) {
        Trainer trainer = trainerService.getTrainer(id);
        if (trainer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(trainer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trainer> updateTrainer(@PathVariable Long id, @RequestBody Trainer trainer) {
        Trainer updatedTrainer = trainerService.updateTrainer(id, trainer);
        if (updatedTrainer == null) {
            return ResponseEntity.notFound().build();
        }
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
