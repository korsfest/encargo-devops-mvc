package vista;
import modelo.Usuario;
import java.util.ArrayList;

public class VistaUsuario {
    public void mostrarUsuarios(ArrayList<Usuario> usuarios) {
        System.out.println("--- Lista de Usuarios ---");
        for (Usuario u : usuarios) {
            System.out.println("- " + u.getNombre());
        }
    }
}