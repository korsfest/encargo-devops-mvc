package modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void testConstructorBasicoYGetters() {
        Usuario usuario = new Usuario(1, "Cris");

        assertEquals(1, usuario.getId());
        assertEquals("Cris", usuario.getNombre());
        assertNull(usuario.getCorreo());
    }

    @Test
    void testConstructorCompletoYSetters() {
        Usuario usuario = new Usuario(2, "Francisco", "francisco@duocuc.cl");
        usuario.setId(10);
        usuario.setNombre("Vicente");
        usuario.setCorreo("vicente@duocuc.cl");

        assertEquals(10, usuario.getId());
        assertEquals("Vicente", usuario.getNombre());
        assertEquals("vicente@duocuc.cl", usuario.getCorreo());
    }
}