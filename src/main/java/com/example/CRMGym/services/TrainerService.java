package com.example.CRMGym.services;

import com.example.CRMGym.models.Trainer;
import com.example.CRMGym.repositories.TrainerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;

@Service
public class TrainerService {
    private final TrainerDao trainerDao;

    @Autowired
    public TrainerService(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
    }

    public Trainer getTrainer(Long id) {
        return trainerDao.findById(id);
    }

    public Map<Long, Trainer> getAllTrainers() {
        return trainerDao.findAll();
    }
    public Trainer createTrainer(Trainer trainer) {
        validateTrainerData(trainer);
        String username = generateUsername(trainer.getFirstName(), trainer.getLastName());
        String password = generateRandomPassword();
        trainer.setUsername(username);
        trainer.setPassword(password);
        return trainerDao.save(trainer);
    }

    public Trainer updateTrainer(Long id, Trainer trainer) {
        if (trainerDao.findById(id) == null) {
            throw new RuntimeException("Trainer not found");
        }
        trainer.setUsername(trainer.getUsername());
        trainer.setPassword(generateRandomPassword());
        return trainerDao.update(id, trainer);
    }

//    public void deleteTrainer(Long id) {
//        trainerDao.delete(id);
//    }

    private String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName.trim() + "." + lastName.trim();
        String username = baseUsername;
        int count = 0;
        // Check if the username exists, and if so, append a number
        while (usernameExists(username)) {
            count++;
            username = baseUsername + count;
        }
        return username;
    }

    private boolean usernameExists(String username) {
        return trainerDao.findAll().values().stream()
                .anyMatch(trainer -> trainer.getUsername().equalsIgnoreCase(username));
    }

    private String generateRandomPassword() {
        int passwordLength = 10;
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(passwordLength);
        for (int i = 0; i < passwordLength; i++) {
            sb.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }
        return sb.toString();
    }

    private void validateTrainerData(Trainer trainer) {
        if (trainer.getFirstName() == null || trainer.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name is required.");
        }
        if (trainer.getLastName() == null || trainer.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Last name is required.");
        }
    }
}
