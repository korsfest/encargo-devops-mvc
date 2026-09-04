package main;
import modelo.Usuario;
import vista.VistaUsuario;
import controlador.ControladorUsuario;

public class Main {
    public static void main(String[] args) {
        VistaUsuario vista = new VistaUsuario();
        ControladorUsuario controlador = new ControladorUsuario(vista);

        controlador.agregarUsuario(new Usuario(1, "cristóbal"));
        controlador.agregarUsuario(new Usuario(1, "cristóbal"));
        controlador.agregarUsuario(new Usuario(1, "francisco"));
        controlador.agregarUsuario(new Usuario(1, "vicente"));
        controlador.agregarUsuario(new Usuario(1, "diego"));

        controlador.actualizarVista();
    }
}