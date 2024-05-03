package Proyecto;

import java.awt.EventQueue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import ConexionBaseDatos.ConexionMySQL;

public class Ajustes extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Ajustes frame = new Ajustes();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Ajustes() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/Imagenes/Logo_marco.png.png")));
        setTitle("Ajustes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 515, 702);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Panel para el botón de "Atrás" y los ajustes
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
        gestionarPerfilMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar el color de fondo cuando el ratón entra en la opción del menú
                gestionarPerfilMenuItem.setBackground(new Color(0x769976));
            }

            @Override
          public void mouseExited(MouseEvent e) {
                // Restaurar el color de fondo cuando el ratón sale de la opción del menú
                gestionarPerfilMenuItem.setBackground(null);
            }
        });
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
        ajustesMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar el color de fondo cuando el ratón entra en la opción del menú
                ajustesMenuItem.setBackground(new Color(0x769976));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar el color de fondo cuando el ratón sale de la opción del menú
                ajustesMenuItem.setBackground(null);
            }
        });
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


        // Botón de "Inicio"
        JButton backButton = new JButton("Inicio");
        backButton.addActionListener(e -> {
            // Acción al presionar el botón "Atrás"
            dispose();
            FeedPrincipal feedPrincipal = new FeedPrincipal();
            feedPrincipal.setVisible(true);
        });
        topPanel.add(backButton, BorderLayout.EAST);
        
        backButton.setForeground(Color.WHITE); // Cambio de color del texto
        backButton.setBackground(new Color(0x769976)); // Cambio de color del fondo del botón
        

        // Panel para los ajustes
        JPanel settingsPanel = new JPanel(new BorderLayout());
        contentPane.add(settingsPanel, BorderLayout.CENTER);
        
     // Panel para los campos de contraseña y el botón de cerrar cuenta
        JPanel settingsPanel2 = new JPanel(new GridBagLayout()) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			// Sobreescribe el método paintComponent para dibujar la imagen de fondo
        	 @Override
        	    protected void paintComponent(Graphics g) {
        	        super.paintComponent(g);
        	        try {
        	            // Carga la imagen desde el archivo de recursos del proyecto
        	            BufferedImage image = ImageIO.read(getClass().getResource("/Imagenes/coche_difuminado2.png"));
        	            // Dibuja la imagen estirándola para que ocupe todo el espacio del panel
        	            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        	        } catch (IOException ex) {
        	            ex.printStackTrace();
        	        }
        	    }
        	};
        	settingsPanel.add(settingsPanel2, BorderLayout.CENTER);       

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.insets = new Insets(20, 5, 5, 5); // Espaciado entre componentes
        gbc2.anchor = GridBagConstraints.WEST; // Alineación izquierda
        
        
        // Título "Actualizar Contraseña"
        JLabel changePasswordTitleLabel = new JLabel("Actualizar Contraseña");
        changePasswordTitleLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Fuente, negrita, tamaño
        changePasswordTitleLabel.setForeground(new Color(0x769976)); // Cambio de color del texto
        settingsPanel2.add(changePasswordTitleLabel, gbc2);

        gbc2.gridx = 0;
        gbc2.gridy++;

        // Campo de texto para contraseña actual
        JLabel currentPasswordLabel = new JLabel("Contraseña actual:");
        currentPasswordLabel.setForeground(new Color(0x769976)); // Cambio de color del texto
        settingsPanel2.add(currentPasswordLabel, gbc2);

        gbc2.gridx++;
        JPasswordField currentPasswordField = new JPasswordField(20);
        settingsPanel2.add(currentPasswordField, gbc2);

        gbc2.gridx = 0;
        gbc2.gridy++;

        // Campo de texto para nueva contraseña
        JLabel newPasswordLabel = new JLabel("Nueva contraseña:");
        newPasswordLabel.setForeground(new Color(0x769976)); // Cambio de color del texto
        settingsPanel2.add(newPasswordLabel, gbc2);

        gbc2.gridx++;
        JPasswordField newPasswordField = new JPasswordField(20);
        settingsPanel2.add(newPasswordField, gbc2);

        gbc2.gridx = 0;
        gbc2.gridy++;

        // Botón para cambiar contraseña
        JButton changePasswordButton = new JButton("Cambiar Contraseña");
        changePasswordButton.addActionListener(e -> {
            ConexionMySQL conexion = new ConexionMySQL("root", "test", "HoomieNomad");
            try {
                conexion.conectar();
                // Verificación de la contraseña actual y cambio de contraseña en la base de datos
                String nombreUsuario = Usuario.getNombreUsuario();
                String contrasenaActual = new String(currentPasswordField.getPassword());
                String nuevaContrasena = new String(newPasswordField.getPassword());

                // Verifica si la contraseña actual es correcta antes de actualizarla
                if (contrasenaActual.equals(Usuario.getContrasena())) {
                    // Si la contraseña actual es correcta, actualiza la contraseña en la base de datos
                    conexion.actualizarContrasena(nombreUsuario, nuevaContrasena);
                    Usuario.setContrasena(nuevaContrasena); // Actualizar la contraseña en Usuario
                    JOptionPane.showMessageDialog(this, "Contraseña cambiada con éxito.");
                } else {
                    // Si la contraseña actual es incorrecta, muestra un mensaje de error
                    JOptionPane.showMessageDialog(this, "La contraseña actual es incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al cambiar la contraseña: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    conexion.desconectar(); // Cierra la conexión a la base de datos
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        changePasswordButton.setForeground(Color.WHITE); // Cambio de color del texto
        changePasswordButton.setBackground(new Color(0x769976)); // Cambio de color del fondo del botón
        settingsPanel2.add(changePasswordButton, gbc2);

        // Panel para el botón de "Cerrar Cuenta"
        JPanel deleteAccountPanel = new JPanel(new GridBagLayout());
        deleteAccountPanel.setBackground(Color.WHITE); // Establecer el color de fondo del panel como blanco
        settingsPanel.add(deleteAccountPanel, BorderLayout.SOUTH);

        GridBagConstraints gbc21 = new GridBagConstraints();
        gbc21.gridx = 0;
        gbc21.gridy = 0;
        gbc21.insets = new Insets(5, 5, 30, 5); // Espaciado entre componentes
        gbc21.anchor = GridBagConstraints.WEST; // Alineación izquierda

        // Título "Cerrar Cuenta"
        JLabel deleteAccountTitleLabel = new JLabel("Cerrar Cuenta");
        deleteAccountTitleLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Fuente, negrita, tamaño
        deleteAccountTitleLabel.setForeground(new Color(0x769976)); // Cambio de color del texto
        deleteAccountPanel.add(deleteAccountTitleLabel, gbc21);
        
        gbc21.gridx = 0;
        gbc21.gridy++;
        // Botón para borrar cuenta
        JButton deleteAccountButton = new JButton("Cerrar Cuenta");
        deleteAccountButton.addActionListener(e -> {
            // Debes mostrar una confirmación al usuario antes de cerrar la cuenta
            int choice = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea cerrar su cuenta?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                borrarUsuario(Usuario.getNombreUsuario());
                JOptionPane.showMessageDialog(this, "Cuenta cerrada con éxito.");
                ConexionMySQL conexion = ConexionMySQL.obtenerInstancia();
                // Desconectar de la base de datos
                try {
					conexion.desconectar();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

                // Abrir la ventana de inicio de sesión (login)
                Login login = new Login();
                login.setVisible(true);
                // Cerrar la ventana actual
                dispose();
            }
        });
        deleteAccountButton.setForeground(Color.WHITE); // Cambio de color del texto
        deleteAccountButton.setBackground(new Color(0x769976)); // Cambio de color del fondo del botón
        deleteAccountPanel.add(deleteAccountButton, gbc21);

    }
    
    public static void borrarUsuario(String nombreUsuario) {
        ConexionMySQL conexion = new ConexionMySQL("root", "test", "HoomieNomad");
        try {
            // Conectar a la base de datos
            conexion.conectar();

            // Verificar si el usuario existe antes de eliminarlo
            ResultSet resultado = conexion.ejecutarSelect("SELECT * FROM Usuario WHERE nombreUsuario = '" + nombreUsuario + "'");
            if (resultado.next()) {
                // El usuario existe, proceder a eliminarlo
                String consulta = "DELETE FROM Usuario WHERE nombreUsuario = '" + nombreUsuario + "'";
                int filasAfectadas = conexion.ejecutarInsertDeleteUpdate(consulta);

                // Comprobar si se borró la fila correctamente
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Usuario borrado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo borrar el usuario.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "El usuario no existe.");
            }

            // Desconectar de la base de datos
            conexion.desconectar();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }
}
