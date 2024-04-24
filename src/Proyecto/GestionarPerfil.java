package Proyecto;

import javax.swing.*;

import ConexionBaseDatos.ConexionMySQL;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionarPerfil extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GestionarPerfil frame = new GestionarPerfil();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public GestionarPerfil() {
        setTitle("Gestionar Perfil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 515, 702);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

     // Panel para el botón de "Atrás" y "Añadir Propiedades"
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        contentPane.add(topPanel, BorderLayout.NORTH);

        // Botón de "Atrás"
        JButton backButton = new JButton("← Atrás");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FeedPrincipal feedPrincipal = new FeedPrincipal();
                feedPrincipal.setVisible(true);
                dispose();
            }
        });
        topPanel.add(backButton);

        // Botón de "Añadir Propiedades"
        JButton addPropertiesButton = new JButton("Añadir Propiedades");
        addPropertiesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AnadirPropiedades anadirPropiedades = new AnadirPropiedades();
                anadirPropiedades.setVisible(true);
            }
        });
        topPanel.add(addPropertiesButton);

        // Botón de "Añadir Intereses"
        JButton addInterestsButton = new JButton("Añadir Intereses");
        addInterestsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AnadirIntereses anadirIntereses = new AnadirIntereses();
                anadirIntereses.setVisible(true);
            }
        });
        topPanel.add(addInterestsButton);


        JPanel propertyPanel = new JPanel();
        contentPane.add(propertyPanel, BorderLayout.CENTER);
        propertyPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.insets = new Insets(10, 10, 10, 10); // Aumenta el espaciado entre propiedades
        gbc1.anchor = GridBagConstraints.WEST;

        ConexionMySQL conexion = new ConexionMySQL("root", "test", "HoomieNomad");

        try {
            conexion.conectar();
            ResultSet resultSet = conexion.ejecutarSelect("SELECT * FROM Propiedades WHERE id_usuario = " + Usuario.getIdUsuario());
            int contadorPropiedades = 1;

            while (resultSet.next()) {
                JPanel propiedadPanel = new JPanel(new BorderLayout());
                propiedadPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10),
                        BorderFactory.createLineBorder(Color.GRAY)
                ));

                JLabel titleLabel = new JLabel("<html><b>Propiedad " + contadorPropiedades + "</b></html>");
                titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16)); // Cambia el tamaño y el estilo de la fuente
                propiedadPanel.add(titleLabel, BorderLayout.NORTH);

                JButton verCaracteristicasButton = new JButton("Ver características");
                StringBuilder mensaje = new StringBuilder();
                mensaje.append("<html>");
                mensaje.append("Tipo de casa: ").append(resultSet.getString("tipo_de_casa")).append("<br>");
                mensaje.append("Número de baños: ").append(resultSet.getInt("num_banos")).append("<br>");
                mensaje.append("Número de habitaciones: ").append(resultSet.getInt("num_habitaciones")).append("<br>");
                mensaje.append("Terraza/Patio: ").append(resultSet.getString("terraza_patio")).append("<br>");
                mensaje.append("Ubicación: ").append(resultSet.getString("ubicacion")).append("<br>");
                mensaje.append("Garaje: ").append(resultSet.getString("garaje")).append("<br>");
                mensaje.append("Piscina: ").append(resultSet.getString("piscina")).append("<br>");
                mensaje.append("Número de ocupantes: ").append(resultSet.getInt("num_ocupantes")).append("<br>");
                mensaje.append("Disponible: ").append(resultSet.getBoolean("disponible") ? "Sí" : "No").append("</html>");
                verCaracteristicasButton.addActionListener(e -> {
                    JOptionPane.showMessageDialog(null, mensaje.toString(), "Características de la propiedad", JOptionPane.INFORMATION_MESSAGE);
                });
                propiedadPanel.add(verCaracteristicasButton, BorderLayout.CENTER);

                propertyPanel.add(propiedadPanel, gbc1);
                contadorPropiedades++;
                gbc1.gridy++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener las propiedades del usuario: " + ex.getMessage());
        }
        propertyPanel.revalidate();
        propertyPanel.repaint();
    }


}
