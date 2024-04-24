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
import java.sql.SQLException;

public class FeedPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

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

                        FeedPrincipal frame = new FeedPrincipal();
                        frame.setVisible(true);
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
                    /* Crear una instancia de ConexionMySQL y conectar a la base de datos
                    ConexionMySQL conexion = new ConexionMySQL("root", "test", "HoomieNomad");
                    conexion.conectar();*/

                    // Abrir la ventana de añadir propiedades y pasar la sesión
                    AnadirPropiedades anadirPropiedades = new AnadirPropiedades();
                    anadirPropiedades.setVisible(true);
            }
        });
        topPanel.add(addPropertiesButton, BorderLayout.SOUTH);
    }



}
