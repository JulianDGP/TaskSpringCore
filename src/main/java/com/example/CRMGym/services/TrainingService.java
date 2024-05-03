package com.example.CRMGym.services;

import com.example.CRMGym.models.Training;
import com.example.CRMGym.repositories.TrainingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TrainingService {
    private final TrainingDao trainingDao;

    @Autowired
    public TrainingService(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }

    public Training getTraining(Long id) {
        Training training = trainingDao.findById(id);
        if (training == null) {
            throw new RuntimeException("Training not found");
        }
        return training;
    }

    public Map<Long, Training> getAllTrainings() {
        return trainingDao.findAll();
    }

    public Training createTraining(Training training) {
        validateTrainingData(training);  // Validar los datos antes de guardar el training
        return trainingDao.save(training);
    }

    private void validateTrainingData(Training training) {
        if (training.getTrainingName() == null || training.getTrainingName().isEmpty()) {
            throw new IllegalArgumentException("Training name is required.");
        }
        if (training.getTrainingDate() == null) {
            throw new IllegalArgumentException("Training date is required.");
        }
        if (training.getTrainingType() == null) {
            throw new IllegalArgumentException("Training type is required.");
        }
        if (training.getTrainingDuration() == null) {
            throw new IllegalArgumentException("Training duration is required.");
        }

    }

}