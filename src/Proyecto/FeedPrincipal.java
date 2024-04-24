package Proyecto;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ConexionBaseDatos.ConexionMySQL;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class FeedPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Usuario usuario = new Usuario ("nombreUsuario", "contrasena"); // Nuevo campo para almacenar la instancia de Usuario

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Crear una instancia de ConexionMySQL y conectar a la base de datos
                    ConexionMySQL conexion = new ConexionMySQL("root", "test", "HoomieNomad");
                    conexion.conectar();

                    // Obtener la conexión a la base de datos
                    Connection connection = conexion.getConnection();

                    // Crear una nueva instancia de Sesion con la conexión obtenida
                    Sesion sesion = new Sesion(connection); // Crea una nueva sesión

                    // Verificar si hay una sesión activa
                    if (sesion.sesionActiva()) {
                        FeedPrincipal frame = new FeedPrincipal();
                        frame.setVisible(true);
                    } else {
                        // Manejar el caso en el que no haya una sesión activa
                        JOptionPane.showMessageDialog(null, "No hay una sesión activa.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + e.getMessage());
                }
            }
        });
    }


    public FeedPrincipal() {
    	setTitle("Feed Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 515, 702);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Panel para el botón de "Añadir Propiedades"
        JPanel topPanel = new JPanel(new BorderLayout());
        contentPane.add(topPanel, BorderLayout.NORTH);

        // Botón de "Añadir Propiedades"
        JButton addPropertiesButton = new JButton("Añadir Propiedades");
        addPropertiesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Crear una instancia de ConexionMySQL y conectar a la base de datos
                    ConexionMySQL conexion = new ConexionMySQL("root", "test", "HoomieNomad");
                    conexion.conectar();

                    // Obtener la conexión a la base de datos
                    Connection connection = conexion.getConnection();

                    // Crear una nueva instancia de Sesion con la conexión obtenida
                    Sesion sesion = new Sesion(connection); // Crea una nueva sesión

                    // Abrir la ventana de añadir propiedades y pasar la sesión
                    AnadirPropiedades anadirPropiedades = new AnadirPropiedades(sesion);
                    anadirPropiedades.setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
                }
            }
        });
        topPanel.add(addPropertiesButton, BorderLayout.SOUTH);
    }


	// Setter para establecer la instancia de Usuario
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    // Getter para obtener la instancia de Usuario
    public Usuario getUsuario() {
        return usuario;
    }
}
