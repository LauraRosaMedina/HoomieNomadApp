package Proyecto;

import javax.swing.*;
import ConexionBaseDatos.ConexionMySQL;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List; // Importación añadida
import java.util.ArrayList;

public class FeedPrincipal extends JFrame {

    private JPanel contentPane;
    private JPanel profilesPanel;
    private JTextField searchBar;
    private JButton searchButton;

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
    	setIconImage(Toolkit.getDefaultToolkit().getImage(FeedPrincipal.class.getResource("/Imagenes/Logo_marco.png.png")));
        setTitle("Feed Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 515, 702);
        setLocationRelativeTo(null);
        
        

        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
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
        // Agregar el botón de "Actualizar" encima de la barra de búsqueda
        topPanel.add(actualizarButton, BorderLayout.NORTH);

        // Agregar un espacio vertical entre el botón de actualizar y la barra de búsqueda
        topPanel.add(Box.createVerticalStrut(10)); // Cambiar el valor 10 según sea necesario

        // Crear la barra de búsqueda con texto de sugerencia
        searchBar = new JTextField("Filtrar propiedades por ubicación");
        searchBar.setPreferredSize(new Dimension(300, 30));
        searchBar.setMaximumSize(new Dimension(Short.MAX_VALUE, 30));

        // Agregar un FocusListener para limpiar el texto de sugerencia al hacer clic en la barra de búsqueda
        searchBar.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBar.getText().equals("Filtrar propiedades por ubicación")) {
                    searchBar.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchBar.getText().isEmpty()) {
                    searchBar.setText("Filtrar propiedades por ubicación");
                }
            }
        });

        topPanel.add(searchBar, BorderLayout.CENTER);

        // Botón de búsqueda
        searchButton = new JButton("Buscar");
        searchButton.setPreferredSize(new Dimension(80, 30)); // Establecer el tamaño preferido
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ubicacionBusqueda = searchBar.getText().toLowerCase();
                buscarYMostrarPorUbicacion(ubicacionBusqueda);
            }
        });

        // Agregar el botón de búsqueda a la derecha de la barra de búsqueda
        topPanel.add(searchButton, BorderLayout.EAST);

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
        
     /* Carga la imagen desde un recurso en el proyecto
        ImageIcon icono = new ImageIcon(getClass().getResource("/Imagenes/Logo_muypequeno.png.png"));

        // Crea un JLabel para mostrar la imagen
        JLabel imagenLabel = new JLabel(icono);
        imagenLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Crea un panel para superponer la imagen sobre el contenido existente
        JPanel overlayPanel = new JPanel();
        overlayPanel.setLayout(new OverlayLayout(overlayPanel));

        // Agrega la imagen al panel de superposición
        overlayPanel.add(imagenLabel);
        
        overlayPanel.setPreferredSize(new Dimension(110, 130)); // Ajusta los valores según el tamaño deseado

        // Agrega el panel de superposición al panel de contenido principal
        contentPane.add(overlayPanel, BorderLayout.NORTH);*/

        // Panel para mostrar los perfiles de los usuarios
        JPanel profilesPanel = new JPanel(new GridBagLayout());
        profilesPanel.setBackground(Color.WHITE);
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
                int idUsuario = resultSet.getInt("id_usuario");
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
        Color customTextColor = Color.WHITE; // Color personalizado para el texto de los botones
        cambiarColorBotones(this.getContentPane(), customButtonColor, customTextColor);
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
                    int propiedadId = propertiesResultSet.getInt("id_propiedad"); // Mover esta línea dentro del bucle

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
                    verCaracteristicasButton.setBackground(new Color(0x769976)); // Color de fondo
                    verCaracteristicasButton.setForeground(Color.WHITE); // Color del texto
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
                    verCaracteristicasButton.addActionListener(e -> {
                        JOptionPane.showMessageDialog(null, mensaje.toString(), "Características de la propiedad", JOptionPane.INFORMATION_MESSAGE);
                    });
                    propertySubPanel.add(verCaracteristicasButton, BorderLayout.CENTER);

                    // Botón para reservar la propiedad
                    JButton reservarButton = new JButton("Reservar");
                    reservarButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                // Cambiar el estado de disponibilidad de la propiedad a false en la base de datos
                                cambiarEstadoDisponibilidadPropiedad(propiedadId, false);
                                
                             // Realizar la reserva en la base de datos
                                reservarPropiedad(Usuario.getIdUsuario(), propiedadId);
                                
                                // Mostrar mensaje de reserva exitosa
                                JOptionPane.showMessageDialog(null, "Propiedad reservada con éxito.");
                                // Cerrar la ventana de características de la propiedad
                                profileFrame.dispose();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Error al reservar la propiedad: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });
                    propertySubPanel.add(reservarButton, BorderLayout.WEST);

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
    
    private void reservarPropiedad(int idUsuario, int idPropiedad) throws SQLException {
        ConexionMySQL conexion = ConexionMySQL.obtenerInstancia();
        conexion.conectar();

        // Consulta para insertar la reserva en la tabla Reservas
        String query = "INSERT INTO Reservas (id_usuario, id_propiedad) VALUES (?, ?)";
        PreparedStatement statement = conexion.prepareStatement(query);
        statement.setInt(1, Usuario.getIdUsuario());
        statement.setInt(2, idPropiedad);
        statement.executeUpdate();

        // Cerrar la conexión
        conexion.desconectar();
    }
    
    public static void cambiarEstadoDisponibilidadPropiedad(int idPropiedad, boolean disponible) throws SQLException {
        ConexionMySQL conexion = ConexionMySQL.obtenerInstancia();
        conexion.conectar();

        // Consulta para actualizar el estado de disponibilidad de la propiedad
        String query = "UPDATE Propiedades SET disponible = ? WHERE id_propiedad = ?";
        PreparedStatement statement = conexion.prepareStatement(query);
        statement.setBoolean(1, disponible);
        statement.setInt(2, idPropiedad);
        statement.executeUpdate();

        // Cerrar la conexión
        conexion.desconectar();
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
            statement.setString(1, Usuario.getNombre()); // Aquí establecemos el nombre de usuario para excluirlo de la consulta
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

    private void buscarYMostrarPorUbicacion(String ubicacion) {
        try {
            ConexionMySQL conexion = ConexionMySQL.obtenerInstancia();
            conexion.conectar();

            // Consulta SQL para seleccionar las propiedades con la ubicación especificada
            String query = "SELECT * FROM Propiedades WHERE LOWER(ubicacion) = LOWER(?) AND id_usuario NOT IN (SELECT id_usuario FROM Usuario WHERE nombre = ?)";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, ubicacion);
            statement.setString(2, Usuario.getNombre()); // Aquí se establece el valor del segundo parámetro
            ResultSet resultSet = statement.executeQuery();

            // Verificar si hay resultados en el ResultSet
            if (!resultSet.isBeforeFirst()) {
                JOptionPane.showMessageDialog(null, "No se encontraron propiedades en la ubicación especificada.", "Búsqueda sin resultados", JOptionPane.INFORMATION_MESSAGE);
                return; // Salir del método si no hay resultados
            }

            // Mostrar las propiedades encontradas en una ventana emergente
            JFrame searchFrame = new JFrame("Propiedades en " + ubicacion);
            searchFrame.getContentPane().setLayout(new BoxLayout(searchFrame.getContentPane(), BoxLayout.Y_AXIS)); // Cambia el layout al eje Y

            // Crear un panel de desplazamiento para mostrar todas las propiedades
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Deshabilitar la barra de desplazamiento horizontal

            // Crear un panel para agregar todas las propiedades
            JPanel propertiesPanel = new JPanel();
            propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS)); // Cambiar el layout al eje Y

            int contadorPropiedad = 1;

            while (resultSet.next()) {
                // Crear un panel para mostrar cada propiedad
                JPanel propertyPanel = new JPanel(new BorderLayout());
                propertyPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10),
                        BorderFactory.createLineBorder(Color.GRAY)
                ));

                // Mostrar el título de la propiedad
                JLabel titleLabel = new JLabel("<html><b>Propiedad " + contadorPropiedad + "</b></html>");
                propertyPanel.add(titleLabel, BorderLayout.NORTH);

                // Obtener los datos de la primera fila del ResultSet para mostrar las características
                int idUsuario = resultSet.getInt("id_usuario");
                String nombre = obtenerNombreUsuario(idUsuario);
                String tipoCasa = resultSet.getString("tipo_de_casa");
                int numBanos = resultSet.getInt("num_banos");
                int numHabitaciones = resultSet.getInt("num_habitaciones");
                String terrazaPatio = resultSet.getString("terraza_patio");
                String ubicacionPropiedad = resultSet.getString("ubicacion");
                String garaje = resultSet.getString("garaje");
                String piscina = resultSet.getString("piscina");
                int numOcupantes = resultSet.getInt("num_ocupantes");
                boolean disponible = resultSet.getBoolean("disponible");

                int propiedadId = resultSet.getInt("id_propiedad"); // Obtener el ID de la propiedad desde el ResultSet
             // Agregar el botón para reservar la propiedad
                JButton reservarButton = new JButton("Reservar");
                reservarButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            // Cambiar el estado de disponibilidad de la propiedad a false en la base de datos
                            cambiarEstadoDisponibilidadPropiedad(propiedadId, false);
                            
                            // Realizar la reserva en la base de datos
                            reservarPropiedad(Usuario.getIdUsuario(), propiedadId);
                            
                            // Mostrar mensaje de reserva exitosa
                            JOptionPane.showMessageDialog(null, "Propiedad reservada con éxito.");
                            // Cerrar la ventana de búsqueda de propiedades
                            searchFrame.dispose(); // Asumiendo que searchFrame es la referencia al JFrame donde se muestran las propiedades
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error al reservar la propiedad: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                propertyPanel.add(reservarButton, BorderLayout.WEST); // Colocar el botón "Reservar" a la izquierda

                // Agregar el botón para ver características
                JButton viewButton = new JButton("Ver características");
                viewButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Construir el mensaje con las características de la propiedad
                        StringBuilder mensaje = new StringBuilder();
                        mensaje.append("Propiedad de: ").append(nombre).append("\n");
                        mensaje.append("Tipo de casa: ").append(tipoCasa).append("\n");
                        mensaje.append("Número de baños: ").append(numBanos).append("\n");
                        mensaje.append("Número de habitaciones: ").append(numHabitaciones).append("\n");
                        mensaje.append("Terraza/Patio: ").append(terrazaPatio).append("\n");
                        mensaje.append("Ubicación: ").append(ubicacionPropiedad).append("\n");
                        mensaje.append("Garaje: ").append(garaje).append("\n");
                        mensaje.append("Piscina: ").append(piscina).append("\n");
                        mensaje.append("Número de ocupantes: ").append(numOcupantes).append("\n");
                        mensaje.append("Disponible: ").append(disponible ? "Sí" : "No").append("\n\n");
                        JOptionPane.showMessageDialog(null, mensaje.toString(), "Características de la propiedad", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                propertyPanel.add(viewButton, BorderLayout.CENTER); // Colocar el botón "Ver características" en el centro

                // Agregar el panel de propiedad al panel de propiedades
                propertiesPanel.add(propertyPanel);
            }

            // Agregar el panel de propiedades al panel de desplazamiento
            scrollPane.setViewportView(propertiesPanel);

            // Agregar el panel de desplazamiento al marco de búsqueda
            searchFrame.getContentPane().add(scrollPane);

            // Mostrar el marco de búsqueda
            searchFrame.setSize(400, 300);
            searchFrame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
            searchFrame.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al buscar propiedades: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private String obtenerNombreUsuario(int idUsuario) throws SQLException {
        ConexionMySQL conexion = ConexionMySQL.obtenerInstancia();
        conexion.conectar();

        // Consulta para obtener el nombre de usuario por ID
        String query = "SELECT nombre FROM Usuario WHERE id_usuario = ?";
        PreparedStatement statement = conexion.prepareStatement(query);
        statement.setInt(1, idUsuario);
        ResultSet resultSet = statement.executeQuery();

        // Verificar si hay un resultado en el ResultSet
        if (resultSet.next()) {
            // Devolver el nombre de usuario
            return resultSet.getString("nombre");
        }

        // Si no se encuentra ningún resultado, devolver una cadena vacía
        return "";
    }

    private void cambiarColorBotones(Container container, Color customButtonColor, Color customTextColor) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.setBackground(customButtonColor);
                button.setForeground(customTextColor);
                button.setFocusPainted(false); // Deshabilitar el efecto de enfoque
            } else if (component instanceof Container) {
                cambiarColorBotones((Container) component, customButtonColor, customTextColor);
            }
        }
    }
}
