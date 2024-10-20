# Backend del Proyecto de Gestión de Muestras y Gráficos

Este es el **backend** del proyecto, construido en **Spring Boot**. Proporciona una API REST para gestionar muestras, gráficos y sus relaciones, permitiendo crear, modificar y eliminar muestras y gráficos, además de visualizar los datos de forma procesada.

## 1. Funcionalidades Principales

- **Gestión de Muestras**: Crear, actualizar, eliminar y obtener muestras bioquímicas.
- **Gestión de Gráficos**: Crear, actualizar, eliminar y obtener gráficos relacionados con las muestras.
- **Procesamiento de Muestras**: Procesamiento concurrente de muestras para generar gráficos automáticamente.

## 2. Estructura del Proyecto

El backend sigue una arquitectura de capas que se divide en las siguientes secciones:

### 2.1. Paquete **controller**

- **GraficoController**: Controla las solicitudes relacionadas con los gráficos.
- **MuestraController**: Controla las solicitudes relacionadas con las muestras.
- **HomeController, WebController**: Manejan las rutas básicas de la interfaz web.
- **HtmxErrorController**: Manejo de errores para peticiones con HTMX.

### 2.2. Paquete **domain**

Define las entidades principales del proyecto:

- **DatoBioquimico**: Representa datos bioquímicos asociados a las muestras.
- **Grafico**: Representa un gráfico generado a partir de datos procesados.
- **GrupoMuestras**: Relaciona grupos de muestras para un mejor manejo.
- **Muestra**: Entidad que representa una muestra bioquímica.

### 2.3. Paquete **model**

Contiene los DTO (Data Transfer Objects) que se utilizan para transferir datos entre el backend y el frontend:

- **DatoBioquimicoDTO**
- **GraficoDTO**
- **GrupoMuestrasDTO**
- **MuestraDTO**

### 2.4. Paquete **repos**

Define los repositorios para acceder a las entidades en la base de datos:

- **DatoBioquimicoRepository**
- **GraficoRepository**
- **GrupoMuestrasRepository**
- **MuestraRepository**

### 2.5. Paquete **service**

Contiene la lógica de negocio:

- **GraficoService**: Lógica para gestionar gráficos.
- **MuestraService**: Lógica para gestionar muestras bioquímicas.
- **GrupoMuestraService**: Lógica para agrupar muestras.
- **MuestraFactoryService**: Fabrica que maneja diferentes tipos de muestras.

### 2.6. Paquete **rest**

Proporciona un recurso REST accesible:

- **MuestraResource**: Controlador REST para la gestión de las muestras bioquímicas.

## 3. Endpoints Disponibles

### 3.1. Gestión de Muestras

1. `GET /api/muestras/{id}`: Obtener una muestra por su ID.
2. `POST /api/muestras`: Crear una muestra nueva.
3. `PUT /api/muestras/{id}`: Actualizar una muestra existente.
4. `DELETE /api/muestras/{id}`: Eliminar una muestra por su ID.
5. `GET /api/muestras`: Obtener todas las muestras disponibles.

### 3.2. Gestión de Gráficos

1. `GET /api/graficos/{id}`: Obtener un gráfico por su ID.
2. `POST /api/graficos`: Crear un gráfico nuevo.
3. `PUT /api/graficos/{id}`: Actualizar un gráfico existente.
4. `DELETE /api/graficos/{id}`: Eliminar un gráfico por su ID.
5. `GET /api/graficos`: Obtener todos los gráficos disponibles.

### 3.3. Procesamiento de Gráficos y Muestras

- `POST /api/graficos/procesar`: Procesar gráficos concurrentemente.
- `POST /api/muestras/procesar`: Procesar muestras concurrentemente.



