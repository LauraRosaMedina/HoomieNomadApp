package Proyecto;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

public class Ajustes extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

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
        	// Crear una instancia de la clase FeedPrincipal
            FeedPrincipal feedPrincipal = new FeedPrincipal();
            // Hacer visible la ventana del feed principal
            feedPrincipal.setVisible(true);
            // Cerrar la ventana actual
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
            // Lógica para cambiar la contraseña
            // Debes verificar que la contraseña actual sea correcta y que la nueva contraseña cumpla con tus requisitos
            // Si el cambio es exitoso, debes actualizar la contraseña en tu base de datos o sistema de autenticación
            // Muestra un mensaje de éxito o error según corresponda
            JOptionPane.showMessageDialog(this, "Contraseña cambiada con éxito.");
            // Limpia los campos de contraseña
            currentPasswordField.setText("");
            newPasswordField.setText("");
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
            // Lógica para cerrar la cuenta
            // Debes mostrar una confirmación al usuario antes de cerrar la cuenta
            int choice = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea cerrar su cuenta?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                // Debes llamar al método para borrar el usuario en tu sistema de autenticación
                // Ejemplo: Login.borrarUsuario(nombreUsuario);
                JOptionPane.showMessageDialog(this, "Cuenta cerrada con éxito.");
                // Aquí puedes realizar acciones adicionales, como cerrar la sesión y redirigir al usuario a la pantalla de inicio de sesión
                // Cerrar la ventana actual
                dispose();
                // Abrir la ventana de inicio de sesión (login)
                Login login = new Login();
                login.setVisible(true);
            }
        });
        deleteAccountPanel.add(deleteAccountButton, gbc2);
    }
}
