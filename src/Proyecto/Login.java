package Proyecto;

import ConexionBaseDatos.ConexionMySQL;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/Imagenes/Logo_marco.png.png")));
		setTitle("Hoomie Nomad - Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 584, 737);
		//para centrar las cositas//
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.WHITE);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		// Etiqueta para el nombre de usuario
        JLabel usernameLabel = new JLabel("Nombre de usuario:");
        usernameLabel.setBounds(50, 50, 150, 30);
        contentPane.add(usernameLabel);        
        
        // Campo de texto para el nombre de usuario
        JTextField usernameField = new JTextField();
        usernameField.setBounds(200, 50, 250, 30);
        contentPane.add(usernameField);

        // Etiqueta para la contraseña
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(50, 100, 150, 30);
        contentPane.add(passwordLabel);

        // Campo de texto para la contraseña
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(200, 100, 250, 30);
        contentPane.add(passwordField);

        // Botón para iniciar sesión
        JButton loginButton = new JButton("Iniciar sesión");
        loginButton.setBounds(100, 150, 150, 30);
        contentPane.add(loginButton);

        // Botón para registrarse
        JButton signUpButton = new JButton("Registrarse");
        signUpButton.setBounds(300, 150, 150, 30);
        contentPane.add(signUpButton);
        
        JLabel forgotPasswordLabel = new JLabel("¿Olvidaste tu contraseña?");
        forgotPasswordLabel.setBounds(200, 206, 200, 30);
        contentPane.add(forgotPasswordLabel);
        
      //Vamos a poner el logo de fondo//
      		String rutaImagen = "/Imagenes/Logo_login.png";
      		JLabel backgroundLabel = new JLabel ();
      		backgroundLabel.setBounds(123, 280, 337, 278);
      		contentPane.add(backgroundLabel);
      		
      		ImageIcon backgroundImage = new ImageIcon(getClass().getResource(rutaImagen));
      		backgroundLabel.setIcon(backgroundImage);;
      		

        // ActionListener para el enlace "He olvidado mi contraseña"
        forgotPasswordLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Aquí abrirías un cuadro de diálogo para introducir el correo electrónico
                // y luego un botón de aceptar.
                String email = JOptionPane.showInputDialog(null, "Introduce tu correo electrónico:");
                if (email != null) {
                    JOptionPane.showMessageDialog(null, "Se ha enviado un correo de recuperación a " + email);
                }
            }
        });
        
        //ActionListener: al pulsar el botón de Registrarse, me lleva a esa página//
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Aquí abrimos la ventana de registro
                Registrarse registro = new Registrarse();
                registro.setVisible(true);
                dispose();
            }
        });
        
     // ActionListener para el botón "Iniciar sesión"
        loginButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {               

                try {
                    // Crear una instancia de ConexionMySQL y conectar a la base de datos
                    ConexionMySQL conexion = new ConexionMySQL("root", "test", "HoomieNomad");
                    conexion.conectar();

                    String query = "SELECT * FROM Usuario WHERE nombreUsuario = '" + usernameField.getText() + "' AND contrasena = '" + new String(passwordField.getPassword()) + "'";
                    ResultSet resultSet = conexion.ejecutarSelect(query);
                    
                    if (resultSet.next()) {
                        // Usuario autenticado correctamente
                       Usuario.setIdUsuario(resultSet.getInt("id_usuario"));
                       Usuario.setNombreUsuario(resultSet.getString("nombreUsuario"));
                       Usuario.setNombre(resultSet.getString("nombre"));
                       Usuario.setContrasena(resultSet.getString("contrasena"));
                        FeedPrincipal feedPrincipal = new FeedPrincipal();
                        feedPrincipal.setVisible(true);
                        dispose();
                        
                    } else {
                        // Usuario no encontrado en la base de datos
                        JOptionPane.showMessageDialog(null, "Nombre de usuario o contraseña incorrectos.");
                    }
                    
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos: " + ex.getMessage());
                }
            }
        });                    
	}
}
