package controlador;

import modelo.Usuario;
import org.junit.jupiter.api.Test;
import vista.VistaUsuario;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControladorUsuarioTest {

    @Test
    void testAgregarUsuarioValido() {
        VistaUsuario vista = new VistaUsuario();
        ControladorUsuario controlador = new ControladorUsuario(vista);
        Usuario u = new Usuario(1, "Vicente");

        controlador.agregarUsuario(u);
        List<Usuario> lista = controlador.getUsuarios();

        assertEquals(1, lista.size());
        assertEquals("Vicente", lista.get(0).getNombre());
    }

    @Test
    void testAgregarUsuarioNuloLanzaExcepcion() {
        VistaUsuario vista = new VistaUsuario();
        ControladorUsuario controlador = new ControladorUsuario(vista);

        assertThrows(IllegalArgumentException.class, () -> controlador.agregarUsuario(null));
    }

    @Test
    void testActualizarVistaSinErrores() {
        VistaUsuario vista = new VistaUsuario();
        ControladorUsuario controlador = new ControladorUsuario(vista);
        controlador.agregarUsuario(new Usuario(2, "Diego"));

        assertDoesNotThrow(controlador::actualizarVista);
    }

    @Test
    void testActualizarVistaConVistaNulaNoRompeElPrograma() {
        ControladorUsuario controlador = new ControladorUsuario(null);
        assertDoesNotThrow(controlador::actualizarVista);
    }
}