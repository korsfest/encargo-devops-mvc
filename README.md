# Microservicio de Gestión (Java MVC)

## 1. Estrategia de Ramificación
Para este proyecto colaborativo hemos elegido **GitFlow**. Se justifica porque permite una separación clara de los entornos:
* `main`: Contiene el código de producción estable.
* `develop`: Base para integrar las nuevas funcionalidades (features) del modelo, vista o controlador.
* `feature/<nombre>`: Permite trabajar de forma aislada en componentes específicos sin interrumpir el desarrollo principal.
* `hotfix/<nombre>`: Garantiza una respuesta rápida ante bugs críticos en producción.

## 2. Convenciones y Buenas Prácticas
* **Mensajes de Commit:** Utilizamos *Conventional Commits*. 
  * `feat:` para nuevas funcionalidades (ej. `feat: agregar ArrayList al controlador`).
  * `fix:` para solucionar errores (ej. `fix: resolver error de compilación en vista`).
  * `docs:` para actualizaciones en el README.
* **Naming de Ramas:** 
  * Features: `feature/crear-modelo-usuario`, `feature/vistas-consola`.
  * Hotfixes: `hotfix/npe-controlador`.
* **Flujos de Merge y Estrategia de Revisión:**
  * Todo desarrollo se realiza en ramas paralelas (`feature/*`).
  * La integración a `develop` o `main` se realiza **exclusivamente mediante Pull Requests (PR)**.
  * Se requiere la aprobación de al menos 1 revisor del equipo (el compañero de la pareja) para asegurar la calidad del código antes de hacer merge.