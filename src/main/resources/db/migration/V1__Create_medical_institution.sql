CREATE TABLE IF NOT EXISTS users (
                                     id BIGSERIAL PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS user_roles (
                                          user_id BIGINT NOT NULL,
                                          roles VARCHAR(255) NOT NULL,
    CONSTRAINT fk_user_roles_user
    FOREIGN KEY (user_id)
    REFERENCES users (id)
    ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS patient (
                                       id BIGSERIAL PRIMARY KEY,
                                       full_name VARCHAR(255) NOT NULL,
    birth_date TIMESTAMP NOT NULL,
    gender VARCHAR(100) NOT NULL,
    phone_number VARCHAR(100) NOT NULL,
    email VARCHAR(255),
    address VARCHAR(255) NOT NULL,
    medical_card_number VARCHAR(255) NOT NULL UNIQUE,
    user_id BIGINT UNIQUE,
    CONSTRAINT fk_patient_user
    FOREIGN KEY (user_id)
    REFERENCES users (id)
    ON DELETE SET NULL
    );

CREATE TABLE IF NOT EXISTS doctor (
                                      id BIGSERIAL PRIMARY KEY,
                                      full_name VARCHAR(255) NOT NULL,
    specialization VARCHAR(255) NOT NULL,
    experience INTEGER NOT NULL,
    cabinet VARCHAR(100) NOT NULL,
    phone VARCHAR(100) NOT NULL,
    email VARCHAR(255),
    user_id BIGINT UNIQUE,
    CONSTRAINT fk_doctor_user
    FOREIGN KEY (user_id)
    REFERENCES users (id)
    ON DELETE SET NULL
    );

CREATE TABLE IF NOT EXISTS medical_service (
                                               id BIGSERIAL PRIMARY KEY,
                                               name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    price NUMERIC(12, 2) NOT NULL,
    duration_minutes INTEGER NOT NULL
    );

CREATE TABLE IF NOT EXISTS appointment (
                                           id BIGSERIAL PRIMARY KEY,
                                           patient_id BIGINT,
                                           doctor_id BIGINT,
                                           service_id BIGINT,
                                           timestamp TIMESTAMP NOT NULL,
                                           notes TEXT,
                                           CONSTRAINT fk_appointment_patient
                                           FOREIGN KEY (patient_id)
    REFERENCES patient (id)
    ON DELETE SET NULL,
    CONSTRAINT fk_appointment_doctor
    FOREIGN KEY (doctor_id)
    REFERENCES doctor (id)
    ON DELETE SET NULL,
    CONSTRAINT fk_appointment_service
    FOREIGN KEY (service_id)
    REFERENCES medical_service (id)
    ON DELETE SET NULL
    );

CREATE TABLE IF NOT EXISTS appointment_status (
                                                  appointment_id BIGINT NOT NULL,
                                                  status VARCHAR(255) NOT NULL,
    CONSTRAINT fk_appointment_status_appointment
    FOREIGN KEY (appointment_id)
    REFERENCES appointment (id)
    ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS medical_record (
                                              id BIGSERIAL PRIMARY KEY,
                                              appointment_id BIGINT UNIQUE,
                                              diagnosis VARCHAR(255) NOT NULL,
    treatment VARCHAR(255) NOT NULL,
    doctor_notes TEXT,
    CONSTRAINT fk_medical_record_appointment
    FOREIGN KEY (appointment_id)
    REFERENCES appointment (id)
    ON DELETE SET NULL
    );

CREATE INDEX IF NOT EXISTS idx_patient_full_name
    ON patient (full_name);

CREATE INDEX IF NOT EXISTS idx_doctor_full_name
    ON doctor (full_name);

CREATE INDEX IF NOT EXISTS idx_doctor_specialization
    ON doctor (specialization);

CREATE INDEX IF NOT EXISTS idx_appointment_patient_id
    ON appointment (patient_id);

CREATE INDEX IF NOT EXISTS idx_appointment_doctor_timestamp
    ON appointment (doctor_id, timestamp);

CREATE INDEX IF NOT EXISTS idx_medical_record_appointment_id
    ON medical_record (appointment_id);