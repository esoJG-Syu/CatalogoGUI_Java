/*
 * Paradigmas de Programación
 * José Enrique González Sánchez
 * Grupo 512
 * 20-11-2024
 * Práctica 8
 */

package gatitopedia.gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;

import gatitopedia.dominio.Gato;
import gatitopedia.excepciones.ExcepcionesGatitopedia;
import gatitopedia.excepciones.ManejoDeErroresGP;
import gatitopedia.utilerias.ManejadorDeArchivos;
import gatitopedia.utilerias.MiFocusTraversalPolicy;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

/**
 * MiDialogo representa un diálogo personalizado para manejar un catálogo de
 * gatos. Extiende JDialog e implementa ActionListener e ItemListener para
 * manejar eventos.
 */
public class DialogoGatitopedia extends JDialog implements ItemListener {

	private static final long serialVersionUID = 1L;
	private VentanaPrincipal ventanaPrincipal;

	// Opciones de menú y botones
	private JMenu menuOperaciones;
	private JMenuItem operacionNuevo;
	private JMenuItem operacionModificar;
	private JMenuItem operacionGuardar;
	private JMenuItem operacionEliminar;
	private JMenuItem operacionCancelar;
	private JMenuBar barraMenu;

	private JButton bNuevo;
	private JButton bModificar;
	private JButton bGuardar;
	private JButton bEliminar;
	private JButton bCancelar;

	private JLabel entidadLabel; // Entidad
	private JComboBox<Gato> listaGatos;

	private JLabel edadLabel; // Número libre
	private JTextField edadGato;

	private JLabel pesoLabel;
	private JTextField pesoGato;

	private JLabel precioLabel; // Número con rango
	private JTextField precioAdopcion;

	private JLabel nombreLabel; // Texto en formato libre
	private JTextField nombreGato;

	private JLabel idLabel; // Texto con formato predefinido
	private JTextField identificadorGato;

	private JLabel fechaNLabel; // Fecha
	private JDateChooser fechaNacimiento;

	private JLabel sexoLabel; // Dato mutuamente excluyente fijo: "Macho" o "Hembra"
	private JRadioButton macho;
	private JRadioButton hembra;

	private JLabel esterilizadoLabel; // Dato mutuamente excluyente fijo: "Sí" o "No"
	private JRadioButton gatoEsterilizadoSI;
	private JRadioButton gatoEsterilizadoNO;

	private JLabel personalidadLabel; // Dato mutuamente excluyente dinámico
	private JComboBox<String> personalidad;

	private JLabel habitosLabel; // Dato multivalorado no excluyentes fijas "habitosEntrenamiento"
	private JCheckBox habito1;
	private JCheckBox habito2;
	private JCheckBox habito3;
	private JCheckBox habito4;

	private JLabel ColorLabel; // Dato multivalorado no excluyente dinámico
	private JComboBox<String> color;
	private JScrollPane scrollColor;
	private DefaultListModel<String> modeloColor;
	private JList<String> listaColor;
	private JButton bAñadirColor;
	private JButton bQuitarColor;

	// Componentes para la imagen
	private JLabel etiquetaImagen;
	private JLabel espacioImagen;
	private JButton botonAñadirImagen;

	// Acciones
	private Action nuevo;
	private Action modificar;
	private Action guardar;
	private Action eliminar;
	private Action cancelar;
	private Action añadirC;
	private Action quitarC;
	private Action seleccionar;

	// Variable para la verificación de los objetos y para la ruta de la imagen
	private boolean esNuevo;
	private String rutaAbsolutaTemp;
	private final String CONSTANTE_IMG = "/gatitopedia/imagenes/imgxdefecto.png";

	/**
	 * Constructor de MiDialogo.
	 */
	public DialogoGatitopedia(JFrame principal) {
		super(principal, "Cátalogo de gatos", true);
		ventanaPrincipal = (VentanaPrincipal) principal;
		crearAcciones();
		inicializarComponentes();
		establecerPoliticaFoco();
		inicializar();
		lanzarDialogo();
	}

	// Método que inicaliza todos los componentes y forma el diálogo
	private void inicializarComponentes() {
		//////////////// Menús //////////////////////
		menuOperaciones = new JMenu("Operaciones");
		menuOperaciones.setIcon(new ImageIcon(getClass().getResource("/gatitopedia/imagenes/operaciones.png")));
		menuOperaciones.setMnemonic(KeyEvent.VK_O);

		operacionNuevo = new JMenuItem(nuevo);
		operacionModificar = new JMenuItem(modificar);
		operacionGuardar = new JMenuItem(guardar);
		operacionEliminar = new JMenuItem(eliminar);
		operacionCancelar = new JMenuItem(cancelar);

		/////////////// Botones //////////////////
		bNuevo = new JButton(nuevo);
		bNuevo.getActionMap().put("BotonNuevo", nuevo);
		bNuevo.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put((KeyStroke) nuevo.getValue(Action.ACCELERATOR_KEY),
				"BotonNuevo");

		bModificar = new JButton(modificar);
		bModificar.getActionMap().put("BotonModificar", modificar);
		bModificar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put((KeyStroke) modificar.getValue(Action.ACCELERATOR_KEY), "BotonModifiar");

		bGuardar = new JButton(guardar);
		bGuardar.getActionMap().put("BotonGuardar", guardar);
		bGuardar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put((KeyStroke) guardar.getValue(Action.ACCELERATOR_KEY), "BotonGuardar");

		bEliminar = new JButton(eliminar);
		bEliminar.getActionMap().put("BotonEliminar", eliminar);
		bEliminar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put((KeyStroke) eliminar.getValue(Action.ACCELERATOR_KEY), "BotonEliminar");

		bCancelar = new JButton(cancelar);
		bCancelar.getActionMap().put("BotonCancelar", cancelar);
		bCancelar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put((KeyStroke) cancelar.getValue(Action.ACCELERATOR_KEY), "BotonCancelar");

		//////////////// Crear la barra y añadir los elementos ////////////////////////
		barraMenu = new JMenuBar();

		menuOperaciones.add(operacionNuevo);
		menuOperaciones.add(operacionModificar);
		menuOperaciones.add(operacionGuardar);
		menuOperaciones.add(operacionEliminar);
		menuOperaciones.add(operacionCancelar);

		barraMenu.add(menuOperaciones);
		this.setJMenuBar(barraMenu);

		//////////// Componentes /////////////////////////

		// I - Entidad
		entidadLabel = new JLabel("Entidades:");
		entidadLabel.setDisplayedMnemonic(KeyEvent.VK_N);
		listaGatos = new JComboBox<Gato>();
		listaGatos.setPreferredSize(new Dimension(260, 20));
		listaGatos.setToolTipText("Seleccione una entidad");
		listaGatos.addItemListener(this);

		// II - Edad
		edadLabel = new JLabel("Edad:");
		edadGato = new JTextField(20);
		edadLabel.setLabelFor(edadGato);
		edadLabel.setDisplayedMnemonic(KeyEvent.VK_E);
		edadGato.setToolTipText("Edad del animal");

		// III - Peso
		pesoLabel = new JLabel("Peso:");
		pesoGato = new JTextField(20);
		pesoLabel.setLabelFor(pesoGato);
		pesoLabel.setDisplayedMnemonic(KeyEvent.VK_S);
		pesoGato.setToolTipText("Peso del animal");

		// IV - Precio
		precioLabel = new JLabel("Precio:");
		precioAdopcion = new JTextField(20);
		precioLabel.setLabelFor(precioAdopcion);
		precioLabel.setDisplayedMnemonic(KeyEvent.VK_C);
		precioAdopcion.setToolTipText("Costo de adopción del animal");

		// V - Nombre
		nombreLabel = new JLabel("Nombre:");
		nombreGato = new JTextField(20);
		nombreLabel.setLabelFor(nombreGato);
		nombreLabel.setDisplayedMnemonic(KeyEvent.VK_M);
		nombreGato.setToolTipText("Nombre que tiene el animal");

		// VI - Indentificador
		idLabel = new JLabel("Identificador:");
		identificadorGato = new JTextField(20);
		idLabel.setLabelFor(identificadorGato);
		idLabel.setDisplayedMnemonic(KeyEvent.VK_D);
		identificadorGato
				.setToolTipText("Formato: Tres letras mayúsculas, cuatro números, seguido de H o M. Ejemplo: ABC1234H");

		// VII - Fecha Nacimiento
		fechaNLabel = new JLabel("Fecha de nacimiento:");
		fechaNacimiento = new JDateChooser();
		fechaNacimiento.setPreferredSize(new Dimension(230, 20));
		fechaNLabel.setLabelFor(fechaNacimiento);
		fechaNLabel.setDisplayedMnemonic(KeyEvent.VK_F);
		fechaNacimiento.getCalendarButton().setToolTipText("Fecha en la que nació el animal");
		fechaNacimiento.getDateEditor().getUiComponent().setToolTipText("DD/MM/AAAA");

		// VIII - Género
		sexoLabel = new JLabel("Género:");
		sexoLabel.setToolTipText("Seleccione una opción");
		macho = new JRadioButton("Macho");
		hembra = new JRadioButton("Hembra");
		sexoLabel.setDisplayedMnemonic(KeyEvent.VK_G);
		sexoLabel.setLabelFor(macho);
		macho.setToolTipText("Seleccione si es un macho");
		macho.setSelected(true);
		hembra.setToolTipText("Seleccione si es una hembra");

		ButtonGroup grupoGenero = new ButtonGroup();
		grupoGenero.add(macho);
		grupoGenero.add(hembra);

		// IX - Esterilizado
		esterilizadoLabel = new JLabel("Esterilizado:");
		esterilizadoLabel.setToolTipText("Seleccione una opción");
		gatoEsterilizadoSI = new JRadioButton("Si");
		gatoEsterilizadoNO = new JRadioButton("No");
		esterilizadoLabel.setDisplayedMnemonic(KeyEvent.VK_Z);
		esterilizadoLabel.setLabelFor(gatoEsterilizadoSI);
		gatoEsterilizadoSI.setToolTipText("Seleccione si su gato está esterilizado");
		gatoEsterilizadoSI.setSelected(true);
		gatoEsterilizadoNO.setToolTipText("Seleccione si su gato no está esterilizado");

		ButtonGroup esterilizado = new ButtonGroup();
		esterilizado.add(gatoEsterilizadoSI);
		esterilizado.add(gatoEsterilizadoNO);

		// X - Personalidad
		personalidadLabel = new JLabel("Personalidad:");
		personalidad = new JComboBox<>();
		personalidad.setPreferredSize(new Dimension(220, 20));
		personalidadLabel.setDisplayedMnemonic(KeyEvent.VK_P);
		personalidadLabel.setLabelFor(personalidad);
		personalidad.setToolTipText("Seleccione o añada la personalidad del gato");
		personalidad.setEditable(true);

		// XI - Habitos
		habitosLabel = new JLabel("Habitos:");
		habito1 = new JCheckBox("Usa caja de arena");
		habito2 = new JCheckBox("Usa rascador");
		habito3 = new JCheckBox("Camina con correa");
		habito4 = new JCheckBox("Obedece órdenes");
		habitosLabel.setDisplayedMnemonic(KeyEvent.VK_H);

		// XII - Color
		ColorLabel = new JLabel("Color:");
		color = new JComboBox<>();
		color.setPreferredSize(new Dimension(220, 20));
		modeloColor = new DefaultListModel<>();
		listaColor = new JList<>(modeloColor);
		scrollColor = new JScrollPane(listaColor);
		scrollColor.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollColor.setPreferredSize(new Dimension(200, 60));
		ColorLabel.setDisplayedMnemonic(KeyEvent.VK_C);
		ColorLabel.setLabelFor(color);
		color.setToolTipText("Seleccione o añada el color del gato");
		color.setEditable(true);

		bAñadirColor = new JButton(añadirC);
		bAñadirColor.setPreferredSize(new Dimension(110, 40));
		bAñadirColor.getActionMap().put("BotonAñadirC", añadirC);
		bAñadirColor.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put((KeyStroke) añadirC.getValue(Action.ACCELERATOR_KEY), "BotonAñadirC");

		bQuitarColor = new JButton(quitarC);
		bQuitarColor.setPreferredSize(new Dimension(110, 40));
		bQuitarColor.getActionMap().put("BotonQuitarC", quitarC);
		bQuitarColor.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put((KeyStroke) quitarC.getValue(Action.ACCELERATOR_KEY), "BotonQuitarC");

		// XII - Imagen
		etiquetaImagen = new JLabel("Imagen:");
		espacioImagen = new JLabel();
		espacioImagen.setPreferredSize(new Dimension(200, 60));

		botonAñadirImagen = new JButton(seleccionar);
		etiquetaImagen.setDisplayedMnemonic(KeyEvent.VK_M);
		etiquetaImagen.setLabelFor(espacioImagen);
		botonAñadirImagen.setMnemonic(KeyEvent.VK_L);

		/////////////// Paneles ////////////

		// Añadimos los componentes por medio de dos paneles que se meteen en el panel
		// principal
		// Cada elemento tiene su panel que se añade a los paneles anteriores

		JPanel panelN = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelN.add(entidadLabel);
		panelN.add(listaGatos);

		JPanel panelEs = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		JPanel panelAux = new JPanel(new GridLayout(9, 1));
		panelAux.add(bNuevo);
		panelAux.add(new JPanel());
		panelAux.add(bModificar);
		panelAux.add(new JPanel());
		panelAux.add(bGuardar);
		panelAux.add(new JPanel());
		panelAux.add(bEliminar);
		panelAux.add(new JPanel());
		panelAux.add(bCancelar);
		panelEs.add(panelAux);

		JPanel panelCentral1 = new JPanel(new GridLayout(9, 2));

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel1.add(edadLabel);
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel2.add(edadGato);
		JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel3.add(pesoLabel);
		JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel4.add(pesoGato);
		JPanel panel5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel5.add(precioLabel);
		JPanel panel6 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel6.add(precioAdopcion);
		JPanel panel7 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel7.add(nombreLabel);
		JPanel panel8 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel8.add(nombreGato);
		JPanel panel9 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel9.add(idLabel);
		JPanel panel10 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel10.add(identificadorGato);
		JPanel panel11 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel11.add(fechaNLabel);
		JPanel panel12 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel12.add(fechaNacimiento);
		JPanel panel13 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel13.add(sexoLabel);
		JPanel panel14 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel14.add(macho);
		panel14.add(hembra);

		JPanel panel15 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel15.add(habitosLabel);

		JPanel panel16 = new JPanel(new GridLayout(2, 1));
		JPanel panelAux1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelAux1.add(habito1);
		JPanel panelAux2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelAux2.add(habito2);

		panel16.add(panelAux1);
		panel16.add(panelAux2);

		JPanel panel17 = new JPanel();
		panel17.add(new JPanel());

		JPanel panel18 = new JPanel(new GridLayout(2, 1));
		JPanel panelAux3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelAux3.add(habito3);
		JPanel panelAux4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelAux4.add(habito4);

		panel18.add(panelAux3);
		panel18.add(panelAux4);

		JPanel panelCentral2 = new JPanel(new GridLayout(7, 2));

		JPanel panelC1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelC1.add(esterilizadoLabel);
		JPanel panelC2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelC2.add(gatoEsterilizadoSI);
		panelC2.add(gatoEsterilizadoNO);
		JPanel panelC3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelC3.add(personalidadLabel);
		JPanel panelC4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelC4.add(personalidad);
		JPanel panelC6 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelC6.add(ColorLabel);
		JPanel panelC7 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelC7.add(color);
		JPanel panelC8 = new JPanel();
		panelC8.add(new JPanel());

		JPanel panelC14 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelC14.add(scrollColor);

		JPanel panelC15 = new JPanel();
		panelC15.add(new JPanel());
		JPanel panelC16 = new JPanel();
		panelC16.add(new JPanel());

		JPanel panelC9 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelC9.add(bAñadirColor);
		panelC9.add(bQuitarColor);

		JPanel panelC10 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelC10.add(etiquetaImagen);
		JPanel panelC11 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelC11.add(espacioImagen);
		JPanel panelC12 = new JPanel();
		panelC12.add(new JPanel());
		JPanel panelC13 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelC13.add(botonAñadirImagen);

		// Se agregan los paneles de los componentes a sus respectivios paneles
		panelCentral1.add(panel1);
		panelCentral1.add(panel2);
		panelCentral1.add(panel3);
		panelCentral1.add(panel4);
		panelCentral1.add(panel5);
		panelCentral1.add(panel6);
		panelCentral1.add(panel7);
		panelCentral1.add(panel8);
		panelCentral1.add(panel9);
		panelCentral1.add(panel10);
		panelCentral1.add(panel11);
		panelCentral1.add(panel12);
		panelCentral1.add(panel13);
		panelCentral1.add(panel14);
		panelCentral1.add(panel15);
		panelCentral1.add(panel16);
		panelCentral1.add(panel17);
		panelCentral1.add(panel18);

		panelCentral2.add(panelC1);
		panelCentral2.add(panelC2);
		panelCentral2.add(panelC3);
		panelCentral2.add(panelC4);
		panelCentral2.add(panelC6);
		panelCentral2.add(panelC7);
		panelCentral2.add(panelC8);

		panelCentral2.add(panelC14);
		panelCentral2.add(panelC15);

		panelCentral2.add(panelC9);
		panelCentral2.add(panelC10);
		panelCentral2.add(panelC11);
		panelCentral2.add(panelC12);
		panelCentral2.add(panelC13);

		JPanel panelCentral = new JPanel(new GridLayout(1, 2));
		panelCentral.add(panelCentral1);
		panelCentral.add(panelCentral2);

		// PANEL PRINCIPAL
		JPanel panelPrincipal = new JPanel(new BorderLayout());
		panelPrincipal.add(panelN, BorderLayout.NORTH);
		panelPrincipal.add(panelEs, BorderLayout.EAST);
		panelPrincipal.add(panelCentral, BorderLayout.CENTER);

		this.add(panelPrincipal);
	}

	// Método que define las características del diálogo para ser lanzado cuando se
	// llame
	private void lanzarDialogo() {
		this.setSize(1280, 600);
		this.setLocationRelativeTo(ventanaPrincipal);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/gatitopedia/imagenes/icono.png")));

		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setModal(true);
		this.setVisible(true);
	}

	// Creamos las acciones en un metodo
	private void crearAcciones() {

		// Acción nuevo
		nuevo = new AbstractAction("Nuevo", new ImageIcon(getClass().getResource("/gatitopedia/imagenes/nuevo.png"))) {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				accionNuevo();
			}
		};
		nuevo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		nuevo.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		nuevo.putValue(Action.SHORT_DESCRIPTION, "Nuevo Registro");

		// Acción Modificar
		modificar = new AbstractAction("Modificar",
				new ImageIcon(getClass().getResource("/gatitopedia/imagenes/modificar.png"))) {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				accionModificar();
			}
		};

		modificar.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK));
		modificar.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_M);
		modificar.putValue(Action.SHORT_DESCRIPTION, "Modificar Registro");

		// Acción Guardar
		guardar = new AbstractAction("Guardar",
				new ImageIcon(getClass().getResource("/gatitopedia/imagenes/guardar.png"))) {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				accionGuardar();
			}
		};
		guardar.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
		guardar.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_G);
		guardar.putValue(Action.SHORT_DESCRIPTION, "Guardar Registro");

		// Acción Eliminar
		eliminar = new AbstractAction("Eliminar",
				new ImageIcon(getClass().getResource("/Gatitopedia/imagenes/eliminar.png"))) {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				accionEliminar();
			}
		};
		eliminar.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		eliminar.putValue(Action.SHORT_DESCRIPTION, "Eliminar Registro");

		// Acción Cancelar
		cancelar = new AbstractAction("Cancelar",
				new ImageIcon(getClass().getResource("/gatitopedia/imagenes/cancelar.png"))) {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				accionCancelar();
			}
		};
		cancelar.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		cancelar.putValue(Action.SHORT_DESCRIPTION, "Cancelar un nuevo registro");

		// Acción seleccioanar imagen
		seleccionar = new AbstractAction("Seleccionar",
				new ImageIcon(getClass().getResource("/gatitopedia/imagenes/select.png"))) {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				accionAñadirImagen();
			}
		};
		seleccionar.putValue(Action.SHORT_DESCRIPTION, "Selecionar una imagen para subir");

		// Acción añadir color
		añadirC = new AbstractAction("Añadir",
				new ImageIcon(getClass().getResource("/gatitopedia/imagenes/añadir.png"))) {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				try {
					accionAñadirColor();
				} catch (ExcepcionesGatitopedia ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), ex.getTitulo(), JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
		};
		añadirC.putValue(Action.SHORT_DESCRIPTION, "Añade un color más a la lista");

		// Acción Quitar color
		quitarC = new AbstractAction("Quitar",
				new ImageIcon(getClass().getResource("/gatitopedia/imagenes/quitar.png"))) {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				accionQuitarColor();
			}
		};
		quitarC.putValue(Action.SHORT_DESCRIPTION, "Quita un color de la lista");
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// Manejo de eventos de cambio de estado en los JComboBox
		if (e.getSource().equals(listaGatos)) {
			metodoConsulta();
		}
	}

	private void metodoConsulta() {

		Gato gato = (Gato) listaGatos.getSelectedItem();

		edadGato.setText(String.valueOf(gato.getEdadGato()));
		pesoGato.setText(String.valueOf(gato.getPesoGato()));
		precioAdopcion.setText(String.valueOf(gato.getPrecioAdopcion()));
		nombreGato.setText(String.valueOf(gato.getNombreGato()));
		identificadorGato.setText(String.valueOf(gato.getIdentificadorGato()));
		fechaNacimiento.setDate(gato.getFechaNacimiento());

		// Restaurar posición de los radios

		String sexo = gato.getSexoGato();
		if (sexo.equals(macho.getText())) {
			macho.setSelected(true);
		} else {
			hembra.setSelected(true);
		}

		boolean castrado = gato.getGatoEsterilizado();
		if (castrado) {
			gatoEsterilizadoSI.setSelected(true);
		} else {
			gatoEsterilizadoNO.setSelected(true);
		}

		// Restaurar selección de los hábitos (JCheckBox)
		ArrayList<String> habitosSeleccionados = gato.getHabitosEntrenamiento(); // Obtener los hábitos del gato

		habito1.setSelected(habitosSeleccionados.contains(habito1.getText()));
		habito2.setSelected(habitosSeleccionados.contains(habito2.getText()));
		habito3.setSelected(habitosSeleccionados.contains(habito3.getText()));
		habito4.setSelected(habitosSeleccionados.contains(habito4.getText()));

		// Restaurar el valor del JComboBox
		String opSelecccionada = gato.getPersonalidad();
		boolean encontrado = false;

		for (int i = 0; i < personalidad.getItemCount(); i++) {
			if (personalidad.getItemAt(i).equalsIgnoreCase(opSelecccionada)) {
				personalidad.setSelectedIndex(i);
				encontrado = true;
				break;
			}
		}
		if (!encontrado) {
			personalidad.addItem(opSelecccionada);
			personalidad.setSelectedItem(opSelecccionada);
		}

		// Restaurar dato multivalorado no excluyente dinámico
		ArrayList<String> listaColores = gato.getColorGato();
		modeloColor.clear();
		for (int i = 0; i < listaColores.size(); i++) {
			modeloColor.addElement(listaColores.get(i));
		}

		// Restaurar imagen
		this.rutaAbsolutaTemp = gato.getRutaImagen();
		muestraImagen();
	}

	// Métodos para manejar las acciones de los botones del menú
	private void accionNuevo() {
		limpiarCampos();
		habilitarCampos(true);

		bGuardar.setEnabled(true);
		operacionGuardar.setEnabled(true);
		bCancelar.setEnabled(true);
		operacionCancelar.setEnabled(true);

		bNuevo.setEnabled(false);
		operacionNuevo.setEnabled(false);
		bModificar.setEnabled(false);
		operacionModificar.setEnabled(false);
		bEliminar.setEnabled(false);
		operacionEliminar.setEnabled(false);
		listaGatos.setEnabled(false);

		// Activar variable esNuevo
		esNuevo = true;
	}

	private void accionModificar() {
		// Desactivar la variable esNuevo
		esNuevo = false;

		// Habilitar campos y botones Guardar y Cancelar
		habilitarCampos(true);
		bGuardar.setEnabled(true);
		operacionGuardar.setEnabled(true);
		bCancelar.setEnabled(true);
		operacionCancelar.setEnabled(true);

		// Deshabilitar botones y menus de Nuevo, Modificar y Eliminar
		bNuevo.setEnabled(false);
		operacionNuevo.setEnabled(false);
		bModificar.setEnabled(false);
		operacionModificar.setEnabled(false);
		bEliminar.setEnabled(false);
		operacionEliminar.setEnabled(false);
		listaGatos.setEnabled(false);
	}

	private void accionGuardar() {
		// Objeto de la entidad gato
		Gato gato;

		boolean esNuevo = this.esNuevo;

		if (esNuevo) {
			// Crear nuevo objeto Gato
			gato = new Gato();
		} else {
			// Usar el objeto seleccionado de la lista principal
			gato = (Gato) listaGatos.getSelectedItem();
		}

		try {

			/*
			 * String temporal para almacenar el identificador del gato antes del set del
			 * nuevo identificador lo usamos para las operaciones de la imagen
			 */
			String getAntesSet = gato.getIdentificadorGato();

			// Aquí empiezan los seteos normales del método guardar
			gato.setEdadGato(edadGato.getText());
			gato.setPesoGato(pesoGato.getText());
			gato.setPrecioAdopcion(precioAdopcion.getText());
			gato.setNombreGato(nombreGato.getText());
			gato.setIdentificadorGato(identificadorGato.getText());

			gato.setFechaNacimiento(fechaNacimiento.getDate());

			// Radios Macho, Hembra, esterilizado
			if (macho.isSelected()) {
				gato.setSexoGato(macho.getText());
			} else if (hembra.isSelected()) {
				gato.setSexoGato(hembra.getText());
			}

			if (gatoEsterilizadoSI.isSelected()) {
				gato.setGatoEsterilizado(true);
			} else if (gatoEsterilizadoNO.isSelected()) {
				gato.setGatoEsterilizado(false);
			}

			// Crear lista de hábitos seleccionados
			ArrayList<String> habitosSeleccionados = new ArrayList<>();

			// Verificar y agregar hábitos seleccionados a la lista temporal
			if (habito1.isSelected()) {
				habitosSeleccionados.add(habito1.getText());
			}
			if (habito2.isSelected()) {
				habitosSeleccionados.add(habito2.getText());
			}
			if (habito3.isSelected()) {
				habitosSeleccionados.add(habito3.getText());
			}
			if (habito4.isSelected()) {
				habitosSeleccionados.add(habito4.getText());
			}

			gato.setHabitosEntrenamiento(habitosSeleccionados);

			// Dato mutuamente excluyente dinámico
			String opSeleccionada = personalidad.getEditor().getItem().toString().trim();
			boolean existe = false;

			for (int i = 0; i < personalidad.getItemCount(); i++) {
				if (personalidad.getItemAt(i).equalsIgnoreCase(opSeleccionada)) {
					personalidad.setSelectedIndex(i);
					existe = true;
					break;
				}
			}

			if (!existe && !opSeleccionada.isEmpty()) {
				personalidad.addItem(opSeleccionada);
				personalidad.setSelectedItem(opSeleccionada);
			}

			gato.setPersonalidad(opSeleccionada);

			// Dato multivalorado no excluyente dinámico

			ArrayList<String> listaColores = new ArrayList<>();
			for (int i = 0; i < modeloColor.getSize(); i++) {
				listaColores.add(modeloColor.getElementAt(i));
			}

			gato.setColorGato(listaColores);

			/*
			 * Procesamos la imagen en un método aparte simplemente por comodidad y para no
			 * saturar el método guardar
			 */
			procesarImagen(gato, esNuevo, getAntesSet);

			
			// Mensaje de éxito acorde a la acción
			if (esNuevo) {
				listaGatos.addItem(gato); // Solo agregar si es nuevo
				JOptionPane.showMessageDialog(this, "Nuevo Gato Guardado Exitosamente.", "Información",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "Datos del Gato Modificados.", "Información",
						JOptionPane.INFORMATION_MESSAGE);
			}

			// Limpiar los campos después de guardar
			accionCancelar();

		} catch (ExcepcionesGatitopedia e) {

			JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitulo(), JOptionPane.WARNING_MESSAGE);
			return;
		} catch(ManejoDeErroresGP e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitulo(), JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	private void accionEliminar() {

		Gato gatoSeleccionado = (Gato) listaGatos.getSelectedItem();

		// Mensaje de confirmación
		int respuesta = JOptionPane.showConfirmDialog(this,
				"¿Está seguro de que desea eliminar al gato: " + gatoSeleccionado.getNombreGato() + "?",
				"Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		// Si el usuario confirma la eliminación
		if (respuesta == JOptionPane.YES_OPTION) {

			listaGatos.removeItemListener(this);
			// Eliminar el gato de la lista
			listaGatos.removeItem(gatoSeleccionado);

			JOptionPane.showMessageDialog(this,
					"El gato '" + gatoSeleccionado.getNombreGato() + "' ha sido eliminado con éxito.",
					"Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
			listaGatos.addItemListener(this);
		}
		accionCancelar();
	}

	private void accionCancelar() {
		limpiarCampos();
		habilitarCampos(false);

		bGuardar.setEnabled(false);
		operacionGuardar.setEnabled(false);
		bCancelar.setEnabled(false);
		operacionCancelar.setEnabled(false);

		bNuevo.setEnabled(true);
		operacionNuevo.setEnabled(true);

		verificarLista();
	}

	// Métodos para manejar las acciones de los botones auxiliares
	private void accionAñadirColor() throws ExcepcionesGatitopedia {
		String colorSeleccionado = color.getEditor().getItem().toString().trim();
		boolean existe = false;

		// Verificar que no sea un valor vacío
		if (colorSeleccionado.isEmpty()) {
			return;
		}

		for (int i = 0; i < color.getItemCount(); i++) {
			if (color.getItemAt(i).equalsIgnoreCase(colorSeleccionado)) {
				color.setSelectedIndex(i);
				colorSeleccionado = color.getItemAt(i);
				existe = true;
				break;
			}
		}

		if (!existe && !colorSeleccionado.isEmpty()) {
			color.addItem(colorSeleccionado);
			color.setSelectedItem(colorSeleccionado);
		}

		if (modeloColor.toString().toLowerCase().contains(colorSeleccionado.toLowerCase())) {
			JOptionPane.showMessageDialog(this, "El elemento ya está en la lista", "Información",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			modeloColor.addElement(colorSeleccionado);
		}

	}

	private void accionQuitarColor() {
		if (listaColor.getSelectedIndex() == -1) {
			JOptionPane.showMessageDialog(this, "Ningún elemento de la lista está seleccionado.", "Advertencia",
					JOptionPane.WARNING_MESSAGE);
		} else {
			int indiceSeleccionado = listaColor.getSelectedIndex();
			modeloColor.remove(indiceSeleccionado);
		}

	}

	// Método para lanzar el díalogo para seleccionar la imágen
	private void accionAñadirImagen() {

		// Instancia de la clase JFileChooser
		JFileChooser chooser = new JFileChooser();
		// Configuración del díalogo
		chooser.setDialogTitle("Seleccione una imagen para su gato.");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter filtroImgsJPG = new FileNameExtensionFilter("Archivos de imagen (JPG)", "jpg");
		FileNameExtensionFilter filtroImgsPNG = new FileNameExtensionFilter("Archivos de imagen (PNG)", "png");
		FileNameExtensionFilter filtroImgsGIF = new FileNameExtensionFilter("Archivos de imagen (GIF)", "gif");
		chooser.setFileFilter(filtroImgsJPG);
		chooser.addChoosableFileFilter(filtroImgsPNG);
		chooser.addChoosableFileFilter(filtroImgsGIF);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setCurrentDirectory((new File(System.getProperty("user.dir"))));
		// Personalizar el botón "Aceptar"
		chooser.setApproveButtonText("Seleccionar Imagen");
		chooser.setApproveButtonToolTipText("Presione para seleccionar la imagen");
		chooser.setMultiSelectionEnabled(false);

		// Lanzar el díalogo y verificar la existencia del archivo
		int opcion = chooser.showOpenDialog(this);
		if (opcion == JFileChooser.APPROVE_OPTION) {
			File archivoSeleccionado = chooser.getSelectedFile();

			if (archivoSeleccionado.exists()) {
				this.rutaAbsolutaTemp = archivoSeleccionado.getAbsolutePath();
				muestraImagen();
			} else {
				// Mostrar mensaje de advertencia si el archivo no existe
				JOptionPane.showMessageDialog(null, "El archivo seleccionado no existe.", "Archivo inexistente",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/**
	 * Método privado para mostrar la imagen en el JLabel, escalando con la
	 * dimensión más grande y manteniendo la proporción.
	 */
	private void muestraImagen() {
		// Cargar la imagen desde la ruta seleccionada
		ImageIcon imagenSeleccionada;
		if (this.rutaAbsolutaTemp.equals(CONSTANTE_IMG)) {
			imagenSeleccionada = new ImageIcon(getClass().getResource(this.rutaAbsolutaTemp));
		} else {
			imagenSeleccionada = new ImageIcon(this.rutaAbsolutaTemp);
		}

		// Obtener las dimensiones de la imagen
		int anchoImg = imagenSeleccionada.getIconWidth();
		int altoImg = imagenSeleccionada.getIconHeight();

		// Obtener las dimensiones del JLabel donde se mostrará la imagen
		int anchoLabel = this.espacioImagen.getWidth();
		int altoLabel = this.espacioImagen.getHeight();
		Image imagenEscalada;

		// Calcular los ratios de la imagen y del JLabel
		double ratioImg = (double) anchoImg / altoImg;
		double ratioLabel = (double) anchoLabel / altoLabel;

		// Escalar la imagen para ajustarse completamente dentro del JLabel
		if (ratioImg > ratioLabel) {
			imagenEscalada = imagenSeleccionada.getImage().getScaledInstance(anchoLabel, -1, Image.SCALE_SMOOTH);
		} else {
			imagenEscalada = imagenSeleccionada.getImage().getScaledInstance(-1, altoLabel, Image.SCALE_SMOOTH);
		}

		this.espacioImagen.setHorizontalAlignment(JLabel.CENTER);
		this.espacioImagen.setVerticalAlignment(JLabel.CENTER);

		this.espacioImagen.setIcon(new ImageIcon(imagenEscalada));
	}
	
	// Método para procesar la imagen en el método guardar.
	private void procesarImagen(Gato gato, boolean esNuevo, String getAntesSet) throws ManejoDeErroresGP {
		// Procesar la imagen y el directorio
		File dirImgs = new File(System.getProperty("user.dir") + File.separator + "imagen");
		if (!dirImgs.exists()) {
			dirImgs.mkdir();
		}

		String getDespuesSet = gato.getIdentificadorGato(); // Almacena el nuevo identificador

		/*
		 * Verificar si es necesario eliminar la imagen anterior únicamente cuando se
		 * usa una nueva imagen con el mismo nombre(identificador) y cuando se cambia la
		 * imagen y el nombre
		 */
		if (!esNuevo && !gato.getRutaImagen().equals(CONSTANTE_IMG)
				&& !(gato.getRutaImagen().equals(this.rutaAbsolutaTemp))) {

			File imagenAnterior = new File(gato.getRutaImagen());
			if (imagenAnterior.exists()) {
				imagenAnterior.delete();
			}
		}

		// Obtener la extensión del archivo original y la ruta
		String rutaD = "";
		File archivoO = new File(this.rutaAbsolutaTemp);
		int pExtension = archivoO.getName().lastIndexOf(".");
		String extension = "";
		if (pExtension > 0) {
			extension = archivoO.getName().substring(pExtension);
		}

		if (!(getAntesSet.equals(getDespuesSet))) {
			rutaD = "imagen" + File.separator + getDespuesSet + extension;
		} else {
			rutaD = "imagen" + File.separator + getAntesSet + extension;
		}

		// Crear la copia y almacenar la ruta relativa de la copia
		if (!(gato.getRutaImagen().equals(this.rutaAbsolutaTemp)) || !(getAntesSet.equals(getDespuesSet))) {
				ManejadorDeArchivos.copiarArchivo(this.rutaAbsolutaTemp, rutaD);
			/*
			 * Verificar si es necesario eliminar la imagen cuando solo se cambia el nombre
			 * y la imagen es la misma
			 */
			if (!esNuevo && !(getAntesSet.equals(getDespuesSet))) {
				File imagenAnterior = new File(gato.getRutaImagen());
				if (imagenAnterior.exists()) {
					imagenAnterior.delete();
				}
			}

			// Aquí hacemos el set de la ruta
			File archivoD = new File(rutaD);
			if (archivoD.exists()) {
				gato.setRutaImagen(rutaD);
			} else {
				gato.setRutaImagen(CONSTANTE_IMG);
			}
		}
	}
	

	/**
	 * Establece la política de enfoque personalizada para el diálogo.
	 */
	private void establecerPoliticaFoco() {
		Vector<Component> componentes = new Vector<Component>();
		componentes.add(bNuevo);
		componentes.add(bModificar);
		componentes.add(bGuardar);
		componentes.add(bEliminar);
		componentes.add(bCancelar);
		componentes.add(listaGatos);
		componentes.add(edadGato);
		componentes.add(pesoGato);
		componentes.add(precioAdopcion);
		componentes.add(nombreGato);
		componentes.add(identificadorGato);
		componentes.add(fechaNacimiento);
		componentes.add(macho);
		componentes.add(gatoEsterilizadoSI);
		componentes.add(personalidad);
		componentes.add(habito1);
		componentes.add(habito2);
		componentes.add(habito3);
		componentes.add(habito4);
		componentes.add(color);
		componentes.add(bAñadirColor);
		componentes.add(bQuitarColor);
		componentes.add(botonAñadirImagen);
		MiFocusTraversalPolicy politicaFoco = new MiFocusTraversalPolicy(componentes);
		this.setFocusTraversalPolicy(politicaFoco);
	}

	private void limpiarCampos() {
		// Limpiar los campos de texto
		nombreGato.setText("");
		identificadorGato.setText("");
		fechaNacimiento.setDate(new Date());
		pesoGato.setText("");
		precioAdopcion.setText("");
		edadGato.setText("");
		// Restablecer los botones de opción a su valor predeterminado
		macho.setSelected(true);
		gatoEsterilizadoSI.setSelected(true);
		// Desmarcar las casillas de verificación
		habito1.setSelected(false);
		habito2.setSelected(false);
		habito3.setSelected(false);
		habito4.setSelected(false);

		personalidad.setSelectedIndex(0);
		modeloColor.clear();
		// Imagen por defecto y llamada al método
		this.rutaAbsolutaTemp = CONSTANTE_IMG;
		muestraImagen();
	}

	private void habilitarCampos(boolean habilitar) {
		// Habilitar o deshabilitar los campos

		nombreGato.setEnabled(habilitar);
		identificadorGato.setEnabled(habilitar);
		fechaNacimiento.setEnabled(habilitar);
		pesoGato.setEnabled(habilitar);
		precioAdopcion.setEnabled(habilitar);
		edadGato.setEnabled(habilitar);
		personalidad.setEnabled(habilitar);
		color.setEnabled(habilitar);
		macho.setEnabled(habilitar);
		hembra.setEnabled(habilitar);
		gatoEsterilizadoSI.setEnabled(habilitar);
		gatoEsterilizadoNO.setEnabled(habilitar);
		habito1.setEnabled(habilitar);
		habito2.setEnabled(habilitar);
		habito3.setEnabled(habilitar);
		habito4.setEnabled(habilitar);
		bAñadirColor.setEnabled(habilitar);
		bQuitarColor.setEnabled(habilitar);
		botonAñadirImagen.setEnabled(habilitar);
		listaColor.setEnabled(habilitar);
	}

	private void verificarLista() {
		if (listaGatos.getItemCount() > 0) {
			listaGatos.setEnabled(true);
			bModificar.setEnabled(true);
			operacionModificar.setEnabled(true);
			bEliminar.setEnabled(true);
			operacionEliminar.setEnabled(true);
			listaGatos.setSelectedIndex(0);
			metodoConsulta();
		} else {
			listaGatos.setEnabled(false);
			bModificar.setEnabled(false);
			operacionModificar.setEnabled(false);
			bEliminar.setEnabled(false);
			operacionEliminar.setEnabled(false);
		}
	}

	void inicializar() {
		listaGatos.setEnabled(false);
		nombreGato.setEnabled(false);
		identificadorGato.setEnabled(false);
		fechaNacimiento.setEnabled(false);
		pesoGato.setEnabled(false);
		precioAdopcion.setEnabled(false);
		edadGato.setEnabled(false);
		personalidad.setEnabled(false);
		color.setEnabled(false);
		macho.setEnabled(false);
		hembra.setEnabled(false);
		gatoEsterilizadoSI.setEnabled(false);
		gatoEsterilizadoNO.setEnabled(false);
		habito1.setEnabled(false);
		habito2.setEnabled(false);
		habito3.setEnabled(false);
		habito4.setEnabled(false);
		bAñadirColor.setEnabled(false);
		bQuitarColor.setEnabled(false);
		botonAñadirImagen.setEnabled(false);
		listaColor.setEnabled(false);

		// Botones
		bNuevo.setEnabled(true);
		operacionNuevo.setEnabled(true);

		bGuardar.setEnabled(false);
		operacionGuardar.setEnabled(false);
		bCancelar.setEnabled(false);
		operacionCancelar.setEnabled(false);

		verificarLista();

		// Inserción de datos temporales, se eliminarán en la próxima práctica
		color.addItem("Negro");
		color.addItem("Blanco");
		color.addItem("Marrón");

		personalidad.addItem("Agresivo");
		personalidad.addItem("Juguetón");
		personalidad.addItem("Asustadizo");

	}
}
