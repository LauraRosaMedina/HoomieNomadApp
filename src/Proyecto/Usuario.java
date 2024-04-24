package Proyecto;

public class Usuario {
    
	private static String nombreUsuario;
    private static String contrasena;
    private static int idUsuario;

    // Métodos getter para obtener información del usuario
    public static String getNombreUsuario() {
        return nombreUsuario;
    }

    public static String getContrasena() {
        return contrasena;
    }
    
    public static int getIdUsuario() {
        return idUsuario;
    }
    
    // Métodos setter para obtener información del usuario
    public static void setNombreUsuario(String nombreUsuario) {
        Usuario.nombreUsuario = nombreUsuario;
    }

    public static void setContrasena(String contrasena) {
        Usuario.contrasena = contrasena;
    }
    
    public static void setIdUsuario(int idUsuario) {
        Usuario.idUsuario = idUsuario;
    }
}
