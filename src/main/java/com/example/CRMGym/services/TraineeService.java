package com.example.CRMGym.services;

import com.example.CRMGym.models.Trainee;
import com.example.CRMGym.repositories.TraineeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;

@Service
public class TraineeService {

    private static final Logger log = LoggerFactory.getLogger(TraineeService.class);

    private final TraineeDao traineeDao;

    @Autowired
    public TraineeService(TraineeDao traineeDao) {
        this.traineeDao = traineeDao;
    }

    public Trainee getTrainee(Long id) {
        Trainee trainee = traineeDao.findById(id);
        if (trainee == null) {
            log.warn("Attempted to find a non-existing Trainee with ID: {}", id);
            return null;
        }
        log.debug("Retrieved Trainee with ID: {}", id);
        return trainee;
    }

    public Map<Long, Trainee> getAllTrainees() {
        log.debug("Retrieving all trainees");
        return traineeDao.findAll();
    }

    public Trainee createTrainee(Trainee trainee) {
        log.debug("Creating Trainee: {}", trainee);
        validateTraineeData(trainee);
        String username = generateUsername(trainee.getFirstName(), trainee.getLastName());
        String password = generateRandomPassword();
        trainee.setUsername(username);
        trainee.setPassword(password);
        Trainee savedTrainee = traineeDao.save(trainee);
        log.info("Trainee created successfully with ID: {}", savedTrainee.getId());
        return savedTrainee;
    }

    public Trainee updateTrainee(Long id, Trainee trainee){
        log.debug("Updating Trainee with ID: {}", id);
        if (traineeDao.findById(id) == null) {
            log.error("Attempted to update a non-existing Trainee with ID: {}", id);
            throw new RuntimeException("Trainee not found");
        }
        Trainee updatedTrainee = traineeDao.update(id, trainee);
        log.info("Trainee updated successfully with ID: {}", id);
        return updatedTrainee;
    }

    public boolean deleteTrainee(Long id) {
        log.debug("Deleting Trainee with ID: {}", id);
        Trainee existingTrainee = traineeDao.findById(id);
        if (existingTrainee != null) {
            traineeDao.delete(id);
            log.info("Trainee deleted successfully with ID: {}", id);
            return true; // Retorna true si el Trainee fue encontrado y eliminado.
        }
        log.warn("Attempted to delete a non-existing Trainee with ID: {}", id);
        return false; // Retorna false si no se encuentra el Trainee.
    }

    private void validateTraineeData(Trainee trainee) {
        if (trainee.getFirstName() == null || trainee.getFirstName().isEmpty()) {
            log.error("First name is required for creating/updating Trainee");
            throw new IllegalArgumentException("First name is required.");
        }
        if (trainee.getLastName() == null || trainee.getLastName().isEmpty()) {
            log.error("Last name is required for creating/updating Trainee");
            throw new IllegalArgumentException("Last name is required.");
        }
    }

    private String generateUsername(String firstName,String lastName) {
        String baseUsername = firstName.trim() + "." +lastName.trim();
        String username = baseUsername;
        int count =0;

        while (usernameExists(username)) {
            count++;
            username = baseUsername + count;
        }
        log.debug("Generated username in method: {}", username);
        return username;
    }

    private boolean usernameExists(String username) {
        boolean exists = traineeDao.findAll().values().stream()
                .anyMatch(trainee -> trainee.getUsername().equalsIgnoreCase(username));
        log.debug("Username exists check: {}", exists);
        return exists;
    }

    private String generateRandomPassword() {
        int passwordLength = 10;
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(passwordLength);
        for (int i = 0; i < passwordLength; i++) {
            sb.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }
        log.debug("Generated random password");
        return sb.toString();
    }

}
