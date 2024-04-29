package Proyecto;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
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

import ConexionBaseDatos.ConexionMySQL;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Toolkit;

public class Registrarse extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registrarse frame = new Registrarse();
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
	public Registrarse() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Registrarse.class.getResource("/Imagenes/Logo_marco.png.png")));
    	ConexionMySQL conexion = new ConexionMySQL("root", "test", "HoomieNomad");

		setTitle("Hoomie Nomad - Sign up");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 584, 737);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.WHITE);

		setContentPane(contentPane);
        contentPane.setLayout(null); // Usamos un layout nulo para posicionar los componentes manualmente
        
        // Etiqueta para el nombre
        JLabel nameLabel = new JLabel("Nombre y apellidos:");
        nameLabel.setBounds(93, 94, 150, 30);
        contentPane.add(nameLabel);

        // Campo de texto para insertar el nombre
        JTextField nameField = new JTextField();
        nameField.setBounds(308, 95, 150, 30);
        contentPane.add(nameField);
        
     // Etiqueta para el nombre de usuario
        JLabel usernameLabel = new JLabel("Nombre de usuario:");
        usernameLabel.setBounds(93, 164, 150, 30);
        contentPane.add(usernameLabel);

        // Campo de texto para el nombre de usuario
        JTextField usernameField = new JTextField();
        usernameField.setBounds(308, 165, 150, 30);
        contentPane.add(usernameField);
        
     // Etiqueta para el email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(93, 232, 150, 30);
        contentPane.add(emailLabel);

        // Campo de texto para insertar el email
        JTextField emailField = new JTextField();
        emailField.setBounds(308, 233, 150, 30);
        contentPane.add(emailField);

        // Etiqueta para la dirección
        JLabel addressLabel = new JLabel("Dirección postal:");
        addressLabel.setBounds(93, 307, 150, 30);
        contentPane.add(addressLabel);

        // Campo de texto para insertar la dirección
        JTextField addressField = new JTextField();
        addressField.setBounds(308, 308, 150, 30);
        contentPane.add(addressField);

        // Etiqueta para el teléfono
        JLabel phoneLabel = new JLabel("Teléfono:");
        phoneLabel.setBounds(93, 384, 150, 30);
        contentPane.add(phoneLabel);

        // Campo de texto para insertar el teléfono
        JTextField phoneField = new JTextField();
        phoneField.setBounds(308, 385, 150, 30);
        contentPane.add(phoneField);

        // Etiqueta para la contraseña
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(93, 452, 150, 30);
        contentPane.add(passwordLabel);

        // Campo de texto para la contraseña
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(308, 453, 150, 30);
        contentPane.add(passwordField);
        
        JLabel repeatPasswordLabel = new JLabel("Repita la contraseña:");
        repeatPasswordLabel.setBounds(93, 515, 200, 30);
        contentPane.add(repeatPasswordLabel);

        JPasswordField repeatPasswordField = new JPasswordField();
        repeatPasswordField.setBounds(308, 516, 150, 30);
        contentPane.add(repeatPasswordField);
        
        //Introducir imagen para esquina superior izquierda//
        
     // Cargar la imagen desde la carpeta del proyecto
        String rutaImagen = "/Imagenes/Logo_pequeno_suave.png"; // Ruta relativa de la imagen
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(rutaImagen));

        // Etiqueta para mostrar la imagen
        JLabel imageLabel = new JLabel(imageIcon);
        // Establecer la posición de la imagen en la esquina superior izquierda
        imageLabel.setBounds(142, 204, 266, 236);
        contentPane.add(imageLabel);

        
        // Botón para registrarse
        JButton signUpButton = new JButton("Registrarse");
        signUpButton.setBounds(308, 594, 150, 30);
        contentPane.add(signUpButton);
        
        
        

        // ActionListener para el botón "Registrarse"
       /* signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario = usernameField.getText();
                String contraseña = new String(passwordField.getPassword());

                // Verificar que los campos no estén vacíos
                if (usuario.isEmpty() || contraseña.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return;
                }

                try {
                    // Conectar a la base de datos
                    conexion.conectar();

                    // Verificar si el usuario ya existe en la base de datos
                    ResultSet resultado = conexion.ejecutarSelect("SELECT * FROM Usuario WHERE nombreUsuario='" + usuario + "'");
                    if (resultado.next()) {
                        JOptionPane.showMessageDialog(null, "¡El nombre de usuario ya está en uso!");
                    } else {
                        // Insertar el nuevo usuario en la base de datos
                        String consulta = "INSERT INTO Usuario (nombreUsuario, contrasena) VALUES ('" + usuario + "', '" + contraseña + "')";
                        int filasAfectadas = conexion.ejecutarInsertDeleteUpdate(consulta);

                        if (filasAfectadas > 0) {
                            JOptionPane.showMessageDialog(null, "¡Registro exitoso!");
                            // Cerrar la ventana de registro después del registro exitoso
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al registrar el usuario.");
                        }
                    }

                    // Desconectar de la base de datos
                    conexion.desconectar();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
                }
            }
        }); */
        
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtener los valores de todos los campos
                String nombreUsuario = usernameField.getText();
                String contraseña = new String(passwordField.getPassword());
                String repetirContraseña = new String(repeatPasswordField.getPassword());
                String nombre = nameField.getText();
                String email = emailField.getText();
                String direccion = addressField.getText();
                String telefono = phoneField.getText();

                // Verificar que todos los campos estén completos
                if (nombreUsuario.isEmpty() || contraseña.isEmpty() || nombre.isEmpty() || email.isEmpty() || direccion.isEmpty() || telefono.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return;
                }
                if (!contraseña.equals(repetirContraseña)) {
                    JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden. Por favor, inténtelo de nuevo.");
                    return;
                }
                try {
                    // Conectar a la base de datos
                    conexion.conectar();

                    // Verificar si el usuario ya existe en la base de datos
                    ResultSet resultado = conexion.ejecutarSelect("SELECT * FROM Usuario WHERE nombreUsuario='" + nombreUsuario + "'");
                    if (resultado.next()) {
                        JOptionPane.showMessageDialog(null, "¡El nombre de usuario ya está en uso!");
                    } else {
                        // Insertar el nuevo usuario en la base de datos
                        String consulta = "INSERT INTO Usuario (nombre, nombreUsuario, email, direccion, telefono, contrasena) VALUES (?, ?, ?, ?, ?, ?)";
                        PreparedStatement statement = conexion.prepareStatement(consulta);
                        statement.setString(1, nombre);
                        statement.setString(2, nombreUsuario);
                        statement.setString(3, email);
                        statement.setString(4, direccion);
                        statement.setString(5, telefono);
                        statement.setString(6, contraseña);
                        int filasAfectadas = statement.executeUpdate();

                        if (filasAfectadas > 0) {
                            JOptionPane.showMessageDialog(null, "¡Registro exitoso!");
                            // Cerrar la ventana de registro después del registro exitoso
                            dispose();
                            
                            // Abrir la ventana de "Completar Perfil"
                            Login perfilFrame = new Login();
                            perfilFrame.setVisible(true);
                            
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al registrar el usuario.");
                        }
                    }

                    // Desconectar de la base de datos
                    conexion.desconectar();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
                }
            }
            
            });
        
        
     // Botón para volver atrás
        JButton backButton = new JButton("Volver atrás");
        backButton.setBounds(71, 594, 150, 30);
        contentPane.add(backButton);
        
        JTextArea txtrHoomieNomad = new JTextArea();
        txtrHoomieNomad.setForeground(new Color(0, 0, 0));
        txtrHoomieNomad.setFont(new Font("MS Gothic", Font.PLAIN, 26));
        txtrHoomieNomad.setText("HOOMIE NOMAD");
        txtrHoomieNomad.setBounds(187, 28, 200, 30);
        contentPane.add(txtrHoomieNomad);

        // ActionListener para el botón "Volver atrás"
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Cerrar la ventana actual y abrir la ventana de inicio de sesión
                dispose();
                Login loginFrame = new Login();
                loginFrame.setVisible(true);
            }
        });
        
        
	}
}
