package com.example.CRMGym.service;

import com.example.CRMGym.models.Trainee;
import com.example.CRMGym.repositories.TraineeDao;
import com.example.CRMGym.services.TraineeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TraineeServiceTests {

    @Mock
    private TraineeDao traineeDao;

    @InjectMocks
    private TraineeService traineeService;

    @Test
    void testCreateTrainee() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Doe");

        when(traineeDao.save(any(Trainee.class))).thenReturn(trainee);

        Trainee created = traineeService.createTrainee(trainee);
        assertNotNull(created, "The created trainee should not be null");
        assertEquals("John", created.getFirstName(), "The first name should match");
        assertEquals("Doe", created.getLastName(), "The last name should match");
    }

    @Test
    void testGetTrainee() {
        Trainee trainee = new Trainee();
        trainee.setId(1L);

        when(traineeDao.findById(1L)).thenReturn(trainee);

        Trainee found = traineeService.getTrainee(1L);
        assertNotNull(found, "Trainee should be found with id 1");
        assertEquals(1L, found.getId(), "The trainee ID should match");
    }

    @Test
    void testUpdateTrainee() {
        Trainee existingTrainee = new Trainee();
        existingTrainee.setId(1L);
        existingTrainee.setFirstName("Alice");

        when(traineeDao.findById(1L)).thenReturn(existingTrainee);
        when(traineeDao.update(any(Long.class), any(Trainee.class))).thenReturn(existingTrainee);

        Trainee updated = traineeService.updateTrainee(1L, existingTrainee);
        assertNotNull(updated, "Updated trainee should not be null");
        assertEquals("Alice", updated.getFirstName(), "The first name should be Alice");
    }

    @Test
    void testDeleteTrainee() {
        Trainee trainee = new Trainee();
        trainee.setId(1L);

        when(traineeDao.findById(1L)).thenReturn(trainee);
        when(traineeDao.delete(1L)).thenReturn(true);

        boolean deleted = traineeService.deleteTrainee(1L);
        assertTrue(deleted, "Trainee should be deleted successfully");
    }

    @Test
    void testGetAllTrainees() {
        Map<Long, Trainee> trainees = new HashMap<>();
        Trainee trainee1 = new Trainee();
        trainee1.setId(1L);
        Trainee trainee2 = new Trainee();
        trainee2.setId(2L);
        trainees.put(1L, trainee1);
        trainees.put(2L, trainee2);

        when(traineeDao.findAll()).thenReturn(trainees);

        Map<Long, Trainee> allTrainees = traineeService.getAllTrainees();
        assertEquals(2, allTrainees.size(), "Should return all trainees");
        assertTrue(allTrainees.containsKey(1L), "Should contain trainee with ID 1");
        assertTrue(allTrainees.containsKey(2L), "Should contain trainee with ID 2");
    }
}
