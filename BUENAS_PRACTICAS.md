1: naming de ramas (estrategia gitflow)
utilizamos el modelo de GitFlow para separar el codigo en desarrollo, produccion y nuevas caracteristicas:

rama principal (main): contiene de manera exclusiva el codigo listo para produccion y estable. no se editará directamente sobre ella.
rama de integracion (develop): rama base donde se unificarán las nuevas funcionalidades, antes de que estas pasen a producción.
ramas de funcionalidad (feature/): utilizadas para desarrollar nuevas características o vistas. nacen de develop y se fusionan en ella.
ramas de corrección de urgencia (hotfix/): se utilizan para solucionar errores críticos, los cuales se detectan en produccion. nacen de manera obligatoria de main , y luego se fusionan de vuelta en main y develop 

2: convención de mensajes de commit (Conventional Commits)
los commits tienen que ser claros, descriptivos, y comenzar con un prefijo según la acción a realizar:
feat: para agregar una nuevas características o funcionalidades 
fix: para corregir un error o bug que pueda haber en el codigo 
docs: para actualizaciones o a la hora de crear documentos 
chore: para tareas de mantenimiento, o configuración de entorno, o tambien archivos de ignorados (como .gitignore).

3: estructura de las carpetas (ModeloVistaControlador)
este proyecto organiza sus componentes lógicos y físicos separando responsabilidades bajo el patrón Modelo-Vista-Controlador:
.github/workflows/: este contiene los archivos de configuración YAML que sirven para la automatización de la integración continua (CI) con github actions.
controlador/: contiene la lógica del programa y los controladores que conectan lo que ponga el usuario con los datos.
modelo/: este contiene las clases de las entidades, y estructuras de datos (arrays). 
vista/: este almacena las interfaces de usuario, o los componentes visuales que tenga el sistema.
raiz del pryecto(root o main) contiene archivos globales esenciales como .gitignore y el README.md.

4: control de versiones y flujo de colaboración
Pull Requests (PR): Ningún cambio se integra de forma directa a develop o main sin que este pase por un Pull Request.
Revisión Cruzada: los pull requests deben ser revisados y aprobados por un miembro del equipo distinto al autor antes de realizar el Merge.
integración continua (CI): cada pull request ejecutará automáticamente un pipeline en github actions que compilará el código fuente en java, para garantizar que no exista ningun error de sintaxis ni de compilación antes de que se acepten los cambios.
