package Proyecto;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
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
	public Principal() {
		setTitle("Hoomie Nomad - Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 522, 737);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

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
        loginButton.setBounds(50, 150, 150, 30);
        contentPane.add(loginButton);

        // Botón para registrarse
        JButton signUpButton = new JButton("Registrarse");
        signUpButton.setBounds(210, 150, 150, 30);
        contentPane.add(signUpButton);

        // Botón de aceptar
        JButton acceptButton = new JButton("Aceptar");
        acceptButton.setBounds(370, 150, 150, 30);
        contentPane.add(acceptButton);
	}
	
	

}
