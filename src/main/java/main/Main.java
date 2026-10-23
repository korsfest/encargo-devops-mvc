package main;

import controlador.ControladorUsuario;
import modelo.Usuario;
import vista.VistaUsuario;

public class Main {
    public static void main(String[] args) {
        VistaUsuario vista = new VistaUsuario();
        ControladorUsuario controlador = new ControladorUsuario(vista);

        // Se ajustaron los IDs para que no se repita el valor "1"
        controlador.agregarUsuario(new Usuario(1, "cristóbal"));
        controlador.agregarUsuario(new Usuario(2, "francisco"));
        controlador.agregarUsuario(new Usuario(3, "vicente"));
        controlador.agregarUsuario(new Usuario(4, "diego"));

        controlador.actualizarVista();
        //correcion de error nulo.
    }
}