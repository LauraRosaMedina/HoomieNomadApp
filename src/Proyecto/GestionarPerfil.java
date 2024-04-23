package Proyecto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GestionarPerfil extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private String nombreUsuario;
    private String contrasena;

    public GestionarPerfil() {
        setTitle("Gestionar Perfil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 515, 702);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

     // Panel para el botón de "Atrás" y "Añadir Propiedades"
        JPanel topPanel = new JPanel(new BorderLayout());
        contentPane.add(topPanel, BorderLayout.NORTH);

        // Botón de "Atrás"
        JButton backButton = new JButton("← Atrás");
        backButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	// Crear una instancia de la clase FeedPrincipal
                    FeedPrincipal feedPrincipal = new FeedPrincipal("idUsuario");
                    // Hacer visible la ventana del feed principal
                    feedPrincipal.setVisible(true);
                    // Cerrar la ventana actual
                    dispose();
                }
        });
        topPanel.add(backButton, BorderLayout.WEST);

        Object gbc;
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

        // Panel para mostrar las propiedades
        JPanel propertiesPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        contentPane.add(propertiesPanel, BorderLayout.CENTER);

        // Ejemplo de propiedades (se pueden cargar desde una base de datos)
        String[] propertyImages = {"C:\\LauraRM\\Documentos\\Grado superior DAM\\Programación (P)\\HoomieNomadApp\\src\\Imagenes\\casaCostaBrava.JPG", "C:\\LauraRM\\Documentos\\Grado superior DAM\\Programación (P)\\HoomieNomadApp\\src\\Imagenes\\pisoMarbella.jpg"}; // Rutas de las imágenes
        for (String imagePath : propertyImages) {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            JLabel propertyLabel = new JLabel(scaledIcon);
            propertyLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Mostrar ventana emergente con los atributos de la propiedad
                    mostrarAtributosPropiedad1();
                }
            });
            propertiesPanel.add(propertyLabel);
        }
        }
        
     // Método para mostrar la ventana emergente con los atributos de la propiedad
    	private void mostrarAtributosPropiedad1() {
    	    // Crea y configura la ventana emergente
    	    JFrame ventanaEmergente = new JFrame();
    	    ventanaEmergente.setTitle("Características de la Propiedad");
    	    ventanaEmergente.setSize(300, 200);
    	    ventanaEmergente.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
    	    ventanaEmergente.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    	    // Agrega un panel para mostrar los atributos
    	    JPanel panelAtributos = new JPanel(new GridLayout(0, 1));
    	    // Agrega etiquetas con los atributos de ejemplo (reemplaza con tus atributos)
    	    panelAtributos.add(new JLabel("Tipo de Casa: Casa"));
    	    panelAtributos.add(new JLabel("Número de Baños: 2"));
    	    panelAtributos.add(new JLabel("Número de Habitaciones: 3"));
    	    panelAtributos.add(new JLabel("Terraza/Patio: Terraza"));
    	    panelAtributos.add(new JLabel("Ubicación: Barrio X"));
    	    panelAtributos.add(new JLabel("Garaje: Sí"));
    	    panelAtributos.add(new JLabel("Piscina: No"));
    	    panelAtributos.add(new JLabel("Número de Ocupantes: 4"));

    	    ventanaEmergente.getContentPane().add(panelAtributos);

    	    // Hacer visible la ventana emergente
    	    ventanaEmergente.setVisible(true);
    }

    // Método para mostrar la ventana emergente con los atributos de la propiedad
    private void mostrarAtributosPropiedad() {
    	// Crear y configurar la ventana emergente
        JFrame ventanaEmergente = new JFrame();
        ventanaEmergente.setTitle("Características de la Propiedad");
        ventanaEmergente.setSize(400, 300);
        ventanaEmergente.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        ventanaEmergente.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Cambiar a HIDE_ON_CLOSE

        // Panel para mostrar los atributos
        JPanel atributosPanel = new JPanel(new GridLayout(0, 2, 10, 10));

        // Aquí puedes agregar los componentes para mostrar los atributos de la propiedad

        ventanaEmergente.add(atributosPanel);
        ventanaEmergente.setVisible(true);
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

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
}
