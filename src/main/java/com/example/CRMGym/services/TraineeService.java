package com.example.CRMGym.services;

import com.example.CRMGym.models.Trainee;
import com.example.CRMGym.repositories.TraineeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;

@Service
public class TraineeService {

    private final TraineeDao traineeDao;

    @Autowired
    public TraineeService(TraineeDao traineeDao) {
        this.traineeDao = traineeDao;
    }

    public Trainee getTrainee(Long id) {
        return traineeDao.findById(id);
    }

    public Map<Long, Trainee> getAllTrainees() {
        return traineeDao.findAll();
    }

    public Trainee createTrainee(Trainee trainee) {
        validateTraineeData(trainee);
        String username = generateUsername(trainee.getFirstName(), trainee.getLastName());
        String password = generateRandomPassword();
        trainee.setUsername(username);
        trainee.setPassword(password);
        return traineeDao.save(trainee);
    }

    public Trainee updateTrainee(Long id, Trainee trainee){
        if (traineeDao.findById(id) == null) {
            throw new RuntimeException("Trainee not found");
        }
        return traineeDao.update(id, trainee);
    }

    public boolean deleteTrainee(Long id) {
        Trainee existingTrainee = traineeDao.findById(id);
        if (existingTrainee != null) {
            traineeDao.delete(id);
            return true; // Retorna true si el Trainee fue encontrado y eliminado.
        }
        return false; // Retorna false si no se encuentra el Trainee.
    }

    private void validateTraineeData(Trainee trainee) {
        if (trainee.getFirstName() == null || trainee.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name is required.");
        }
        if (trainee.getLastName() == null || trainee.getLastName().isEmpty()) {
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
        return username;
    }

    private boolean usernameExists(String username) {
        return traineeDao.findAll().values().stream()
                .anyMatch(trainee -> trainee.getUsername().equalsIgnoreCase(username));
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

}
