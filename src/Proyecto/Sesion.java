package Proyecto;

import java.sql.Connection;
import java.sql.SQLException;

public class Sesion {
	
	private Usuario usuario;
    private Connection connection;

    // Constructor sin argumentos
    public Sesion() {
        
    }
    
    // Constructor con argumentos
    public Sesion(Connection connection) {
        this.connection = connection;
    }

    // Método para iniciar sesión
    public boolean iniciarSesion(String nombreUsuario, String contrasena) throws SQLException {
        usuario = new Usuario(nombreUsuario, contrasena);
        return usuario.iniciarSesion(connection);
    }

    // Método para cerrar sesión
    public void cerrarSesion() {
        usuario = null;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    
    // Método para verificar si hay una sesión activa
    public boolean sesionActiva() {
        return usuario != null;
    }

    // Método para obtener el nombre de usuario de la sesión actual
    public String obtenerNombreUsuario() {
        return usuario != null ? usuario.getNombreUsuario() : null;
    }

    // Método para obtener el ID de usuario de la sesión actual
    public int obtenerIdUsuario() {
        return usuario != null ? usuario.getIdUsuario() : -1;
    }
}