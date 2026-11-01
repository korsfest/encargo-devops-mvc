# Microservicio MVC - Encargo DevOps

Breve descripción del proyecto, su propósito y la lógica de negocio que resuelve este microservicio Java.

## Tecnologías Utilizadas
* **Lenguaje:** Java 17
* **Gestor de dependencias:** Maven
* **Contenedores:** Docker y Docker Compose
* **Integración Continua (CI/CD):** GitHub Actions
* **Análisis de Seguridad:** Snyk
* **Pruebas Unitarias:** JUnit

## Estrategia de Ramificación (GitFlow)
Se implementó un modelo basado en GitFlow para asegurar la calidad del código y organizar el trabajo colaborativo:
* **`main`:** Contiene el código de producción estable. Cuenta con reglas de protección que exigen la aprobación de Pull Requests y el éxito del pipeline automatizado.
* **`develop`:** Rama base de integración donde se agrupan las nuevas características antes de pasar a producción.
* **`feature/*`:** Ramas temporales para el desarrollo de componentes específicos de forma aislada.
* **`hotfix/*`:** Ramas dedicadas a solucionar problemas críticos de manera rápida en el entorno de producción.

## Pipeline CI/CD
El flujo automatizado (`build-test-and-scan`) se dispara automáticamente ante cada push en `develop` o pull request hacia `main`. Sus etapas consisten en:
1. **Setup:** Configuración del entorno con Java 17 (Temurin).
2. **Test:** Ejecución de pruebas unitarias mediante JUnit.
3. **Package:** Compilación y empaquetado del microservicio ignorando temporalmente los tests ya ejecutados.
4. **Docker Build:** Construcción de la imagen del contenedor.
5. **Security Scan:** Análisis de vulnerabilidades estáticas con Snyk, configurado para bloquear el proceso si se detectan amenazas de criticidad alta.

## Despliegue Local
Para levantar el entorno de manera local con los contenedores orquestados, ejecuta el siguiente comando en la raíz del proyecto:
```bash
docker-compose up --build -d