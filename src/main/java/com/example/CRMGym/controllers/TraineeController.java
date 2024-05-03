package com.example.CRMGym.controllers;

import com.example.CRMGym.models.Trainee;
import com.example.CRMGym.services.TraineeService;
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


    @Autowired
    private TraineeService traineeService;

    @GetMapping
    public ResponseEntity<List<Trainee>> getAllTrainees() {
        Map<Long, Trainee> traineesMap = traineeService.getAllTrainees();
        List<Trainee> trainees = new ArrayList<>(traineesMap.values());
        return ResponseEntity.ok(trainees);
    }

    @PostMapping
    public ResponseEntity<Trainee> createTrainee(@RequestBody Trainee trainee) {
        Trainee createdTrainee = traineeService.createTrainee(trainee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTrainee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trainee> getTrainee(@PathVariable Long id) {
        Trainee trainee = traineeService.getTrainee(id);
        if (trainee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(trainee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trainee> updateTrainee(@PathVariable Long id, @RequestBody Trainee trainee) {
        Trainee updatedTrainee = traineeService.updateTrainee(id, trainee);
        if (updatedTrainee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedTrainee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainee(@PathVariable Long id) {
        boolean wasDeleted = traineeService.deleteTrainee(id);
        if (wasDeleted) {
            return ResponseEntity.ok().build(); // Retorna OK si el Trainee fue encontrado y eliminado.
        }
        return ResponseEntity.notFound().build(); // Retorna Not Found si no se encuentra el Trainee.
    }
}