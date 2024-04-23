package Proyecto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
    private String nombreUsuario;
    private String contrasena;
    private int idUsuario;

    // Constructor
    public Usuario(String nombreUsuario, String contrasena) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    // Método para iniciar sesión
    public boolean iniciarSesion(Connection connection) throws SQLException {
        String query = "SELECT id_usuario FROM Usuario WHERE nombreUsuario = ? AND contrasena = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombreUsuario);
            statement.setString(2, contrasena);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    idUsuario = resultSet.getInt("id_usuario");
                    return true;
                }
            }
        }
        return false;
    }

    // Métodos getter para obtener información del usuario
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
}
