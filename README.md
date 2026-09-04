#microservicio de gestion 

#estrategia de ramificacion utilizada:

nosotros, para este proyecto, hemos elegido gitflow. ya que este mismo permite una separacion clara entre los entornos:
 "main": contiene el codigo del proyecto.
 "develop": base para integrar las nuevas funcionalidades (features) del modelo, vista o controlador.
 "feature/<nombre>": permite trabajar de forma aislada en, componentes específicos sin que el desarrollo se vea interrumpido.
 "hotfix/<nombre>": garantizará una respuesta rápida ante ante problemas criticos que pueda haber en el desarrollo.

convenciones y buenas practicas:
 mensajes de los commit: usamos conventional commits. 
   "feat:" para nuevas funcionalidades. 
   "fix:" para solucionar errores. 
   "docs:" para cualquier actualizacion que se le haga al README.
 naming de las ramas:
  RAMAS FEATURE: se usa para desarrollar nuevas caracteristicas en el microservicio.
  RAMAS HOTFIX: se usa exclusivamente para solucionar problemas criticos en el main.
   features: feature/crear-modelo-usuario, feature/vistas-consola.
   hotfixes: hotfix/npe-controlador.
 estrategia para la revision y flujos de merge:
   cualquier tipo de desarrollo, se realiza en ramas paralelas (feature/*).
   la integración a "develop" o "main" se realiza de manera exclusiva mediante Pull Requests (PR).
   se requiere aprobacion de al menos 1 revisor del equipo para asegurar la integridad y calidad del codigo antes de hacer merge.

algunos otros modelos de ramificacion analizados:

  "Trunk-based development":
  todo el equipo trabaja sobre una rama principal ("main" o "trunk"), integrando de esta manera, pequeños cambios de forma continua (varias veces al dia).
  ventajas en la nube para este modelo: Maximizará la velocidad del pipeline CI/CD y evitará conflictos grandes de integración a la  hora de eliminar ramas de duracion larga.
  por qué no lo usamos ? : es algo riesgoso para un equipo estudiantil inexperto, porque exige un sistema que tenga pruebas automatizadas, de manera muy estricta, para llegar a no romper algo ante cualquier error.

  "GitHub Flow":
  es un modelo mas agil, y bastante mas simplificado, que solo utiliza la rama "main" y ramas cortas de "feature" que se integran haciendo Pull Requests.
  ventajas en la nube para este modelo: muy ligero y rápido para aplicaciones web que despliegan a producción (main) de manera continua.
  por qué no lo usamos: este modelo, carece de un entorno intermedio de prueba antes a la hora de mandar los cambios a la versión final.

por qué ocupamos gitflow en nuestro proyecto ? :
elegimos gitflow porque, a la hora de trabajar tres desarrolladores en al mismo tiempo en github, nos ofrecía 2 ventajas que sobresalían:
1: aislamiento e integración ("develop"): actuará como un "escudo", donde juntaremos las ramas "feature/*" de cada integrante del grupo y validaremos que el microservicio funcione correctamente antes de tocar la versión oficial.
2: estabilidad en la producción ("main"): este mantendrá la versión entregable limpia, y nos dará el mecanismo de "hotfix/*" para resolver cualquier tipo de emergencias que se pueda anteponer sin entorpecer el avance de las demás funcionalidades.