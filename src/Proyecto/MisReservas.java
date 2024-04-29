package Proyecto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import ConexionBaseDatos.ConexionMySQL;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MisReservas extends JFrame {

    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MisReservas frame = new MisReservas();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MisReservas() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/Imagenes/Logo_marco.png.png")));
        setTitle("Mis reservas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 515, 702);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(0, 1, 0, 10));
        
     // Crear la barra de menú
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Obtener el nombre de usuario con sesión iniciada
        String nombreUsuario = Usuario.getNombre();

        // Crear el menú desplegable con el nombre de usuario
        JMenu menuUsuario = new JMenu(nombreUsuario + " ∨");
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
        
        // Agregar el menú en la esquina superior izquierda
        add(menuBar, BorderLayout.NORTH);

        // Botón de "Atrás"
        JButton backButton = new JButton("Inicio");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Crear una instancia de la clase FeedPrincipal
                FeedPrincipal feedPrincipal = new FeedPrincipal();
                // Hacer visible la ventana del feed principal
                feedPrincipal.setVisible(true);
                // Cerrar la ventana actual
                dispose();
            }
        });

        // Agregar el botón de "Atrás" en la esquina superior derecha
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.NORTH);

        // Obtener las reservas del usuario actual desde la base de datos
        try {
            ConexionMySQL conexion = ConexionMySQL.obtenerInstancia();
            conexion.conectar();
            String query = "SELECT Reservas.* FROM Reservas WHERE Reservas.id_usuario = " + Usuario.getIdUsuario() + " "; 
            ResultSet resultSet = conexion.ejecutarSelect(query);
         
         // Variable para llevar la cuenta del número de reserva
            int numeroReserva = 1;

            while (resultSet.next()) {
                // Obtener los detalles de la propiedad reservada
                int idReserva = resultSet.getInt("id_reserva");
                // Obtener más detalles según sea necesario

                // Crear un panel para mostrar la información de la propiedad reservada
                JPanel propertyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
                propertyPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
               //código nuevo sólo una línea
                propertyPanel.setName("propertyPanel_" + idReserva);
                // Mostrar la información de la propiedad
                JLabel lblNumeroReserva = new JLabel("Reserva " + numeroReserva + ":");
                propertyPanel.add(lblNumeroReserva);

             // Botón "Ver Detalles"
                JButton detailsButton = new JButton("Ver Detalles");
                detailsButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Obtener el ID de la propiedad asociada al botón
                        int idPropiedad = obtenerIdReservaDelBoton((JButton) e.getSource());

                        // Llamar al método para mostrar las características de la propiedad
                        mostrarCaracteristicasPropiedad(idPropiedad);
                    }
                });
                propertyPanel.add(detailsButton);

             // Botón "Cancelar Reserva"
                JButton cancelButton = new JButton("Cancelar Reserva");
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Obtener el ID de la reserva asociada al botón
                        int idReserva = obtenerIdReservaDelBoton((JButton) e.getSource());

                        // Cancelar la reserva
                        cancelarReserva(idReserva);

                        // Eliminar visualmente la reserva cancelada
                        eliminarReservaVisualmente(idReserva, panel);
                    }
                });
                propertyPanel.add(cancelButton);



                // Agregar el panel de la propiedad al panel principal
                panel.add(propertyPanel);
                
                // Incrementar el número de reserva para la siguiente iteración
                numeroReserva++;
            }

            // Cerrar la conexión
            resultSet.close();
            conexion.desconectar();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener las reservas: " + ex.getMessage());
            
        }
        
        
        
    }
    
    
    // Método para obtener el ID de la reserva asociada al botón
    private int obtenerIdReservaDelBoton(JButton button) {
        // Obtener el panel padre del botón
        Container container = button.getParent();
        
        // Iterar a través de los paneles padres hasta encontrar el panel que contiene el ID de la reserva
        while (container != null) {
            // Verificar si el panel es un JPanel y tiene un nombre que contiene el ID de la reserva
            if (container instanceof JPanel && container.getName() != null && container.getName().startsWith("propertyPanel")) {
                // Dividir el nombre del panel para extraer el ID de la reserva
                String[] parts = container.getName().split("_");
                if (parts.length == 2) {
                    try {
                        // Intentar convertir la segunda parte del nombre en un entero (ID de la reserva)
                        int idReserva = Integer.parseInt(parts[1]);
                        return idReserva; // Retornar el ID de la reserva
                    } catch (NumberFormatException e) {
                        e.printStackTrace(); // Manejar la excepción si la conversión falla
                    }
                }
            }
            // Obtener el panel padre del panel actual
            container = container.getParent();
        }
        return -1; // Retornar -1 si no se pudo encontrar el ID de la reserva
    }

    
    private void mostrarCaracteristicasPropiedad(int idPropiedad) {
        try {
            ConexionMySQL conexion = ConexionMySQL.obtenerInstancia();
            conexion.conectar();

            // Consulta para obtener las características de la propiedad
            String query = "SELECT Reservas.*, Propiedades.*, Usuario.nombre FROM Reservas " +
                    "INNER JOIN Propiedades ON Reservas.id_propiedad = Propiedades.id_propiedad " +
                    "INNER JOIN Usuario ON Propiedades.id_usuario = Usuario.id_usuario " +
                    "WHERE Reservas.id_usuario = " + Usuario.getIdUsuario() + " "; 
            ResultSet resultSet = conexion.ejecutarSelect(query);

            if (resultSet.next()) {
                // Obtener las características de la propiedad junto con el nombre del propietario
                String nombrePropietario = resultSet.getString("Usuario.nombre");
                String tipoCasa = resultSet.getString("tipo_de_casa");
                int numBanos = resultSet.getInt("num_banos");
                int numHabitaciones = resultSet.getInt("num_habitaciones");
                String terrazaPatio = resultSet.getString("terraza_patio");
                String ubicacionPropiedad = resultSet.getString("ubicacion");
                String garaje = resultSet.getString("garaje");
                String piscina = resultSet.getString("piscina");
                int numOcupantes = resultSet.getInt("num_ocupantes");
                boolean disponible = resultSet.getBoolean("disponible");

                // Construir el mensaje con las características de la propiedad y el nombre del propietario
                StringBuilder mensaje = new StringBuilder();
                mensaje.append("<html>");
                mensaje.append("<b>Propietario:</b> ").append(nombrePropietario).append("<br>");
                mensaje.append("<b>Tipo de casa:</b> ").append(tipoCasa).append("<br>");
                mensaje.append("<b>Número de baños:</b> ").append(numBanos).append("<br>");
                mensaje.append("<b>Número de habitaciones:</b> ").append(numHabitaciones).append("<br>");
                mensaje.append("<b>Terraza/Patio:</b> ").append(terrazaPatio).append("<br>");
                mensaje.append("<b>Ubicación:</b> ").append(ubicacionPropiedad).append("<br>");
                mensaje.append("<b>Garaje:</b> ").append(garaje).append("<br>");
                mensaje.append("<b>Piscina:</b> ").append(piscina).append("<br>");
                mensaje.append("<b>Número de ocupantes:</b> ").append(numOcupantes).append("<br>");
                mensaje.append("<b>Disponible:</b> ").append(disponible ? "Sí" : "No").append("<br>");
                mensaje.append("</html>");

                // Mostrar las características en un cuadro de diálogo
                JOptionPane.showMessageDialog(null, mensaje.toString(), "Características de la propiedad", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // La propiedad no fue encontrada
                JOptionPane.showMessageDialog(null, "No se encontraron características para la propiedad con ID: " + idPropiedad, "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Cerrar la conexión y liberar recursos
            resultSet.close();
            conexion.desconectar();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener las características de la propiedad: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cancelarReserva(int idReserva) {
        try {
            ConexionMySQL conexion = ConexionMySQL.obtenerInstancia();
            conexion.conectar();

            // Obtener el ID de la propiedad asociada a la reserva
            String queryIdPropiedad = "SELECT id_propiedad FROM Reservas WHERE id_reserva =" + idReserva + "";
            ResultSet resultSetIdPropiedad = conexion.ejecutarSelect(queryIdPropiedad);

            if (resultSetIdPropiedad.next()) {
                int idPropiedad = resultSetIdPropiedad.getInt("id_propiedad");

                // Actualizar la disponibilidad de la propiedad a true en la tabla Propiedades
                String queryActualizarPropiedad = "UPDATE Propiedades SET disponible = true WHERE id_propiedad = " + idPropiedad + "";
                conexion.ejecutarInsertDeleteUpdate(queryActualizarPropiedad);

                
                // Borrar el registro de la tabla Reservas
                String queryBorrarReserva = " DELETE FROM Reservas WHERE Reservas.id_reserva = ' " + idReserva + "'";
                conexion.ejecutarInsertDeleteUpdate(queryBorrarReserva);

                JOptionPane.showMessageDialog(null, "Reserva cancelada con éxito.");

            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la reserva correspondiente con ID: " + idReserva, "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Cerrar recursos
            resultSetIdPropiedad.close();
            conexion.desconectar();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cancelar la reserva: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
 // Método para eliminar visualmente una reserva de la interfaz gráfica
    private void eliminarReservaVisualmente(int idReserva, JPanel panel) {
        // Itera sobre todos los componentes en el panel
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel propertyPanel = (JPanel) component;
                // Comprueba si el panel representa la reserva que se canceló
                int reservaId = obtenerIdReservaDelPanel(propertyPanel);
                if (reservaId == idReserva) {
                    // Elimina el panel de la reserva
                    panel.remove(propertyPanel);
                    // Revalida y repinta el panel para que la interfaz refleje los cambios
                    panel.revalidate();
                    panel.repaint();
                    break; // No es necesario seguir iterando
                }
            }
        }
    }


 // Método para obtener el ID de la reserva desde el panel visual de la reserva
    private int obtenerIdReservaDelPanel(JPanel propertyPanel) {
        // Obtiene el nombre del panel
        String panelName = propertyPanel.getName();
        
        // Verifica si el nombre del panel no es nulo y sigue un formato específico que incluye el ID de la reserva
        if (panelName != null && panelName.startsWith("propertyPanel")) {
            // Divide el nombre del panel usando "_" como delimitador
            String[] parts = panelName.split("_");
            
            // Verifica si hay al menos dos partes en el nombre del panel y la segunda parte contiene el ID de la reserva
            if (parts.length >= 2) {
                // Intenta convertir la segunda parte a un entero que representa el ID de la reserva
                try {
                    int idReserva = Integer.parseInt(parts[1]);
                    return idReserva; // Retorna el ID de la reserva
                } catch (NumberFormatException e) {
                    // Maneja la excepción si la segunda parte del nombre no es un número válido
                    e.printStackTrace();
                }
            }
        }
        
        // Retorna -1 si no se pudo extraer el ID de la reserva del nombre del panel
        return -1;
    }






}
