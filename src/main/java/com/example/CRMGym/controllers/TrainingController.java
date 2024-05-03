package com.example.CRMGym.controllers;

import com.example.CRMGym.models.Training;
import com.example.CRMGym.services.TrainingService;
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

    @Autowired
    private TrainingService trainingService;

    @GetMapping
    public ResponseEntity<List<Training>> getAllTrainings() {
        Map<Long, Training> trainingsMap = trainingService.getAllTrainings();
        List<Training> trainings = new ArrayList<>(trainingsMap.values());
        return ResponseEntity.ok(trainings);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Training> getTraining(@PathVariable Long id) {
        Training training = trainingService.getTraining(id);
        if (training == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(training);
    }

    @PostMapping
    public ResponseEntity<Training> createTraining(@RequestBody Training training) {
        Training createdTraining = trainingService.createTraining(training);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTraining);
    }



}
