package Proyecto;

import javax.swing.*;
import ConexionBaseDatos.ConexionMySQL;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FeedPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel profilesPanel;

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
        
        profilesPanel = new JPanel(new GridBagLayout());
        contentPane.add(profilesPanel, BorderLayout.CENTER);

        // Panel para los botones
        JPanel topPanel = new JPanel(new BorderLayout());
        contentPane.add(topPanel, BorderLayout.NORTH);
        
        // Crear el botón de "Actualizar"
        JButton actualizarButton = new JButton("Actualizar");
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarPerfiles();
            }
        });
        // Agregar el botón de "Actualizar" en la esquina superior derecha
        topPanel.add(actualizarButton, BorderLayout.EAST);

        // Crear la barra de menú
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Obtener el nombre de usuario con sesión iniciada
        String nombreUsuario = Usuario.getNombre();

        // Crear el menú desplegable con el nombre de usuario
        JMenu menuUsuario = new JMenu(nombreUsuario + " ∨");
        menuBar.add(menuUsuario);
        
        // Añadir un MouseListener al menú de usuario para resaltar cuando se pasa el ratón sobre él
        menuUsuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	Color customColor = new Color(0x769976); // Crear un color personalizado con el código hexadecimal
                menuUsuario.setForeground(customColor); // Aplicar el color personalizado al texto
            }

            @Override
            public void mouseExited(MouseEvent e) {
                menuUsuario.setForeground(UIManager.getColor("Menu.foreground")); // Restaurar el color del texto cuando el ratón sale
            }
        });
        
     // Aumentar el tamaño de la fuente y ponerla en negrita
        Font font = menuUsuario.getFont();
        Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize() + 4); // Crear una fuente en negrita con el tamaño aumentado
        menuUsuario.setFont(boldFont); // Aplicar la fuente en negrita con el tamaño aumentado al texto

                
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

     // Panel para mostrar los perfiles de los usuarios
        JPanel profilesPanel = new JPanel(new GridBagLayout());
        contentPane.add(profilesPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        try {
            ConexionMySQL conexion = ConexionMySQL.obtenerInstancia();
            conexion.conectar();

            // Obtener todos los usuarios excepto el usuario actual
            String query = "SELECT DISTINCT Usuario.id_Usuario, Usuario.nombre FROM Usuario LEFT JOIN Propiedades ON Usuario.id_Usuario = Propiedades.id_Usuario WHERE Usuario.nombre != ?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, nombreUsuario); // Aquí establecemos el nombre de usuario para excluirlo de la consulta
            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
                // Obtener información del usuario
                int idUsuario = resultSet.getInt("id_Usuario");
                String nombreUsuarioDB = resultSet.getString("nombre");

                // Crear panel para mostrar el perfil del usuario
                JPanel profilePanel = new JPanel(new BorderLayout());
                profilePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

                // Mostrar el nombre del usuario
                JLabel nameLabel = new JLabel(nombreUsuarioDB);
                profilePanel.add(nameLabel, BorderLayout.NORTH);

                // Botón para ver el perfil completo
                JButton viewProfileButton = new JButton("Ver perfil");
                viewProfileButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mostrarPerfil(idUsuario, nombreUsuarioDB);
                    }
                });
                profilePanel.add(viewProfileButton, BorderLayout.CENTER);
                profilesPanel.add(profilePanel, gbc);
                gbc.gridy++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener los perfiles de los usuarios: " + ex.getMessage());
        }
        
        Color customButtonColor = new Color(0x769976); // Color personalizado para los botones
        cambiarColorBotones(this.getContentPane(), customButtonColor);

    }

    private void mostrarPerfil(int idUsuario, String nombreUsuario) {
        try {
            ConexionMySQL conexion = ConexionMySQL.obtenerInstancia();
            conexion.conectar();

            // Obtener las propiedades del usuario de la base de datos
            String propertiesQuery = "SELECT * FROM Propiedades WHERE id_usuario = ?";
            PreparedStatement propertiesStatement = conexion.prepareStatement(propertiesQuery);
            propertiesStatement.setInt(1, idUsuario);
            ResultSet propertiesResultSet = propertiesStatement.executeQuery();

            // Verificar si el usuario tiene propiedades registradas
            if (propertiesResultSet.next()) {
                // El usuario tiene propiedades, mostrarlas en una ventana emergente
                JFrame profileFrame = new JFrame("Perfil de " + nombreUsuario);
                profileFrame.setSize(400, 300);
                profileFrame.setLocationRelativeTo(null);

                // Panel para las propiedades del usuario
                JPanel propertyPanel = new JPanel();
                propertyPanel.setLayout(new GridBagLayout());
                GridBagConstraints gbc1 = new GridBagConstraints();
                gbc1.gridx = 0;
                gbc1.gridy = 0;
                gbc1.insets = new Insets(10, 10, 10, 10); // Aumenta el espaciado entre propiedades
                gbc1.anchor = GridBagConstraints.WEST;

                int contadorPropiedad = 1; // Inicializamos el contador en 1

                do {
                    // Mostrar las propiedades del usuario
                    JPanel propertySubPanel = new JPanel(new BorderLayout());
                    propertySubPanel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createEmptyBorder(10, 10, 10, 10),
                            BorderFactory.createLineBorder(Color.GRAY)
                    ));

                    JLabel titleLabel = new JLabel("<html><b>Propiedad " + contadorPropiedad + "</b></html>");
                    titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16)); // Cambia el tamaño y el estilo de la fuente
                    propertySubPanel.add(titleLabel, BorderLayout.NORTH);

                    JButton verCaracteristicasButton = new JButton("Ver características");
                    StringBuilder mensaje = new StringBuilder();
                    mensaje.append("<html>");
                    mensaje.append("Tipo de casa: ").append(propertiesResultSet.getString("tipo_de_casa")).append("<br>");
                    mensaje.append("Número de baños: ").append(propertiesResultSet.getInt("num_banos")).append("<br>");
                    mensaje.append("Número de habitaciones: ").append(propertiesResultSet.getInt("num_habitaciones")).append("<br>");
                    mensaje.append("Terraza/Patio: ").append(propertiesResultSet.getString("terraza_patio")).append("<br>");
                    mensaje.append("Ubicación: ").append(propertiesResultSet.getString("ubicacion")).append("<br>");
                    mensaje.append("Garaje: ").append(propertiesResultSet.getString("garaje")).append("<br>");
                    mensaje.append("Piscina: ").append(propertiesResultSet.getString("piscina")).append("<br>");
                    mensaje.append("Número de ocupantes: ").append(propertiesResultSet.getInt("num_ocupantes")).append("<br>");
                    mensaje.append("Disponible: ").append(propertiesResultSet.getBoolean("disponible") ? "Sí" : "No").append("</html>");
                    verCaracteristicasButton.addActionListener(evt -> {
                        JOptionPane.showMessageDialog(null, mensaje.toString(), "Características de la propiedad", JOptionPane.INFORMATION_MESSAGE);
                    });
                    propertySubPanel.add(verCaracteristicasButton, BorderLayout.CENTER);

                    propertyPanel.add(propertySubPanel, gbc1);
                    gbc1.gridy++;
                    
                    contadorPropiedad++; // Incrementamos el contador
                } while (propertiesResultSet.next());

                // Agregar el panel de propiedades al contenido del perfil
                profileFrame.setContentPane(propertyPanel);
                profileFrame.setVisible(true);
            } else {
                // El usuario no tiene propiedades registradas, mostrar un mensaje
                JOptionPane.showMessageDialog(null, "El usuario no tiene propiedades registradas.", "Perfil de " + nombreUsuario, JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener las propiedades del usuario: " + ex.getMessage());
        }
    }
    
    private void actualizarPerfiles() {
        profilesPanel.removeAll(); // Elimina todos los perfiles mostrados actualmente
        profilesPanel.revalidate(); // Vuelve a validar el panel para actualizar la interfaz
        profilesPanel.repaint(); // Vuelve a pintar el panel para actualizar la interfaz

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        try {
            ConexionMySQL conexion = ConexionMySQL.obtenerInstancia();
            conexion.conectar();

         // Obtener todos los usuarios excepto el usuario actual
            String query = "SELECT DISTINCT Usuario.id_Usuario, Usuario.nombre FROM Usuario LEFT JOIN Propiedades ON Usuario.id_Usuario = Propiedades.id_Usuario WHERE Usuario.nombre != ?";
            PreparedStatement statement = conexion.prepareStatement(query);
            String nombreUsuario = Usuario.getNombre();
			statement.setString(1, nombreUsuario ); // Aquí establecemos el nombre de usuario para excluirlo de la consulta
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Obtener información del usuario
                int idUsuario = resultSet.getInt("id_Usuario");
                String nombreUsuarioDB = resultSet.getString("nombre");

                // Crear panel para mostrar el perfil del usuario
                JPanel profilePanel = new JPanel(new BorderLayout());
                profilePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

                // Mostrar el nombre del usuario
                JLabel nameLabel = new JLabel(nombreUsuarioDB);
                profilePanel.add(nameLabel, BorderLayout.NORTH);

                // Botón para ver el perfil completo
                JButton viewProfileButton = new JButton("Ver perfil");
                viewProfileButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mostrarPerfil(idUsuario, nombreUsuarioDB);
                    }
                });
                profilePanel.add(viewProfileButton, BorderLayout.CENTER);
                profilesPanel.add(profilePanel, gbc);
                gbc.gridy++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener los perfiles de los usuarios: " + ex.getMessage());
        }
    }

    public void cambiarColorBotones(Container container, Color color) {
    	for (Component component : container.getComponents()) {
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBackground(color); // Establecer el color de fondo personalizado para el botón
                button.setForeground(Color.BLACK); // Mantener el color del texto del botón en negro
            } else if (component instanceof Container) {
                cambiarColorBotones((Container) component, color); // Llamar recursivamente para los contenedores internos
            }
        }
    }


}