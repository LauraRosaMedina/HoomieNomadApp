package Proyecto;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MisReservas extends JFrame {

	
	private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public MisReservas() {
        setTitle("Mis reservas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 515, 702);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

     // Panel para el botón de "Atrás"
        JPanel topPanel = new JPanel(new BorderLayout());
        contentPane.add(topPanel, BorderLayout.NORTH);

        // Botón de "Atrás"
        JButton backButton = new JButton("← Atrás");
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
        topPanel.add(backButton, BorderLayout.WEST);

        Object gbc;
		
    }
    
    

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GestionarPerfil frame = new GestionarPerfil();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
