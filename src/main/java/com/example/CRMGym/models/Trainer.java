package com.example.CRMGym.models;

    public class Trainer extends User{
        private String specialization;

        public Trainer(Long id, String firstName, String lastName, String username, String password, boolean isActive, String specialization) {
            super(id, firstName, lastName, username, password, isActive);
            this.specialization = specialization;
        }

        public Trainer() {

        }

        public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
