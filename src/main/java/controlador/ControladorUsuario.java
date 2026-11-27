package controlador;

import main.Main;
import modelo.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vista.VistaUsuario;

import java.util.ArrayList;
import java.util.List;

public class ControladorUsuario {
    private static final Logger logger = LoggerFactory.getLogger(ControladorUsuario.class);
    private final List<Usuario> usuarios = new ArrayList<>();
    private final VistaUsuario vista;

    public ControladorUsuario(VistaUsuario vista) {
        this.vista = vista;
    }

    public void agregarUsuario(Usuario u) {
        if (u == null) {
            logger.error("Error de validacion: intento de agregar usuario nulo");
            Main.enviarMetricaCloudWatch("ErroresAplicacion", 1.0);
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        usuarios.add(u);
        logger.info("Usuario agregado correctamente. Total: {}", usuarios.size());
        Main.enviarMetricaCloudWatch("UsuariosAgregados", 1.0);
    }

    public void actualizarVista() {
        if (vista != null) {
            vista.mostrarUsuarios(new ArrayList<>(usuarios));
            logger.info("Vista actualizada con {} usuarios", usuarios.size());
        } else {
            logger.error("Error al actualizar vista: VistaUsuario es null");
            Main.enviarMetricaCloudWatch("ErroresAplicacion", 1.0);
        }
    }

    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }
}