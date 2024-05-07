package com.example.CRMGym.service;


import com.example.CRMGym.models.Training;
import com.example.CRMGym.models.TrainingType;
import com.example.CRMGym.repositories.TrainingDao;
import com.example.CRMGym.services.TrainingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceTests {

    @Mock
    private TrainingDao trainingDao;

    @InjectMocks
    private TrainingService trainingService;

    @Test
    void testCreateTraining() {
        Training training = new Training();
        training.setTrainingName("Strength Training");
        training.setTrainingDate(LocalDateTime.now());
        training.setTrainingType(TrainingType.valueOf("STRENGTH_TRAINING"));
        training.setTrainingDuration(Duration.parse("PT1H"));

        when(trainingDao.save(any(Training.class))).thenReturn(training);

        Training created = trainingService.createTraining(training);
        assertNotNull(created, "The created training should not be null");
        assertEquals("Strength Training", created.getTrainingName(), "The training name should match");
    }

    @Test
    void testGetTraining() {
        Training training = new Training();
        training.setId(1L);

        when(trainingDao.findById(1L)).thenReturn(training);

        Training found = trainingService.getTraining(1L);
        assertNotNull(found, "Training should be found with id 1");
        assertEquals(1L, found.getId(), "The training ID should match");
    }

    @Test
    void testGetAllTrainings() {
        Map<Long, Training> trainings = new HashMap<>();
        Training training1 = new Training();
        training1.setId(1L);
        Training training2 = new Training();
        training2.setId(2L);
        trainings.put(1L, training1);
        trainings.put(2L, training2);

        when(trainingDao.findAll()).thenReturn(trainings);

        Map<Long, Training> allTrainings = trainingService.getAllTrainings();
        assertEquals(2, allTrainings.size(), "Should return all trainings");
        assertTrue(allTrainings.containsKey(1L), "Should contain training with ID 1");
        assertTrue(allTrainings.containsKey(2L), "Should contain training with ID 2");
    }

    @Test
    void testCreateTrainingWithInvalidData() {
        Training training = new Training(); // Missing required fields

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            trainingService.createTraining(training);
        });

        assertTrue(exception.getMessage().contains("Training name is required"));
    }
}
