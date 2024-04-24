package Proyecto;

import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ConexionBaseDatos.ConexionMySQL;

public class AnadirIntereses extends JFrame {

	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                    // Crear una instancia de ConexionMySQL y conectar a la base de datos
                    ConexionMySQL conexion = new ConexionMySQL("root", "test", "HoomieNomad");
                    try {
						conexion.conectar();
					} catch (SQLException e) {
						e.printStackTrace();
	                    JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos: " + e.getMessage());
					}

                    AnadirPropiedades frame = new AnadirPropiedades(); // Pasa la sesión como argumento al constructor
                    frame.setVisible(true);
            }
        });
    }
    
    public AnadirIntereses() {
    	
    }
	
}
