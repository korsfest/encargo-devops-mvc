package vista;

import java.util.ArrayList;
import modelo.Usuario;

public class VistaUsuario {
    public void mostrarUsuarios(ArrayList<Usuario> usuarios) {
        System.out.println("--- Lista de Usuarios ---");
        for (Usuario u : usuarios) {
            System.out.println("- " + u.getNombre());
        }
    }
}