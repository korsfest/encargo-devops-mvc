package controlador;

import java.util.ArrayList;
import vista.VistaUsuario;
import modelo.Usuario;

public class ControladorUsuario {
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private VistaUsuario vista;

    public ControladorUsuario(VistaUsuario vista) {
        this.vista = vista;
    }

    public void agregarUsuario(Usuario u) {
        usuarios.add(u);
    }

    public void actualizarVista() {
        vista.mostrarUsuarios(usuarios);
    }
}