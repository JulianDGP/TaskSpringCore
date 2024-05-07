package com.example.CRMGym.service;


import com.example.CRMGym.models.Trainer;
import com.example.CRMGym.repositories.TrainerDao;
import com.example.CRMGym.services.TrainerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceTests {

    @Mock
    private TrainerDao trainerDao;

    @InjectMocks
    private TrainerService trainerService;

    @Test
    void testCreateTrainer() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("John");
        trainer.setLastName("Smith");

        when(trainerDao.save(any(Trainer.class))).thenReturn(trainer);

        Trainer created = trainerService.createTrainer(trainer);
        assertNotNull(created, "The created trainer should not be null");
        assertEquals("John", created.getFirstName(), "The first name should match");
        assertEquals("Smith", created.getLastName(), "The last name should match");
    }

    @Test
    void testGetTrainer() {
        Trainer trainer = new Trainer();
        trainer.setId(1L);

        when(trainerDao.findById(1L)).thenReturn(trainer);

        Trainer found = trainerService.getTrainer(1L);
        assertNotNull(found, "Trainer should be found with id 1");
        assertEquals(1L, found.getId(), "The trainer ID should match");
    }



    @Test
    void testUpdateTrainer() {
        Trainer existingTrainer = new Trainer();
        existingTrainer.setId(1L);
        existingTrainer.setFirstName("Alice");

        when(trainerDao.findById(1L)).thenReturn(existingTrainer);
        when(trainerDao.update(any(Long.class), any(Trainer.class))).thenReturn(existingTrainer);

        Trainer updated = trainerService.updateTrainer(1L, existingTrainer);
        assertNotNull(updated, "Updated trainer should not be null");
        assertEquals("Alice", updated.getFirstName(), "The first name should be Alice");
    }

    @Test
    void testGetAllTrainers() {
        Map<Long, Trainer> trainers = new HashMap<>();
        Trainer trainer1 = new Trainer();
        trainer1.setId(1L);
        Trainer trainer2 = new Trainer();
        trainer2.setId(2L);
        trainers.put(1L, trainer1);
        trainers.put(2L, trainer2);

        when(trainerDao.findAll()).thenReturn(trainers);

        Map<Long, Trainer> allTrainers = trainerService.getAllTrainers();
        assertEquals(2, allTrainers.size(), "Should return all trainers");
        assertTrue(allTrainers.containsKey(1L), "Should contain trainer with ID 1");
        assertTrue(allTrainers.containsKey(2L), "Should contain trainer with ID 2");
    }

}
