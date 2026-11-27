# Microservicio MVC — Observabilidad, Despliegue y Cumplimiento DevOps

Proyecto correspondiente a la **Evaluación Parcial N°3** de la asignatura **Ingeniería DevOps (DOY0101 - Duoc UC)**. Consiste en la extensión de un microservicio Java bajo arquitectura Modelo-Vista-Controlador (MVC) para la gestión de usuarios, integrando trazabilidad completa desde el desarrollo hasta el despliegue automatizado en la nube con observabilidad en **AWS CloudWatch** y validación continua de calidad y seguridad mediante **SonarCloud** y **Snyk**.

---

## Tecnologías Utilizadas
* **Lenguaje:** Java 17 (empaquetado como Fat JAR con `maven-shade-plugin`)
* **Gestor de dependencias:** Apache Maven
* **Contenedores y Orquestación:** Docker y Docker Compose (`app-mvc` + `mysql:8.0`)
* **Integración y Despliegue Continuo (CI/CD):** GitHub Actions
* **Despliegue en la Nube:** Amazon EC2 (`us-east-1`)
* **Observabilidad (Monitoreo, Logs y Dashboards):** AWS CloudWatch (SDK v2 + driver `awslogs`)
* **Calidad, Cobertura y Seguridad:** JUnit 5, JaCoCo, SonarCloud (`Quality Gate`) y Snyk CLI

---

## Estructura del Proyecto

```text
encargo-devops-mvc/
├── .github/
│   ├── dependabot.yml              # Actualización semanal automatizada de dependencias (Maven y Docker)
│   └── workflows/
│       └── ci.yaml                 # Pipeline CI/CD (Build, Test, JaCoCo, SonarCloud, Snyk y Deploy EC2)
├── src/
│   ├── main/java/
│   │   ├── controlador/            # Lógica de negocio y emisión de métricas/logs (ControladorUsuario.java)
│   │   ├── main/                   # Servidor HTTP embebido, endpoints y cliente CloudWatch (Main.java)
│   │   ├── modelo/                 # Entidades del dominio (Usuario.java)
│   │   └── vista/                  # Salida de representación (VistaUsuario.java)
│   └── test/java/                  # Pruebas unitarias automatizadas con JUnit 5
├── BUENAS_PRACTICAS.md             # Estándares de GitFlow, Conventional Commits y flujo de PRs
├── Dockerfile                      # Construcción multi-etapa (Maven Builder + Eclipse Temurin 17 Alpine)
├── docker-compose.yml              # Orquestación de servicios (app-mvc y db) con driver awslogs
├── pom.xml                         # Configuración de dependencias y plugins (JaCoCo, Sonar, Shade)
└── README.md                       # Documentación técnica del proyecto
```

---

## Endpoints del Microservicio

El servicio levanta un servidor HTTP en el puerto `8080` con rutas instrumentadas para monitoreo y pruebas de observabilidad:

| Endpoint | Método | Descripción | Métrica / Log Asociado |
| :--- | :--- | :--- | :--- |
| `/` | `GET` | Renderiza el listado activo de usuarios del microservicio (`HTTP 200`). | Envía `PeticionesExitosas` (`Count`) y registra acceso en logs. |
| `/health` | `GET` | Endpoint de *healthcheck* (`{"status":"UP"}`) para verificar disponibilidad. | Envía `DisponibilidadHealth` (`Count`) a CloudWatch. |
| `/error-test` | `GET` | Simula una falla crítica controlada (`HTTP 500`) para validar alertas y logs. | Registra `ERROR CRITICO SIMULADO` y envía `ErroresAplicacion` (`Count`). |

---

## Observabilidad y Monitoreo en AWS CloudWatch (IE1, IE2, IE3)

### 1. Recolección Centralizada de Logs (`awslogs`)
En `docker-compose.yml`, tanto el contenedor del microservicio (`app-mvc`) como la base de datos (`mysql-db`) están configurados con el driver nativo `awslogs` apuntando a la región `us-east-1`:
* **Log Group:** `/devops/encargo-mvc`
* **Log Streams:** `app-mvc-container` y `mysql-db-container`
* **Trazabilidad:** Todos los eventos emitidos mediante `SLF4J` (`INFO`, `WARN`, `ERROR`) se envían en tiempo real a CloudWatch Logs sin depender de almacenamiento efímero en la instancia.

### 2. Métricas Personalizadas y de Infraestructura
Mediante el SDK de AWS (`software.amazon.awssdk:cloudwatch`), la aplicación publica métricas personalizadas bajo el namespace **`EncargoDevOps/Microservicio`** (dimensión `Servicio: encargo-devops-mvc`):
* **`DisponibilidadHealth`:** Confirma el estado activo del servicio al iniciar y ante consultas al endpoint `/health`.
* **`PeticionesExitosas` / `UsuariosAgregados`:** Miden el tráfico y la operación funcional del controlador.
* **`ErroresAplicacion`:** Contabiliza excepciones de validación o errores HTTP 500 (`/error-test`).
* **`TiempoDespliegueSegundos`:** Registrada automáticamente vía AWS CLI al finalizar cada despliegue en EC2 dentro del pipeline.

### 3. Dashboard en CloudWatch
Se configuró un dashboard personalizado en AWS CloudWatch que consolida en una sola vista:
* **Tiempo de despliegue (`TiempoDespliegueSegundos`):** Duración de cada actualización en producción.
* **Uso de recursos de cómputo (EC2):** Utilización de CPU (`CPUUtilization`) y tráfico de red de la instancia.
* **Disponibilidad y tráfico:** Evolución de `DisponibilidadHealth` y `PeticionesExitosas`.
* **Errores registrados:** Gráfica de `ErroresAplicacion` vinculada a alertas y widget de logs recientes de `/devops/encargo-mvc`.

---

## Estrategia de Ramificación (GitFlow) y Gobernanza (IE5)
Se implementó un modelo basado en GitFlow (`BUENAS_PRACTICAS.md`) para asegurar la calidad del código y organizar el trabajo colaborativo:
* **`main`:** Contiene el código de producción estable. Cuenta con reglas de protección (*Branch Protection Rules*) que bloquean pushes directos, exigen la aprobación de Pull Requests y requieren el éxito del pipeline automatizado (`build-test-and-scan`).
* **`develop`:** Rama base de integración donde se agrupan las nuevas características antes de pasar a producción.
* **`feature/*`:** Ramas temporales para el desarrollo de componentes específicos de forma aislada.
* **`hotfix/*`:** Ramas dedicadas a solucionar problemas críticos de manera rápida en el entorno de producción.

---