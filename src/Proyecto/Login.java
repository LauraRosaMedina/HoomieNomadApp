package Proyecto;
import ConexionBaseDatos.ConexionMySQL;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		setTitle("Hoomie Nomad - Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 584, 737);
		//para centrar las cositas//
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Etiqueta para el nombre de usuario
        JLabel usernameLabel = new JLabel("Nombre de usuario:");
        usernameLabel.setBounds(50, 50, 150, 30);
        contentPane.add(usernameLabel);
        
        //Campo de texto para introducir nombre de usuario//
        
        
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

      /*  // Botón de aceptar
        JButton acceptButton = new JButton("Aceptar");
        acceptButton.setBounds(370, 150, 150, 30);
        contentPane.add(acceptButton);*/
        
        //ActionListener: al pulsar el botón de Registrarse, me lleva a esa página//
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Aquí abrimos la ventana de registro
                Registrarse registro = new Registrarse();
                registro.setVisible(true);
            }
        });
        
     // ActionListener para el botón "Iniciar sesión"
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario = usernameField.getText();
                String contraseña = new String(passwordField.getPassword());
                ConexionMySQL conexion = new ConexionMySQL("root", "test", "HoomieNomad");
                
                
                try {
                    // Conectarse a la base de datos
                    conexion.conectar();

                    // Consultar si el usuario existe en la base de datos
                    ResultSet resultado = conexion.ejecutarSelect("SELECT * FROM Usuario WHERE nombreUsuario='" + usuario + "' AND contrasena='" + contraseña + "'");

                    if (resultado.next()) {
                        // El usuario existe en la base de datos
                        JOptionPane.showMessageDialog(null, "¡Inicio de sesión exitoso!");
                    } else {
                        // El usuario no existe en la base de datos
                        JOptionPane.showMessageDialog(null, "¡Nombre de usuario o contraseña incorrectos!");
                    }

                    // Desconectar de la base de datos
                    conexion.desconectar();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos: " + ex.getMessage());
                }
            }
        });
        
        //Borrar usuario//
        
        
	}
	
	//creamos este método fuera del constructor Principal//
	/*public static void borrarUsuario(String nombreUsuario) {
    	ConexionMySQL conexion = new ConexionMySQL("root", "test", "HoomieNomad");
        try {
            // Conectar a la base de datos
            conexion.conectar();

            // Crear la sentencia SQL DELETE
            String consulta = "DELETE FROM Usuario WHERE nombreUsuario = '" + nombreUsuario + "'";

            // Ejecutar la sentencia DELETE
            int filasAfectadas = conexion.ejecutarInsertDeleteUpdate(consulta);
            
        
            // Comprobar si se borró la fila correctamente
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Usuario borrado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo borrar el usuario.");
                
            }
            
            

            // Desconectar de la base de datos
            conexion.desconectar();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    
    }*/
	
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
	        } /*else {
	            JOptionPane.showMessageDialog(null, "El usuario no existe.");
	        }*/

	        // Desconectar de la base de datos
	        conexion.desconectar();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
	    }
	}

	

}
