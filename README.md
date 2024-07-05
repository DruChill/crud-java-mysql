# crud-java-mysql lo que vamos a crear:

![Captura de pantalla en ordenador](https://github.com/DruChill/crud-java-mysql/blob/main/Captura%20de%20pantalla%202024-07-05%20a%20la(s)%2018.32.19.png?raw=true)
![Captura de pantalla en ordenador](https://github.com/DruChill/crud-java-mysql/blob/main/Captura%20de%20pantalla%202024-07-05%20a%20la(s)%2018.32.22.png?raw=true)
![Captura de pantalla en ordenador](https://github.com/DruChill/crud-java-mysql/blob/main/Captura%20de%20pantalla%202024-07-05%20a%20la(s)%2018.32.25.png?raw=true)


# Paso 1: Crear un Proyecto en Spring Tool Suite
Dependencias a usar:
- Spring Web: Necesaria para construir aplicaciones web.
- Spring Data JPA: Para trabajar con JPA y realizar operaciones de persistencia.
- MySQL Driver: Para conectar con la base de datos MySQL.
- Thymeleaf: Para construir el front-end.

![Captura de pantalla en ordenador]()

# Paso 2: Configurar la Base de Datos MySQL

```sh
  CREATE DATABASE nombreDeTuBaseDeDatos;
  USE DATABASE nombreDeTuBaseDeDatos;
  ```

Configurar el acceso a la base de datos en application.properties:
```sh
  spring.datasource.url=jdbc:mysql://localhost:3306/NOMBRE-DE-BASE-DE-DATOS
  spring.datasource.username=tu_usuario
  spring.datasource.password=tu_contraseña
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true
  spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

  ```
IMPORTANTE: Asegurate de cambiar el puerto (3306) y puede variar por PC

# Paso 3: Crear el Modelo, Repositorio, Servicio y Controlador

- Modelo (Entidad):
  Crea una clase en src/main/java/com/example/crudapp/model:
  
```sh
  package com.example.crudapp.model;

  import javax.persistence.Entity;
  import javax.persistence.GeneratedValue;
  import javax.persistence.GenerationType;
  import javax.persistence.Id;
  
  @Entity
  public class Persona {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      private String nombre;
      private String apellido;
  
      // Getters y Setters
  }

  ```
- Repositorio:
  Crea una interfaz en src/main/java/com/example/crudapp/repository:

  ```sh
  package com.example.crudapp.repository;

  import org.springframework.data.jpa.repository.JpaRepository;
  import com.example.crudapp.model.Persona;
  
  public interface PersonaRepository extends JpaRepository<Persona, Long> {
  }

  ```

  - Servicio:
    Crea una clase en src/main/java/com/example/crudapp/service:

    ```sh
      package com.example.crudapp.service;
      
      import org.springframework.beans.factory.annotation.Autowired;
      import org.springframework.stereotype.Service;
      import com.example.crudapp.model.Persona;
      import com.example.crudapp.repository.PersonaRepository;
      
      import java.util.List;
      
      @Service
      public class PersonaService {
      
          @Autowired
          private PersonaRepository personaRepository;
      
          public List<Persona> listarTodas() {
              return personaRepository.findAll();
          }
      
          public Persona guardar(Persona persona) {
              return personaRepository.save(persona);
          }
      
          public Persona obtenerPorId(Long id) {
              return personaRepository.findById(id).orElse(null);
          }
      
          public void eliminar(Long id) {
              personaRepository.deleteById(id);
          }
      }

  ```

  
  - Controlador:
    Crea una clase en src/main/java/com/example/crudapp/controller:

  
  ```sh
    package com.example.crudapp.controller;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import com.example.crudapp.model.Persona;
    import com.example.crudapp.service.PersonaService;
    
    @Controller
    @RequestMapping("/personas")
    public class PersonaController {
    
        @Autowired
        private PersonaService personaService;
    
        @GetMapping
        public String listarPersonas(Model model) {
            model.addAttribute("personas", personaService.listarTodas());
            return "persona-list";
        }
    
        @GetMapping("/nuevo")
        public String mostrarFormularioNuevaPersona(Model model) {
            model.addAttribute("persona", new Persona());
            return "persona-form";
        }
    
        @PostMapping
        public String guardarPersona(Persona persona) {
            personaService.guardar(persona);
            return "redirect:/personas";
        }
    
        @GetMapping("/editar/{id}")
        public String mostrarFormularioEditarPersona(@PathVariable Long id, Model model) {
            model.addAttribute("persona", personaService.obtenerPorId(id));
            return "persona-form";
        }
    
        @GetMapping("/eliminar/{id}")
        public String eliminarPersona(@PathVariable Long id) {
            personaService.eliminar(id);
            return "redirect:/personas";
        }
    }

  ```

  # Paso 4: Crear las Vistas con Thymeleaf
  - Crear una carpeta templates en src/main/resources y añade los archivos persona-list.html y persona-form.html:
    persona-list.html:

    ```sh
  <!DOCTYPE html>
  <html xmlns:th="http://www.thymeleaf.org">
  <head>
      <title>Lista de Personas</title>
  </head>
  <body>
      <h1>Lista de Personas</h1>
      <table>
          <thead>
              <tr>
                  <th>ID</th>
                  <th>Nombre</th>
                  <th>Apellido</th>
                  <th>Acciones</th>
              </tr>
          </thead>
          <tbody>
              <tr th:each="persona : ${personas}">
                  <td th:text="${persona.id}"></td>
                  <td th:text="${persona.nombre}"></td>
                  <td th:text="${persona.apellido}"></td>
                  <td>
                      <a th:href="@{/personas/editar/{id}(id=${persona.id})}">Editar</a>
                      <a th:href="@{/personas/eliminar/{id}(id=${persona.id})}">Eliminar</a>
                  </td>
              </tr>
          </tbody>
      </table>
      <a href="/personas/nuevo">Nueva Persona</a>
  </body>
  </html>

  ```

  - persona-form.html:

```sh
  <!DOCTYPE html>
  <html xmlns:th="http://www.thymeleaf.org">
  <head>
      <title>Formulario de Persona</title>
  </head>
  <body>
      <h1 th:text="${persona.id == null} ? 'Nueva Persona' : 'Editar Persona'"></h1>
      <form th:action="@{/personas}" th:object="${persona}" method="post">
          <input type="hidden" th:field="*{id}" />
          <label>Nombre:</label>
          <input type="text" th:field="*{nombre}" />
          <label>Apellido:</label>
          <input type="text" th:field="*{apellido}" />
          <button type="submit">Guardar</button>
      </form>
      <a href="/personas">Volver a la lista</a>
  </body>
  </html>

  ```

# Paso 5: Ejecutar el Proyecto
    
