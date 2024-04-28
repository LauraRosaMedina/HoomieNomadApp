package Proyecto;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ConexionBaseDatos.ConexionMySQL;

public class AnadirIntereses extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldInteres1;
    private JTextField textFieldInteres2;
    private JTextField textFieldInteres3;
    private JTextField textFieldInteres4;
    private JTextField textFieldInteres5;


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ConexionMySQL conexion = new ConexionMySQL("root", "test", "HoomieNomad");
                    conexion.conectar();
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos: " + e.getMessage());
                }
                AnadirIntereses frame = new AnadirIntereses();
                frame.setVisible(true);
            }
        });
    }

    public AnadirIntereses() {       
        setTitle("Añadir intereses");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        contentPane.setBackground(Color.WHITE); // Cambio de color del fondo del panel
        setContentPane(contentPane);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Font para el texto de los componentes
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 16);

        JLabel lblInteres1 = new JLabel("Interés 1:");
        lblInteres1.setForeground(new Color(0x769976)); // Cambio de color de la letra de la etiqueta
        lblInteres1.setFont(font); // Cambio de tamaño del texto
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(lblInteres1, gbc);

        textFieldInteres1 = new JTextField(20);
        textFieldInteres1.setFont(font); // Cambio de tamaño del texto
        gbc.gridx = 1;
        gbc.gridy = 0;
        contentPane.add(textFieldInteres1, gbc);

        JLabel lblInteres2 = new JLabel("Interés 2:");
        lblInteres2.setForeground(new Color(0x769976)); // Cambio de color de la letra de la etiqueta
        lblInteres2.setFont(font); // Cambio de tamaño del texto
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPane.add(lblInteres2, gbc);

        textFieldInteres2 = new JTextField(20);
        textFieldInteres2.setFont(font); // Cambio de tamaño del texto
        gbc.gridx = 1;
        gbc.gridy = 1;
        contentPane.add(textFieldInteres2, gbc);

        JLabel lblInteres3 = new JLabel("Interés 3:");
        lblInteres3.setForeground(new Color(0x769976)); // Cambio de color de la letra de la etiqueta
        lblInteres3.setFont(font); // Cambio de tamaño del texto
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPane.add(lblInteres3, gbc);

        textFieldInteres3 = new JTextField(20);
        textFieldInteres3.setFont(font); // Cambio de tamaño del texto
        gbc.gridx = 1;
        gbc.gridy = 2;
        contentPane.add(textFieldInteres3, gbc);

        JLabel lblInteres4 = new JLabel("Interés 4:");
        lblInteres4.setForeground(new Color(0x769976)); // Cambio de color de la letra de la etiqueta
        lblInteres4.setFont(font); // Cambio de tamaño del texto
        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPane.add(lblInteres4, gbc);

        textFieldInteres4 = new JTextField(20);
        textFieldInteres4.setFont(font); // Cambio de tamaño del texto
        gbc.gridx = 1;
        gbc.gridy = 3;
        contentPane.add(textFieldInteres4, gbc);

        JLabel lblInteres5 = new JLabel("Interés 5:");
        lblInteres5.setForeground(new Color(0x769976)); // Cambio de color de la letra de la etiqueta
        lblInteres5.setFont(font); // Cambio de tamaño del texto
        gbc.gridx = 0;
        gbc.gridy = 4;
        contentPane.add(lblInteres5, gbc);

        textFieldInteres5 = new JTextField(20);
        textFieldInteres5.setFont(font); // Cambio de tamaño del texto
        gbc.gridx = 1;
        gbc.gridy = 4;
        contentPane.add(textFieldInteres5, gbc);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(0x769976)); // Cambio de color del fondo del botón
        btnGuardar.setFont(font); // Cambio de tamaño del texto
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPane.add(btnGuardar, gbc);

        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String interes1 = textFieldInteres1.getText();
                String interes2 = textFieldInteres2.getText();
                String interes3 = textFieldInteres3.getText();
                String interes4 = textFieldInteres4.getText();
                String interes5 = textFieldInteres5.getText();
                try {    
                    // Obtener el id del usuario actualmente conectado
                    int idUsuario = Usuario.getIdUsuario();
                    
                    // Conectar a la base de datos
                    ConexionMySQL conexion = new ConexionMySQL("root", "test", "HoomieNomad");
                    conexion.conectar();
                                        
                    if (idUsuario != 0) {
                        // Insertar la nueva propiedad en la base de datos
                        String query = "INSERT INTO Intereses (id_usuario, interes_1, interes_2, interes_3, interes_4, interes_5) VALUES (?, ?, ?, ?, ?, ?)";
                        PreparedStatement statement = conexion.prepareStatement(query);
                        statement.setInt(1, idUsuario); // Establece el id_usuario en la consulta
                        statement.setString(2, interes1);
                        statement.setString(3, interes2);
                        statement.setString(4, interes3);
                        statement.setString(5, interes4);
                        statement.setString(6, interes5);
                        int filasAfectadas = statement.executeUpdate();

                        if (filasAfectadas > 0) {
                            JOptionPane.showMessageDialog(null, "Intereses guardados correctamente.");
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudieron guardar los intereses.");
                        }
                        
                    } else {
                        // Manejo del caso en el que la sesión o el usuario sean nulos
                        JOptionPane.showMessageDialog(null, "Error al obtener la sesión o el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al guardar los intereses: " + ex.getMessage());
                }
            }
        });
    }
}
