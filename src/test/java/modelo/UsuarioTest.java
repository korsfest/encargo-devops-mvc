package modelo;

import org.junit.jupiter.api.Test;

import modelo.Usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioTest {
    
    @Test
    public void testGetNombre() {
        Usuario usuario = new Usuario(1, "Francisco");
        assertEquals("Francisco", usuario.getNombre(), "El nombre del usuario debe coincidir con el asignado");
    }
}