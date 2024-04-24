package Proyecto;

import javax.swing.*;

import ConexionBaseDatos.ConexionMySQL;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class FeedPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FeedPrincipal frame = new FeedPrincipal();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
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

        // Panel para los botones
        JPanel topPanel = new JPanel(new BorderLayout());
        contentPane.add(topPanel, BorderLayout.NORTH);

        // Crear la barra de menú
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Obtener el nombre de usuario con sesión iniciada
        String nombreUsuario = Usuario.getNombreUsuario();

        // Crear el menú desplegable con el nombre de usuario
        JMenu menuUsuario = new JMenu(nombreUsuario);
        menuBar.add(menuUsuario);

        // Opción "Gestionar Perfil"
        JMenuItem gestionarPerfilMenuItem = new JMenuItem("Gestionar Perfil");
        gestionarPerfilMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Abrir la ventana de gestionar perfil
            	dispose();
                GestionarPerfil gestionarPerfil = new GestionarPerfil();
                gestionarPerfil.setVisible(true);
            }
        });
        menuUsuario.add(gestionarPerfilMenuItem);

        // Opción "Ajustes"
        JMenuItem ajustesMenuItem = new JMenuItem("Ajustes");
        ajustesMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	 Ajustes ajustes = new Ajustes();
                 ajustes.setVisible(true);
            }
        });
        menuUsuario.add(ajustesMenuItem);

        // Separador
        menuUsuario.addSeparator();

     // Opción "Cerrar Sesión"
        JMenuItem cerrarSesionMenuItem = new JMenuItem("Cerrar Sesión");
        cerrarSesionMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                	ConexionMySQL conexion = ConexionMySQL.obtenerInstancia();
                    // Desconectar de la base de datos
                    conexion.desconectar();

                    // Cerrar la ventana actual
                    dispose();

                    // Abrir la ventana de inicio de sesión (login)
                    Login login = new Login();
                    login.setVisible(true);
                } catch (SQLException ex) {
                    // Manejar cualquier excepción que pueda ocurrir al cerrar la conexión
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al cerrar sesión. Por favor, inténtelo de nuevo.");
                }
            }
        });
        menuUsuario.add(cerrarSesionMenuItem);

    }

}
