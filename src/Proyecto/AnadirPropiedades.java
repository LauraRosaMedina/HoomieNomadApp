package Proyecto;

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
    	// Cambiar el color de los botones a 0x769976 y el texto de los botones a blanco
        UIManager.put("Button.background", new Color(0x769976));
        UIManager.put("Button.foreground", Color.WHITE);
        
     // Cambiar el color del texto de las etiquetas de los campos de texto a 0x769976
        UIManager.put("Label.foreground", new Color(0x769976));
    	
        setTitle("Añadir propiedades");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 515, 702);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel propertyPanel = new JPanel();
        contentPane.add(propertyPanel, BorderLayout.CENTER);
        propertyPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes
        gbc.anchor = GridBagConstraints.WEST; // Alineación izquierda

     // Etiqueta y menú desplegable para el tipo de casa
        JLabel tipoDeCasaLabel = new JLabel("Tipo de casa:");
        tipoDeCasaLabel.setHorizontalAlignment(SwingConstants.LEFT);
        tipoDeCasaLabel.setForeground(new Color(0x769976)); // Cambiar el color del texto
        tipoDeCasaLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Cambiar el tamaño de la fuente
        propertyPanel.add(tipoDeCasaLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0; // Estirar el componente horizontalmente
        JComboBox<String> tipoDeCasaComboBox = new JComboBox<>(new String[]{"Casa", "Piso", "Chalet", "Ático"});
        tipoDeCasaComboBox.setPreferredSize(new Dimension(35, tipoDeCasaComboBox.getPreferredSize().height));
        tipoDeCasaComboBox.setPreferredSize(new Dimension(200, tipoDeCasaComboBox.getPreferredSize().width));
        propertyPanel.add(tipoDeCasaComboBox, gbc);

        // Etiqueta y campo de texto para el número de baños
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel banosLabel = new JLabel("Número de baños:");
        banosLabel.setHorizontalAlignment(SwingConstants.LEFT);
        banosLabel.setForeground(new Color(0x769976)); // Cambiar el color del texto
        banosLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Cambiar el tamaño de la fuente
        propertyPanel.add(banosLabel, gbc);

        gbc.gridx = 1;
        JTextField banosField = new JTextField();
        banosField.setDocument(new NumberOnlyDocument("Número de baños"));
        banosField.setPreferredSize(new Dimension(35, banosField.getPreferredSize().height));
        banosField.setPreferredSize(new Dimension(200, banosField.getPreferredSize().width));
        propertyPanel.add(banosField, gbc);

        // Etiqueta y campo de texto para el número de habitaciones
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel habitacionesLabel = new JLabel("Número de habitaciones:");
        habitacionesLabel.setHorizontalAlignment(SwingConstants.LEFT);
        habitacionesLabel.setForeground(new Color(0x769976)); // Cambiar el color del texto
        habitacionesLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Cambiar el tamaño de la fuente
        propertyPanel.add(habitacionesLabel, gbc);

        gbc.gridx = 1;
        JTextField habitacionesField = new JTextField();
        habitacionesField.setDocument(new NumberOnlyDocument("Número de habitaciones"));
        habitacionesField.setPreferredSize(new Dimension(35, habitacionesField.getPreferredSize().height));
        habitacionesField.setPreferredSize(new Dimension(200, habitacionesField.getPreferredSize().width));
        propertyPanel.add(habitacionesField, gbc);

        // Etiqueta y menú desplegable para terraza/patio
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel terrazaLabel = new JLabel("Terraza/Patio:");
        terrazaLabel.setHorizontalAlignment(SwingConstants.LEFT);
        terrazaLabel.setForeground(new Color(0x769976)); // Cambiar el color del texto
        terrazaLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Cambiar el tamaño de la fuente
        propertyPanel.add(terrazaLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> terrazaComboBox = new JComboBox<>(new String[]{"Terraza", "Patio", "Ambas", "Ninguna"});
        terrazaComboBox.setPreferredSize(new Dimension(35, terrazaComboBox.getPreferredSize().height));
        terrazaComboBox.setPreferredSize(new Dimension(200, terrazaComboBox.getPreferredSize().width));
        propertyPanel.add(terrazaComboBox, gbc);

     // Etiqueta y campo de texto para la ubicación
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel ubicacionLabel = new JLabel("Ubicación:");
        ubicacionLabel.setHorizontalAlignment(SwingConstants.LEFT);
        ubicacionLabel.setForeground(new Color(0x769976)); // Cambiar el color del texto
        ubicacionLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Cambiar el tamaño de la fuente
        propertyPanel.add(ubicacionLabel, gbc);

        gbc.gridx = 1;
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
        propertyPanel.add(ubicacionField, gbc);


        // Etiqueta y menú desplegable para garaje
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel garajeLabel = new JLabel("Garaje:");
        garajeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        garajeLabel.setForeground(new Color(0x769976)); // Cambiar el color del texto
        garajeLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Cambiar el tamaño de la fuente
        propertyPanel.add(garajeLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> garajeComboBox = new JComboBox<>(new String[]{"Sí", "No"});
        garajeComboBox.setPreferredSize(new Dimension(35, garajeComboBox.getPreferredSize().height));
        garajeComboBox.setPreferredSize(new Dimension(200, garajeComboBox.getPreferredSize().width));
        propertyPanel.add(garajeComboBox, gbc);

        // Etiqueta y menú desplegable para piscina
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel piscinaLabel = new JLabel("Piscina:");
        piscinaLabel.setHorizontalAlignment(SwingConstants.LEFT);
        piscinaLabel.setForeground(new Color(0x769976)); // Cambiar el color del texto
        piscinaLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Cambiar el tamaño de la fuente
        propertyPanel.add(piscinaLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> piscinaComboBox = new JComboBox<>(new String[]{"Sí", "No"});
        piscinaComboBox.setPreferredSize(new Dimension(35, piscinaComboBox.getPreferredSize().height));
        piscinaComboBox.setPreferredSize(new Dimension(200, piscinaComboBox.getPreferredSize().width));
        propertyPanel.add(piscinaComboBox, gbc);

        // Etiqueta y campo de texto para el número de ocupantes
        gbc.gridx = 0;
        gbc.gridy = 7;
        JLabel ocupantesLabel = new JLabel("Número de ocupantes:");
        ocupantesLabel.setHorizontalAlignment(SwingConstants.LEFT);
        ocupantesLabel.setForeground(new Color(0x769976)); // Cambiar el color del texto
        ocupantesLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Cambiar el tamaño de la fuente
        propertyPanel.add(ocupantesLabel, gbc);

        gbc.gridx = 1;
        JTextField ocupantesField = new JTextField();
        ocupantesField.setDocument(new NumberOnlyDocument("Número de ocupantes"));
        ocupantesField.setPreferredSize(new Dimension(35, ocupantesField.getPreferredSize().height));
        ocupantesField.setPreferredSize(new Dimension(200, ocupantesField.getPreferredSize().width));
        propertyPanel.add(ocupantesField, gbc);

     
     // Botón de guardar
        gbc.gridx = 0;
        gbc.gridy = 9;
        JButton addButton = new JButton("Añadir");
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