package Proyecto;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import ConexionBaseDatos.ConexionMySQL;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AnadirPropiedades extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private boolean disponible = true; // Por defecto, siempre se inicia como disponible
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                    // Crear una instancia de ConexionMySQL y conectar a la base de datos
                    ConexionMySQL conexion = new ConexionMySQL("root", "test", "HoomieNomad");
                    try {
						conexion.conectar();
					} catch (SQLException e) {
						e.printStackTrace();
	                    JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos: " + e.getMessage());
					}
                    AnadirPropiedades frame = new AnadirPropiedades(); // Pasa la sesión como argumento al constructor
                    frame.setVisible(true);
            }
        });
    }

    // Constructor
    public AnadirPropiedades() {
    	setResizable(false);
    	contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
    	 // Cargar la imagen de fondo desde el archivo
        ImageIcon icono = new ImageIcon(getClass().getResource("/Imagenes/Logo_pequeño.png"));
        
        // Crear un JLabel para mostrar la imagen
        JLabel imagenLabel = new JLabel(new ImageIcon(AnadirPropiedades.class.getResource("/Imagenes/Logo_pequeño.png")));
        imagenLabel.setHorizontalAlignment(SwingConstants.LEFT);
        imagenLabel.setVerticalAlignment(SwingConstants.TOP);
        
        // Agregar la imagen en la parte superior izquierda del contentPane
        contentPane.add(imagenLabel, BorderLayout.NORTH);
    	setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirPropiedades.class.getResource("/Imagenes/Logo_marco.png.png")));
    	// Cambiar el color de los botones a 0x769976 y el texto de los botones a blanco
        UIManager.put("Button.background", new Color(0x769976));
        UIManager.put("Button.foreground", Color.WHITE);
        
     // Cambiar el color del texto de las etiquetas de los campos de texto a 0x769976
        UIManager.put("Label.foreground", new Color(0x769976));
    	
        setTitle("Añadir propiedades");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 515, 702);
        setLocationRelativeTo(null);

        
     
        
        // Continuar con el resto del código del constructor...
    

        JPanel propertyPanel = new JPanel();
        propertyPanel.setBackground(Color.WHITE);
        contentPane.add(propertyPanel, BorderLayout.CENTER);
        propertyPanel.setLayout(new GridBagLayout());
        
     

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes
        gbc.anchor = GridBagConstraints.WEST; // Alineación izquierda
        
     
        
                        
                             // Etiqueta y menú desplegable para el tipo de casa
                             // Crear un nuevo objeto GridBagConstraints para la etiqueta del tipo de casa
                                GridBagConstraints gbcTipoDeCasaLabel = new GridBagConstraints();
                                gbcTipoDeCasaLabel.gridx = 0;
                                gbcTipoDeCasaLabel.gridy = 0;
                                gbcTipoDeCasaLabel.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes
                                gbcTipoDeCasaLabel.anchor = GridBagConstraints.WEST; // Alineación izquierda
                                
                                        // Etiqueta para el tipo de casa
                                        JLabel tipoDeCasaLabel = new JLabel("Tipo de casa:");
                                        tipoDeCasaLabel.setHorizontalAlignment(SwingConstants.LEFT);
                                        tipoDeCasaLabel.setForeground(new Color(0x769976)); // Cambiar el color del texto
                                        tipoDeCasaLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Cambiar el tamaño de la fuente
                                        propertyPanel.add(tipoDeCasaLabel, gbcTipoDeCasaLabel);
                        
                                // Crear un nuevo objeto GridBagConstraints para el menú desplegable del tipo de casa
                                GridBagConstraints gbcTipoDeCasaComboBox = new GridBagConstraints();
                                gbcTipoDeCasaComboBox.insets = new Insets(0, 0, 5, 0);
                                gbcTipoDeCasaComboBox.gridx = 1;
                                gbcTipoDeCasaComboBox.gridy = 0;
                                gbcTipoDeCasaComboBox.weightx = 1.0; // Estirar el componente horizontalmente
                                
                                        JComboBox<String> tipoDeCasaComboBox = new JComboBox<>(new String[]{"Casa", "Piso", "Chalet", "Ático"});
                                        tipoDeCasaComboBox.setPreferredSize(new Dimension(35, tipoDeCasaComboBox.getPreferredSize().height));
                                        tipoDeCasaComboBox.setPreferredSize(new Dimension(200, tipoDeCasaComboBox.getPreferredSize().width));
                                        propertyPanel.add(tipoDeCasaComboBox, gbcTipoDeCasaComboBox);
                                
                                
                                        // Etiqueta y campo de texto para el número de baños
                                     // Crear un nuevo objeto GridBagConstraints para la etiqueta del número de baños
                                        GridBagConstraints gbcBanosLabel = new GridBagConstraints();
                                        gbcBanosLabel.gridx = 0;
                                        gbcBanosLabel.gridy = 2;
                                        gbcBanosLabel.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes
                                        gbcBanosLabel.anchor = GridBagConstraints.WEST; // Alineación izquierda
                                        
                                                // Etiqueta para el número de baños
                                                JLabel banosLabel = new JLabel("Número de baños:");
                                                banosLabel.setHorizontalAlignment(SwingConstants.LEFT);
                                                banosLabel.setForeground(new Color(0x769976)); // Cambiar el color del texto
                                                banosLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Cambiar el tamaño de la fuente
                                                propertyPanel.add(banosLabel, gbcBanosLabel);
                        
                                // Crear un nuevo objeto GridBagConstraints para el campo de texto del número de baños
                                GridBagConstraints gbcBanosField = new GridBagConstraints();
                                gbcBanosField.insets = new Insets(0, 0, 5, 0);
                                gbcBanosField.gridx = 1;
                                gbcBanosField.gridy = 2;
                                gbcBanosField.weightx = 1.0; // Estirar el componente horizontalmente
                                
                                        JTextField banosField = new JTextField();
                                        banosField.setDocument(new NumberOnlyDocument("Número de baños"));
                                        banosField.setPreferredSize(new Dimension(35, banosField.getPreferredSize().height));
                                        banosField.setPreferredSize(new Dimension(200, banosField.getPreferredSize().width));
                                        propertyPanel.add(banosField, gbcBanosField);
                
                
                        // Etiqueta y campo de texto para el número de habitaciones
                     // Crear un nuevo objeto GridBagConstraints para la etiqueta del número de habitaciones
                        GridBagConstraints gbcHabitacionesLabel = new GridBagConstraints();
                        gbcHabitacionesLabel.gridx = 0;
                        gbcHabitacionesLabel.gridy = 4;
                        gbcHabitacionesLabel.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes
                        gbcHabitacionesLabel.anchor = GridBagConstraints.WEST; // Alineación izquierda
                        
                                // Etiqueta para el número de habitaciones
                                JLabel habitacionesLabel = new JLabel("Número de habitaciones:");
                                habitacionesLabel.setHorizontalAlignment(SwingConstants.LEFT);
                                habitacionesLabel.setForeground(new Color(0x769976)); // Cambiar el color del texto
                                habitacionesLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Cambiar el tamaño de la fuente
                                propertyPanel.add(habitacionesLabel, gbcHabitacionesLabel);
                
                        // Crear un nuevo objeto GridBagConstraints para el campo de texto del número de habitaciones
                        GridBagConstraints gbcHabitacionesField = new GridBagConstraints();
                        gbcHabitacionesField.insets = new Insets(0, 0, 5, 0);
                        gbcHabitacionesField.gridx = 1;
                        gbcHabitacionesField.gridy = 4;
                        gbcHabitacionesField.weightx = 1.0; // Estirar el componente horizontalmente
                        
                                JTextField habitacionesField = new JTextField();
                                habitacionesField.setDocument(new NumberOnlyDocument("Número de habitaciones"));
                                habitacionesField.setPreferredSize(new Dimension(35, habitacionesField.getPreferredSize().height));
                                habitacionesField.setPreferredSize(new Dimension(200, habitacionesField.getPreferredSize().width));
                                propertyPanel.add(habitacionesField, gbcHabitacionesField);
                
                
                        // Etiqueta y menú desplegable para terraza/patio
                     // Crear un nuevo objeto GridBagConstraints para la etiqueta de terraza/patio
                        GridBagConstraints gbcTerrazaLabel = new GridBagConstraints();
                        gbcTerrazaLabel.gridx = 0;
                        gbcTerrazaLabel.gridy = 6;
                        gbcTerrazaLabel.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes
                        gbcTerrazaLabel.anchor = GridBagConstraints.WEST; // Alineación izquierda
                        
                                // Etiqueta para terraza/patio
                                JLabel terrazaLabel = new JLabel("Terraza/Patio:");
                                terrazaLabel.setHorizontalAlignment(SwingConstants.LEFT);
                                terrazaLabel.setForeground(new Color(0x769976)); // Cambiar el color del texto
                                terrazaLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Cambiar el tamaño de la fuente
                                propertyPanel.add(terrazaLabel, gbcTerrazaLabel);
        
                // Crear un nuevo objeto GridBagConstraints para el menú desplegable de terraza/patio
                GridBagConstraints gbcTerrazaComboBox = new GridBagConstraints();
                gbcTerrazaComboBox.insets = new Insets(0, 0, 5, 0);
                gbcTerrazaComboBox.gridx = 1;
                gbcTerrazaComboBox.gridy = 6;
                gbcTerrazaComboBox.weightx = 1.0; // Estirar el componente horizontalmente
                
                        JComboBox<String> terrazaComboBox = new JComboBox<>(new String[]{"Terraza", "Patio", "Ambas", "Ninguna"});
                        terrazaComboBox.setPreferredSize(new Dimension(35, terrazaComboBox.getPreferredSize().height));
                        terrazaComboBox.setPreferredSize(new Dimension(200, terrazaComboBox.getPreferredSize().width));
                        propertyPanel.add(terrazaComboBox, gbcTerrazaComboBox);
        
        
             // Etiqueta y campo de texto para la ubicación
             // Crear un nuevo objeto GridBagConstraints para la etiqueta de ubicación
                GridBagConstraints gbcUbicacionLabel = new GridBagConstraints();
                gbcUbicacionLabel.gridx = 0;
                gbcUbicacionLabel.gridy = 8;
                gbcUbicacionLabel.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes
                gbcUbicacionLabel.anchor = GridBagConstraints.WEST; // Alineación izquierda
                
                        // Etiqueta para la ubicación
                        JLabel ubicacionLabel = new JLabel("Ubicación:");
                        ubicacionLabel.setHorizontalAlignment(SwingConstants.LEFT);
                        ubicacionLabel.setForeground(new Color(0x769976)); // Cambiar el color del texto
                        ubicacionLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Cambiar el tamaño de la fuente
                        propertyPanel.add(ubicacionLabel, gbcUbicacionLabel);
        
                // Crear un nuevo objeto GridBagConstraints para el campo de texto de ubicación
                GridBagConstraints gbcUbicacionField = new GridBagConstraints();
                gbcUbicacionField.insets = new Insets(0, 0, 5, 0);
                gbcUbicacionField.gridx = 1;
                gbcUbicacionField.gridy = 8;
                gbcUbicacionField.weightx = 1.0; // Estirar el componente horizontalmente
                
                        JTextField ubicacionField = new JTextField("Ciudad");
                        ubicacionField.setPreferredSize(new Dimension(35, ubicacionField.getPreferredSize().height));
                        ubicacionField.setPreferredSize(new Dimension(200, ubicacionField.getPreferredSize().width));
                        ubicacionField.setForeground(Color.GRAY); // Color gris para el texto por defecto
                        ubicacionField.addFocusListener(new FocusAdapter() {
                            @Override
                            public void focusGained(FocusEvent e) {
                                if (ubicacionField.getText().equals("Ciudad")) {
                                    ubicacionField.setText("");
                                    ubicacionField.setForeground(Color.BLACK); // Cambiar el color del texto a negro cuando se empiece a escribir
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {
                                if (ubicacionField.getText().isEmpty()) {
                                    ubicacionField.setText("Ciudad");
                                    ubicacionField.setForeground(Color.GRAY); // Restaurar el color gris del texto si no se ha escrito nada
                                }
                            }
                        });
                        propertyPanel.add(ubicacionField, gbcUbicacionField);
        
        
        
                // Etiqueta y menú desplegable para garaje
             // Crear un nuevo objeto GridBagConstraints para la etiqueta de garaje
                GridBagConstraints gbcGarajeLabel = new GridBagConstraints();
                gbcGarajeLabel.gridx = 0;
                gbcGarajeLabel.gridy = 10;
                gbcGarajeLabel.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes
                gbcGarajeLabel.anchor = GridBagConstraints.WEST; // Alineación izquierda
                
                        // Etiqueta para el garaje
                        JLabel garajeLabel = new JLabel("Garaje:");
                        garajeLabel.setHorizontalAlignment(SwingConstants.LEFT);
                        garajeLabel.setForeground(new Color(0x769976)); // Cambiar el color del texto
                        garajeLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Cambiar el tamaño de la fuente
                        propertyPanel.add(garajeLabel, gbcGarajeLabel);
        
                // Crear un nuevo objeto GridBagConstraints para el menú desplegable de garaje
                GridBagConstraints gbcGarajeComboBox = new GridBagConstraints();
                gbcGarajeComboBox.insets = new Insets(0, 0, 5, 0);
                gbcGarajeComboBox.gridx = 1;
                gbcGarajeComboBox.gridy = 10;
                
                        JComboBox<String> garajeComboBox = new JComboBox<>(new String[]{"Sí", "No"});
                        garajeComboBox.setPreferredSize(new Dimension(35, garajeComboBox.getPreferredSize().height));
                        garajeComboBox.setPreferredSize(new Dimension(200, garajeComboBox.getPreferredSize().width));
                        propertyPanel.add(garajeComboBox, gbcGarajeComboBox);
        
        
                // Etiqueta y menú desplegable para piscina
             // Crear un nuevo objeto GridBagConstraints para la etiqueta de piscina
                GridBagConstraints gbcPiscinaLabel = new GridBagConstraints();
                gbcPiscinaLabel.gridx = 0;
                gbcPiscinaLabel.gridy = 12;
                gbcPiscinaLabel.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes
                gbcPiscinaLabel.anchor = GridBagConstraints.WEST; // Alineación izquierda
                
                        // Etiqueta para la piscina
                        JLabel piscinaLabel = new JLabel("Piscina:");
                        piscinaLabel.setHorizontalAlignment(SwingConstants.LEFT);
                        piscinaLabel.setForeground(new Color(0x769976)); // Cambiar el color del texto
                        piscinaLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Cambiar el tamaño de la fuente
                        propertyPanel.add(piscinaLabel, gbcPiscinaLabel);
        
                // Crear un nuevo objeto GridBagConstraints para el menú desplegable de piscina
                GridBagConstraints gbcPiscinaComboBox = new GridBagConstraints();
                gbcPiscinaComboBox.insets = new Insets(0, 0, 5, 0);
                gbcPiscinaComboBox.gridx = 1;
                gbcPiscinaComboBox.gridy = 12;
                
                        JComboBox<String> piscinaComboBox = new JComboBox<>(new String[]{"Sí", "No"});
                        piscinaComboBox.setPreferredSize(new Dimension(35, piscinaComboBox.getPreferredSize().height));
                        piscinaComboBox.setPreferredSize(new Dimension(200, piscinaComboBox.getPreferredSize().width));
                        propertyPanel.add(piscinaComboBox, gbcPiscinaComboBox);
        
        
                // Etiqueta y campo de texto para el número de ocupantes
             // Crear un nuevo objeto GridBagConstraints para la etiqueta de ocupantes
                GridBagConstraints gbcOcupantesLabel = new GridBagConstraints();
                gbcOcupantesLabel.gridx = 0;
                gbcOcupantesLabel.gridy = 14;
                gbcOcupantesLabel.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes
                gbcOcupantesLabel.anchor = GridBagConstraints.WEST; // Alineación izquierda
                
                        // Etiqueta para el número de ocupantes
                        JLabel ocupantesLabel = new JLabel("Número de ocupantes:");
                        ocupantesLabel.setHorizontalAlignment(SwingConstants.LEFT);
                        ocupantesLabel.setForeground(new Color(0x769976)); // Cambiar el color del texto
                        ocupantesLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Cambiar el tamaño de la fuente
                        propertyPanel.add(ocupantesLabel, gbcOcupantesLabel);
        
                // Crear un nuevo objeto GridBagConstraints para el campo de texto de ocupantes
                GridBagConstraints gbcOcupantesField = new GridBagConstraints();
                gbcOcupantesField.insets = new Insets(0, 0, 5, 0);
                gbcOcupantesField.gridx = 1;
                gbcOcupantesField.gridy = 14;
                
                        JTextField ocupantesField = new JTextField();
                        ocupantesField.setDocument(new NumberOnlyDocument("Número de ocupantes"));
                        ocupantesField.setPreferredSize(new Dimension(35, ocupantesField.getPreferredSize().height));
                        ocupantesField.setPreferredSize(new Dimension(200, ocupantesField.getPreferredSize().width));
                        propertyPanel.add(ocupantesField, gbcOcupantesField);
                        
                        
                  


     
     // Botón de guardar
        gbc.gridx = 0;
        gbc.gridy = 9;
        JButton addButton = new JButton("Añadir");
        addButton.setBackground(new Color(118, 153, 118));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Verificar si todos los campos están completos
                if (banosField.getText().isEmpty() ||
                    habitacionesField.getText().isEmpty() ||
                    ocupantesField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {                      
                    String tipoDeCasa = (String) tipoDeCasaComboBox.getSelectedItem();
                    String banos = banosField.getText();
                    String habitaciones = habitacionesField.getText();
                    String terraza = (String) terrazaComboBox.getSelectedItem();
                    String ubicacion = ubicacionField.getText();
                    String garaje = (String) garajeComboBox.getSelectedItem();
                    String piscina = (String) piscinaComboBox.getSelectedItem();
                    String ocupantes = ocupantesField.getText();
                    try {
                        // Obtener el id del usuario actualmente conectado
                       Usuario.getIdUsuario();

                        // Conectar a la base de datos
                        ConexionMySQL conexion = new ConexionMySQL("root", "test", "HoomieNomad");
                        conexion.conectar();

                        if (Usuario.getIdUsuario() != 0) {
                        	// Insertar la nueva propiedad en la base de datos
                            String consulta = "INSERT INTO Propiedades (id_usuario, tipo_de_casa, num_banos, num_habitaciones, terraza_patio, ubicacion, garaje, piscina, num_ocupantes, disponible) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                            PreparedStatement statement = conexion.prepareStatement(consulta);
                            statement.setInt(1, Usuario.getIdUsuario());
                            statement.setString(2, tipoDeCasa);
                            statement.setInt(3, Integer.parseInt(banos));
                            statement.setInt(4, Integer.parseInt(habitaciones));
                            statement.setString(5, terraza);
                            statement.setString(6, ubicacion);
                            statement.setString(7, garaje);
                            statement.setString(8, piscina);
                            statement.setInt(9, Integer.parseInt(ocupantes));
                            statement.setBoolean(10, disponible); // Establece la disponibilidad como true por defecto
                            int filasAfectadas = statement.executeUpdate();

                            if (filasAfectadas > 0) {
                                JOptionPane.showMessageDialog(null, "Propiedad agregada exitosamente.");
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "Error al agregar la propiedad.");
                            }
                            
                        } else {
                            // Manejo del caso en el que la sesión o el usuario sean nulos
                            JOptionPane.showMessageDialog(null, "Error al obtener la sesión o el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
                    }
                
                }
                
                // Llamar al método actualizarPropiedades de la ventana de GestionarPerfil
                GestionarPerfil gestionarPerfil = new GestionarPerfil();
                gestionarPerfil.actualizarPropiedades(propertyPanel);                
                // Cerrar la ventana de AnadirPropiedades
                dispose();
            }
        });
        
        contentPane.add(addButton, BorderLayout.SOUTH);
    }

	// Clase interna para permitir solo la entrada de números en JTextField
    class NumberOnlyDocument extends PlainDocument {
        private String fieldName;

        public NumberOnlyDocument(String fieldName) {
            this.fieldName = fieldName;
        }

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) {
                return;
            }
            try {
                Integer.parseInt(str);
            } catch (NumberFormatException e) {
                // Mostrar un mensaje emergente si se intenta ingresar una letra
                JOptionPane.showMessageDialog(null, "El campo '" + fieldName + "' solo puede contener números.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            super.insertString(offs, str, a);
        }
    }
}