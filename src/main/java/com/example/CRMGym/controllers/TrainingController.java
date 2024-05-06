package com.example.CRMGym.controllers;

import com.example.CRMGym.models.Training;
import com.example.CRMGym.services.TrainingService;
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
@RequestMapping("/api/trainings")
public class TrainingController {

    private static final Logger log = LoggerFactory.getLogger(TrainingController.class);

    @Autowired
    private TrainingService trainingService;

    @GetMapping
    public ResponseEntity<List<Training>> getAllTrainings() {
        log.debug("Received request to get all trainings");
        Map<Long, Training> trainingsMap = trainingService.getAllTrainings();
        List<Training> trainings = new ArrayList<>(trainingsMap.values());
        log.info("Returned {} trainings", trainings.size());
        return ResponseEntity.ok(trainings);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Training> getTraining(@PathVariable Long id) {
        log.debug("Received request to get training by ID: {}", id);

        Training training = trainingService.getTraining(id);
        if (training == null) {
            log.warn("No training found with ID: {}", id);

            return ResponseEntity.notFound().build();
        }
        log.info("Returned training with ID: {}", id);

        return ResponseEntity.ok(training);
    }

    @PostMapping
    public ResponseEntity<Training> createTraining(@RequestBody Training training) {
        log.debug("Received request to create a new training: {}", training);
        try {
            Training createdTraining = trainingService.createTraining(training);
            log.info("Successfully created training with ID: {}", createdTraining.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTraining);
        } catch (Exception e) {
            log.error("Error creating training: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }


    }

}
