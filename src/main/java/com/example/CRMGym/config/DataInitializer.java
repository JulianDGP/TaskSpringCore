package com.example.CRMGym.config;

import com.example.CRMGym.models.Trainee;
import com.example.CRMGym.models.Trainer;
import com.example.CRMGym.models.Training;
import com.example.CRMGym.services.TraineeService;
import com.example.CRMGym.services.TrainerService;
import com.example.CRMGym.services.TrainingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@Component
public class DataInitializer {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TraineeService traineeService;
    @Autowired
    private TrainerService trainerService;
    @Autowired
    private TrainingService trainingService;

    @Value("classpath:data/initial_data.json")
    private Resource initData;

    @PostConstruct
    public void loadData() throws IOException {
        String dataJson = new String(Files.readAllBytes(initData.getFile().toPath()));
        Map<String, List<Object>> allData = objectMapper.readValue(dataJson, Map.class);
        allData.get("trainees").forEach(obj -> traineeService.createTrainee(objectMapper.convertValue(obj, Trainee.class)));
        allData.get("trainers").forEach(obj -> trainerService.createTrainer(objectMapper.convertValue(obj, Trainer.class)));
        allData.get("trainings").forEach(obj -> trainingService.createTraining(objectMapper.convertValue(obj, Training.class)));
    }
}
