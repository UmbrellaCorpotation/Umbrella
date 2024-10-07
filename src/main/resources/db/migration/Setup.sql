-- src/main/resources/db/migration/V1__Initial_Setup.sql

DROP TABLE IF EXISTS Eventos;
DROP TABLE IF EXISTS Datos_Físicos;
DROP TABLE IF EXISTS Datos_Bioquímicos;
DROP TABLE IF EXISTS Datos_Genéticos;
DROP TABLE IF EXISTS Tipo_Dato;
DROP TABLE IF EXISTS Muestra;

-- 2. Crear tabla Muestra
CREATE TABLE Muestra (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         codigo VARCHAR(255) NOT NULL UNIQUE,
                         fecha_recepcion TIMESTAMP,
                         descripcion TEXT
);

-- 3. Crear tabla Tipo_Dato
CREATE TABLE Tipo_Dato (
                           id INT PRIMARY KEY,
                           nombre VARCHAR(255) NOT NULL UNIQUE
);

-- 4. Crear tabla Datos_Genéticos
CREATE TABLE Datos_Genéticos (
                                 id INT PRIMARY KEY AUTO_INCREMENT,
                                 sample_id INT NOT NULL,
                                 secuencia TEXT,
                                 genes_identificados TEXT,
                                 FOREIGN KEY (sample_id) REFERENCES Muestra(id) ON DELETE CASCADE
);

-- 5. Crear tabla Datos_Bioquímicos
CREATE TABLE Datos_Bioquímicos (
                                   id INT PRIMARY KEY AUTO_INCREMENT,
                                   sample_id INT NOT NULL,
                                   compuestos_detectados TEXT,
                                   concentraciones DECIMAL(10, 2),
                                   FOREIGN KEY (sample_id) REFERENCES Muestra(id) ON DELETE CASCADE
);

-- 6. Crear tabla Datos_Físicos
CREATE TABLE Datos_Físicos (
                               id INT PRIMARY KEY AUTO_INCREMENT,
                               sample_id INT NOT NULL,
                               peso DECIMAL(10, 2),
                               altura DECIMAL(10, 2),
                               temperatura DECIMAL(5, 2),
                               FOREIGN KEY (sample_id) REFERENCES Muestra(id) ON DELETE CASCADE
);

-- 7. Crear tabla Eventos
CREATE TABLE Eventos (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         tipo_evento VARCHAR(255) NOT NULL,
                         descripcion TEXT,
                         fecha TIMESTAMP,
                         sample_id INT NOT NULL,
                         FOREIGN KEY (sample_id) REFERENCES Muestra(id) ON DELETE CASCADE
);

-- 8. Insertar datos de ejemplo en Muestra
INSERT INTO Muestra (codigo, fecha_recepcion, descripcion) VALUES
                                                               ('MZ-001', '2024-04-01 10:30:00', 'Muestra de tejido muscular'),
                                                               ('MZ-002', '2024-04-02 11:00:00', 'Muestra de fluido sanguíneo');

-- 9. Insertar datos de ejemplo en Tipo_Dato
INSERT INTO Tipo_Dato (id, nombre) VALUES
                                       (1, 'Genético'),
                                       (2, 'Bioquímico'),
                                       (3, 'Físico');

-- 10. Insertar datos de ejemplo en Datos_Genéticos
INSERT INTO Datos_Genéticos (sample_id, secuencia, genes_identificados) VALUES
                                                                            (1, 'ATGCGTACGTA...', 'GENE1, GENE2'),
                                                                            (2, 'CGTACGTAGCT...', 'GENE3, GENE4');

-- 11. Insertar datos de ejemplo en Datos_Bioquímicos
INSERT INTO Datos_Bioquímicos (sample_id, compuestos_detectados, concentraciones) VALUES
                                                                                      (1, 'Glucosa, Lactato', 5.50),
                                                                                      (2, 'Proteína A, Proteína B', 7.80);

-- 12. Insertar datos de ejemplo en Datos_Físicos
INSERT INTO Datos_Físicos (sample_id, peso, altura, temperatura) VALUES
                                                                     (1, 70.5, 175.0, 36.6),
                                                                     (2, 65.0, 180.0, 37.0);

-- 13. Insertar datos de ejemplo en Eventos
INSERT INTO Eventos (tipo_evento, descripcion, fecha, sample_id) VALUES
                                                                     ('Recepción', 'Muestra recibida en el laboratorio', '2024-04-01 10:35:00', 1),
                                                                     ('Procesamiento', 'Inicio del análisis genético', '2024-04-01 11:00:00', 1),
                                                                     ('Recepción', 'Muestra recibida en el laboratorio', '2024-04-02 11:05:00', 2),
                                                                     ('Procesamiento', 'Inicio del análisis bioquímico', '2024-04-02 11:30:00', 2);
