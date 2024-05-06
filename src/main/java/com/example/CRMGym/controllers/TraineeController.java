package com.example.CRMGym.controllers;

import com.example.CRMGym.exceptions.ErrorResponse;
import com.example.CRMGym.models.Trainee;
import com.example.CRMGym.services.TraineeService;
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
@RequestMapping("/api/trainees")
public class TraineeController {

    private static final Logger log = LoggerFactory.getLogger(TraineeController.class);

    @Autowired
    private TraineeService traineeService;

    @GetMapping
    public ResponseEntity<List<Trainee>> getAllTrainees() {
        log.debug("Received request to get all trainees");
        Map<Long, Trainee> traineesMap = traineeService.getAllTrainees();
        List<Trainee> trainees = new ArrayList<>(traineesMap.values());
        log.info("Returned {} trainees", trainees.size());
        return ResponseEntity.ok(trainees);
    }

    @PostMapping
    public ResponseEntity<?> createTrainee(@RequestBody Trainee trainee) {
        try {
            log.debug("Received request to create a new trainee: {}", trainee);
            Trainee createdTrainee = traineeService.createTrainee(trainee);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTrainee);
        } catch (IllegalArgumentException e) {
            log.error("Error creating trainee: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ErrorResponse("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e) {
            log.error("Unexpected error occurred while creating trainee: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trainee> getTrainee(@PathVariable Long id) {
        log.debug("Received request to get trainee by ID: {}", id);
        Trainee trainee = traineeService.getTrainee(id);
        if (trainee == null) {
            log.warn("No trainee found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Returned trainee with ID: {}", id);
        return ResponseEntity.ok(trainee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trainee> updateTrainee(@PathVariable Long id, @RequestBody Trainee trainee) {
        log.debug("Received request to update trainee with ID: {}", id);
        Trainee updatedTrainee = traineeService.updateTrainee(id, trainee);
        if (updatedTrainee == null) {
            log.error("Failed to find trainee with ID: {} for update", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Successfully updated trainee with ID: {}", id);
        return ResponseEntity.ok(updatedTrainee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainee(@PathVariable Long id) {
        log.debug("Received request to delete trainee with ID: {}", id);
        boolean wasDeleted = traineeService.deleteTrainee(id);
        if (wasDeleted) {
            log.info("Successfully deleted trainee with ID: {}", id);
            return ResponseEntity.ok().build(); // Retorna OK si el Trainee fue encontrado y eliminado.
        }
        log.warn("Failed to delete trainee with ID: {}, trainee not found", id);
        return ResponseEntity.notFound().build(); // Retorna Not Found si no se encuentra el Trainee.
    }
}