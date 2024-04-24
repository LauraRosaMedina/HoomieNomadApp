package Proyecto;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ConexionBaseDatos.ConexionMySQL;

public class Ajustes extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPasswordField passwordField;
    private JTextField usernameField;

    /**
     * Launch the application.
     */
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

    /**
     * Create the frame.
     */
    public Ajustes() {
        setTitle("Ajustes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 515, 702);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Panel para el botón de "Atrás" y los ajustes
        JPanel topPanel = new JPanel(new BorderLayout());
        contentPane.add(topPanel, BorderLayout.NORTH);

        // Botón de "Atrás"
        JButton backButton = new JButton("← Atrás");
        backButton.addActionListener(e -> {

      
				    FeedPrincipal feedPrincipal = new FeedPrincipal();
				    feedPrincipal.setVisible(true);
				    dispose();

        });
        topPanel.add(backButton, BorderLayout.WEST);

        // Panel para los ajustes
        JPanel settingsPanel = new JPanel(new BorderLayout());
        contentPane.add(settingsPanel, BorderLayout.CENTER);

        JPanel settingsPanel1 = new JPanel(new GridBagLayout());
        settingsPanel.add(settingsPanel1, BorderLayout.NORTH);

        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.insets = new Insets(20, 5, 5, 5); // Espaciado entre componentes
        gbc1.anchor = GridBagConstraints.WEST; // Alineación izquierda

        // Título "Actualizar Contraseña"
        JLabel changePasswordTitleLabel = new JLabel("Actualizar Contraseña");
        changePasswordTitleLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Fuente, negrita, tamaño
        settingsPanel1.add(changePasswordTitleLabel, gbc1);

        gbc1.gridx = 0;
        gbc1.gridy++;

        // Campo de texto para contraseña actual
        JLabel currentPasswordLabel = new JLabel("Contraseña actual:");
        settingsPanel1.add(currentPasswordLabel, gbc1);

        gbc1.gridx++;
        JPasswordField currentPasswordField = new JPasswordField(20);
        settingsPanel1.add(currentPasswordField, gbc1);

        gbc1.gridx = 0;
        gbc1.gridy++;

        // Campo de texto para nueva contraseña
        JLabel newPasswordLabel = new JLabel("Nueva contraseña:");
        settingsPanel1.add(newPasswordLabel, gbc1);

        gbc1.gridx++;
        JPasswordField newPasswordField = new JPasswordField(20);
        settingsPanel1.add(newPasswordField, gbc1);

        gbc1.gridx = 0;
        gbc1.gridy++;

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
        settingsPanel1.add(changePasswordButton, gbc1);

        // Panel para el botón de "Cerrar Cuenta"
        JPanel deleteAccountPanel = new JPanel(new GridBagLayout());
        settingsPanel.add(deleteAccountPanel, BorderLayout.SOUTH);

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.insets = new Insets(5, 5, 30, 5); // Espaciado entre componentes
        gbc2.anchor = GridBagConstraints.WEST; // Alineación izquierda

        // Título "Cerrar Cuenta"
        JLabel deleteAccountTitleLabel = new JLabel("Cerrar Cuenta");
        deleteAccountTitleLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Fuente, negrita, tamaño
        deleteAccountPanel.add(deleteAccountTitleLabel, gbc2);

        gbc2.gridx = 0;
        gbc2.gridy++;
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

                // Abrir la ventana de inicio de sesión (login)
                Login login = new Login();
                login.setVisible(true);
                // Cerrar la ventana actual
                dispose();
            }
        });
        deleteAccountPanel.add(deleteAccountButton, gbc2);
    }
    
    public static void borrarUsuario(String nombreUsuario) {
	    ConexionMySQL conexion = new ConexionMySQL("root", "test", "HoomieNomad");
	    try {
	        // Conectar a la base de datos
	        conexion.conectar();

	        // Verificar si el usuario existe antes de eliminarlo
	        ResultSet resultado = conexion.ejecutarSelect("SELECT * FROM Usuario WHERE nombreUsuario = '" + Usuario.getNombreUsuario() + "'");
	        if (resultado.next()) {
	            // El usuario existe, proceder a eliminarlo
	            String consulta = "DELETE FROM Usuario WHERE nombreUsuario = '" + Usuario.getNombreUsuario() + "'";
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
