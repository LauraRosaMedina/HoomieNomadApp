package Proyecto;

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

import ConexionBaseDatos.ConexionMySQL;

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
    	ConexionMySQL conexion = new ConexionMySQL("root", "test", "HoomieNomad");

		setTitle("Hoomie Nomad - Sign up");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 584, 737);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
        contentPane.setLayout(null); // Usamos un layout nulo para posicionar los componentes manualmente
        
        
     // Etiqueta para el nombre de usuario
        JLabel usernameLabel = new JLabel("Nombre de usuario:");
        usernameLabel.setBounds(50, 50, 150, 30);
        contentPane.add(usernameLabel);

        // Campo de texto para el nombre de usuario
        JTextField usernameField = new JTextField();
        usernameField.setBounds(200, 50, 150, 30);
        contentPane.add(usernameField);

        // Etiqueta para la contraseña
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(50, 100, 150, 30);
        contentPane.add(passwordLabel);

        // Campo de texto para la contraseña
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(200, 100, 150, 30);
        contentPane.add(passwordField);

        // Botón para registrarse
        JButton signUpButton = new JButton("Registrarse");
        signUpButton.setBounds(130, 150, 150, 30);
        contentPane.add(signUpButton);

        // ActionListener para el botón "Registrarse"
        signUpButton.addActionListener(new ActionListener() {
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
        });
	}

}
