package Proyecto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ConexionBaseDatos.ConexionMySQL;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionarPerfil extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    JPanel propertyPanel;
    GridBagConstraints gbc;

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
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Panel para el botón de "Atrás", los ajustes y el botón "Actualizar"
        JPanel topPanel = new JPanel(new BorderLayout());
        contentPane.add(topPanel, BorderLayout.NORTH);

        // Crear la barra de menú
        JMenuBar menuBar = new JMenuBar();
        topPanel.add(menuBar, BorderLayout.WEST);

        // Obtener el nombre de usuario con sesión iniciada
        String nombre = Usuario.getNombre();

        // Crear el menú desplegable con el nombre de usuario
        JMenu menuUsuario = new JMenu(nombre + " ∨");
        menuUsuario.setOpaque(true); // Configurar el menú desplegable como opaco
        menuUsuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar el color de fondo cuando el ratón entra en el menú desplegable
                menuUsuario.setBackground(new Color(0x769976));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar el color de fondo cuando el ratón sale del menú desplegable
                menuUsuario.setBackground(null);
            }
        });
        menuBar.add(menuUsuario);

        // Opción "Gestionar Perfil"
        JMenuItem gestionarPerfilMenuItem = new JMenuItem("Gestionar Perfil");
        gestionarPerfilMenuItem.addActionListener(e -> {
            // Abrir la ventana de gestionar perfil
            dispose();
            GestionarPerfil gestionarPerfil = new GestionarPerfil();
            gestionarPerfil.setVisible(true);
        });
        menuUsuario.add(gestionarPerfilMenuItem);
        
     // Opción "Mis reservas"
        JMenuItem misReservasMenuItem = new JMenuItem("Mis Reservas");
        misReservasMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar el color de fondo cuando el ratón entra en la opción del menú
                misReservasMenuItem.setBackground(new Color(0x769976));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar el color de fondo cuando el ratón sale de la opción del menú
                misReservasMenuItem.setBackground(null);
            }
        });
        misReservasMenuItem.addActionListener(e -> {
            // Abrir la ventana de mis reservas
            dispose();
            MisReservas misReservas = new MisReservas();
            misReservas.setVisible(true); // Aquí es donde se debe llamar al método setVisible(true)
        });
        menuUsuario.add(misReservasMenuItem);

        // Opción "Ajustes"
        JMenuItem ajustesMenuItem = new JMenuItem("Ajustes");
        ajustesMenuItem.addActionListener(e -> {
            dispose();
            Ajustes ajustes = new Ajustes();
            ajustes.setVisible(true);
        });
        menuUsuario.add(ajustesMenuItem);

        // Separador
        menuUsuario.addSeparator();

        // Opción "Cerrar Sesión"
        JMenuItem cerrarSesionMenuItem = new JMenuItem("Cerrar Sesión");
        cerrarSesionMenuItem.addActionListener(e -> {
            // Cerrar sesión
        });
        menuUsuario.add(cerrarSesionMenuItem);

        // Botón de "Inicio"
        JButton backButton = new JButton("Inicio");
        backButton.setBackground(new Color(0x769976)); // Color de fondo
        backButton.setForeground(Color.WHITE); // Color del texto
        backButton.addActionListener(e -> {
            // Acción al presionar el botón "Atrás"
            FeedPrincipal feedPrincipal = new FeedPrincipal();
            feedPrincipal.setVisible(true);
            dispose();
        });
        topPanel.add(backButton, BorderLayout.EAST);

        // Crear un nuevo panel para los botones "Actualizar", "Añadir Propiedades" y "Añadir Intereses"
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Botón "Actualizar"
        JButton actualizarButton = new JButton("Actualizar");
        actualizarButton.setBackground(new Color(0x769976)); // Color de fondo
        actualizarButton.setForeground(Color.WHITE); // Color del texto
        actualizarButton.addActionListener(e -> {
            // Actualizar la lista de propiedades
            actualizarPropiedades(propertyPanel);
        });
        buttonsPanel.add(actualizarButton);

        // Botón de "Añadir Propiedades"
        JButton addPropertiesButton = new JButton("Añadir Propiedades");
        addPropertiesButton.setBackground(new Color(0x769976)); // Color de fondo
        addPropertiesButton.setForeground(Color.WHITE); // Color del texto
        addPropertiesButton.addActionListener(e -> {
            AnadirPropiedades anadirPropiedades = new AnadirPropiedades(); // Pasar una referencia de esta ventana
            anadirPropiedades.setVisible(true);
        });
        buttonsPanel.add(addPropertiesButton);

        // Botón de "Añadir Intereses"
        JButton addInterestsButton = new JButton("Añadir Intereses");
        addInterestsButton.setBackground(new Color(0x769976)); // Color de fondo
        addInterestsButton.setForeground(Color.WHITE); // Color del texto
        addInterestsButton.addActionListener(e -> {
            AnadirIntereses anadirIntereses = new AnadirIntereses();
            anadirIntereses.setVisible(true);
        });
        buttonsPanel.add(addInterestsButton);

        propertyPanel = new JPanel();
        contentPane.add(propertyPanel, BorderLayout.CENTER);
        propertyPanel.setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Aumenta el espaciado entre propiedades
        gbc.anchor = GridBagConstraints.WEST;

        actualizarPropiedades(propertyPanel); // Llamar al método para cargar las propiedades

        propertyPanel.revalidate();
        propertyPanel.repaint();
    }
    // Método para actualizar la lista de propiedades
    public void actualizarPropiedades(JPanel propertyPanel) {
        propertyPanel.removeAll(); // Limpiar el panel antes de agregar nuevas propiedades

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

                JLabel titleLabel = new JLabel("<html><font color='#769976'><b><font size='+1'>Propiedad " + contadorPropiedades + "</font></b></font></html>");
                propiedadPanel.add(titleLabel, BorderLayout.NORTH);

                JButton verCaracteristicasButton = new JButton("Ver características");
                verCaracteristicasButton.setBackground(new Color(0x769976)); // Color de fondo
                verCaracteristicasButton.setForeground(Color.WHITE); // Color del texto
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

                propertyPanel.add(propiedadPanel, gbc); // Aquí utilizamos la instancia de GridBagConstraints (gbc)
                contadorPropiedades++;
                gbc.gridy++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener las propiedades del usuario: " + ex.getMessage());
        } finally {
            propertyPanel.revalidate(); // Revalidar el panel después de hacer cambios
            propertyPanel.repaint();    // Repintar el panel después de hacer cambios
        }
    }

}
