package Proyecto;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class FeedPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Usuario usuario; // Nuevo campo para almacenar la instancia de Usuario

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                    // Crear una instancia de Usuario
                    Usuario usuario = new Usuario("nombreUsuario", "contraseña");

                    // Crear una instancia de FeedPrincipal y pasar el usuario
                    FeedPrincipal frame = new FeedPrincipal(usuario);
                    frame.setVisible(true);
            }
        });
    }

    public FeedPrincipal(Usuario usuario) {
    	this.usuario = usuario;
    	setTitle("Feed Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 515, 702);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Panel para el botón de "Añadir Propiedades"
        JPanel topPanel = new JPanel(new BorderLayout());
        contentPane.add(topPanel, BorderLayout.NORTH);

        // Botón de "Añadir Propiedades"
        JButton addPropertiesButton = new JButton("Añadir Propiedades");
        addPropertiesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Abrir la ventana de añadir propiedades
                AnadirPropiedades anadirPropiedades = new AnadirPropiedades();
                anadirPropiedades.setVisible(true);
            }
        });
        topPanel.add(addPropertiesButton, BorderLayout.SOUTH);
    }


	// Setter para establecer la instancia de Usuario
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    // Getter para obtener la instancia de Usuario
    public Usuario getUsuario() {
        return usuario;
    }
}
