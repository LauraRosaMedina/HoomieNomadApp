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

    // Constructor
    public AnadirPropiedades() {
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
        propertyPanel.add(ubicacionLabel, gbc);

        gbc.gridx = 1;
        JTextField ubicacionField = new JTextField("Nombre del barrio");
        ubicacionField.setPreferredSize(new Dimension(35, ubicacionField.getPreferredSize().height));
        ubicacionField.setPreferredSize(new Dimension(200, ubicacionField.getPreferredSize().width));
        ubicacionField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (ubicacionField.getText().equals("Nombre del barrio")) {
                    ubicacionField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (ubicacionField.getText().isEmpty()) {
                    ubicacionField.setText("Nombre del barrio");
                }
            }
        });
        propertyPanel.add(ubicacionField, gbc);


        // Etiqueta y menú desplegable para garaje
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel garajeLabel = new JLabel("Garaje:");
        garajeLabel.setHorizontalAlignment(SwingConstants.LEFT);
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
        propertyPanel.add(ocupantesLabel, gbc);

        gbc.gridx = 1;
        JTextField ocupantesField = new JTextField();
        ocupantesField.setDocument(new NumberOnlyDocument("Número de ocupantes"));
        ocupantesField.setPreferredSize(new Dimension(35, ocupantesField.getPreferredSize().height));
        ocupantesField.setPreferredSize(new Dimension(200, ocupantesField.getPreferredSize().width));
        propertyPanel.add(ocupantesField, gbc);

        // Botón para agregar la propiedad
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2; // Ocupa dos columnas
        JButton addButton = new JButton("Agregar propiedad");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tipoDeCasa = (String) tipoDeCasaComboBox.getSelectedItem();
                String banos = banosField.getText();
                String habitaciones = habitacionesField.getText();
                String terraza = (String) terrazaComboBox.getSelectedItem();
                String ubicacion = ubicacionField.getText();
                String garaje = (String) garajeComboBox.getSelectedItem();
                String piscina = (String) piscinaComboBox.getSelectedItem();
                String ocupantes = ocupantesField.getText();
                try {
                    // Conectar a la base de datos
                    ConexionMySQL conexion = new ConexionMySQL("root", "test", "HoomieNomad");
                    conexion.conectar();

                    // Insertar la nueva propiedad en la base de datos
                    String consulta = "INSERT INTO Propiedades (id_usuario, tipo_de_casa, num_banos, num_habitaciones, terraza_patio, ubicacion, garaje, piscina, num_ocupantes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = conexion.prepareStatement(consulta);
                    // Aquí necesitas obtener el id del usuario actualmente conectado
                    // Puedes obtenerlo de la ventana de login o pasarlo como parámetro al constructor de esta ventana
                    int idUsuario = obtenerIdUsuario(); // Debes implementar este método
                    statement.setInt(1, idUsuario);
                    statement.setString(2, tipoDeCasa);
                    statement.setInt(3, Integer.parseInt(banos));
                    statement.setInt(4, Integer.parseInt(habitaciones));
                    statement.setString(5, terraza);
                    statement.setString(6, ubicacion);
                    statement.setString(7, garaje);
                    statement.setString(8, piscina);
                    statement.setInt(9, Integer.parseInt(ocupantes));
                    int filasAfectadas = statement.executeUpdate();

                    if (filasAfectadas > 0) {
                        JOptionPane.showMessageDialog(null, "Propiedad agregada exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al agregar la propiedad.");
                    }

                    // Desconectar de la base de datos
                    conexion.desconectar();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
                }
                
             // Crear una instancia de la clase FeedPrincipal
                FeedPrincipal feedPrincipal = new FeedPrincipal();
                // Hacer visible la ventana del feed principal
                feedPrincipal.setVisible(true);
                // Cerrar la ventana actual
                dispose();

            }

			private int obtenerIdUsuario() {
				// TODO Auto-generated method stub
				return 0;
			}
        });

        contentPane.add(addButton, BorderLayout.SOUTH);

        // Botón de guardar
        gbc.gridx = 0;
        gbc.gridy = 9;
        JButton saveButton = new JButton("Guardar");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Verificar si todos los campos están completos
                if (banosField.getText().isEmpty() ||
                    habitacionesField.getText().isEmpty() ||
                    ocupantesField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Aquí guardaríamos los datos
                    JOptionPane.showMessageDialog(null, "Perfil guardado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                }
            }
        });
        
        contentPane.add(saveButton, BorderLayout.SOUTH);
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

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AnadirPropiedades frame = new AnadirPropiedades();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
