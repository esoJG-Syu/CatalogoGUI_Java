/*
 * Paradigmas de Programación
 * José Enrique González Sánchez
 * Grupo 512
 * 20-11-2024
 * Práctica 8
 */

package gatitopedia.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	// Declaración de variables miembro
	private JMenu archivoMenu;
	private JMenu operacionesMenu;
	private JMenu ayudaMenu;
	private JMenuItem abrirMenu;
	private JMenuItem guardarMenu;
	private JMenuItem salirMenu;
	private JMenuItem catalogoMenu;
	private JMenuItem consultasMenu;
	private JMenuItem acercaDeMenu;
	private JMenuBar barraMenu;

	public VentanaPrincipal() {
		// Establecer título e icono de la ventana
		super("Catálogo de Gatos");
		this.setIconImage(
				Toolkit.getDefaultToolkit().getImage(getClass().getResource("/gatitopedia/imagenes/icono.png")));

		// Inicializar Menús y la Barra
		configurarMenus();
		configurarBarraMenu();

		// Configurar propiedades de la ventana
		this.setUndecorated(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				salir();
			}
		});

		// Configurar el tamaño de la ventana
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.getContentPane().setLayout(new BorderLayout());
		JLabel fondo = new JLabel();
		ImageIcon imagenFondo = new ImageIcon(getClass().getResource("/gatitopedia/imagenes/fondo.png"));
		Image imagenEscalada = imagenFondo.getImage().getScaledInstance(-1, getSize().height - 80, Image.SCALE_SMOOTH);
		fondo.setIcon(new ImageIcon(imagenEscalada));
		this.getContentPane().add(fondo);
		this.getContentPane().setBackground(Color.BLACK);
		this.setResizable(false);
		this.setVisible(true);
	}

	// Clase interna para manejar todos los eventos de acción
	private class MenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == abrirMenu) {
				abrir();
			} else if (e.getSource().equals(guardarMenu)) {
				guardar();
			} else if (e.getSource().equals(salirMenu)) {
				salir();
			} else if (e.getSource().equals(catalogoMenu)) {
				abrirCatalogo();
			} else if (e.getSource().equals(consultasMenu)) {
				abrirConsultas();
			} else if (e.getSource().equals(acercaDeMenu)) {
				mostrarAcercaDe();
			}
		}
	}

	private void configurarMenus() {
		// Menú de Archivo
		archivoMenu = new JMenu("Archivo");
		archivoMenu.setIcon(new ImageIcon(getClass().getResource("/gatitopedia/imagenes/archivo.png")));
		archivoMenu.setMnemonic(KeyEvent.VK_A);
		archivoMenu.setToolTipText("Accede a las opciones de archivo");

		abrirMenu = new JMenuItem("Abrir");
		abrirMenu.setIcon(new ImageIcon(getClass().getResource("/gatitopedia/imagenes/abrir.png")));
		abrirMenu.setMnemonic(KeyEvent.VK_A);
		abrirMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		abrirMenu.setToolTipText("Abrir un archivo");
		abrirMenu.addActionListener(new MenuActionListener());

		guardarMenu = new JMenuItem("Guardar");
		guardarMenu.setIcon(new ImageIcon(getClass().getResource("/gatitopedia/imagenes/guardar.png")));
		guardarMenu.setMnemonic(KeyEvent.VK_G);
		guardarMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		guardarMenu.setToolTipText("Guardar el archivo actual");
		guardarMenu.addActionListener(new MenuActionListener());

		salirMenu = new JMenuItem("Salir");
		salirMenu.setIcon(new ImageIcon(getClass().getResource("/gatitopedia/imagenes/salir.png")));
		salirMenu.setMnemonic(KeyEvent.VK_I);
		salirMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
		salirMenu.setToolTipText("Salir de la aplicación");
		salirMenu.addActionListener(new MenuActionListener());

		archivoMenu.add(abrirMenu);
		archivoMenu.add(guardarMenu);
		archivoMenu.addSeparator();
		archivoMenu.add(salirMenu);

		// Operaciones Menú
		operacionesMenu = new JMenu("Operaciones");
		operacionesMenu.setIcon(new ImageIcon(getClass().getResource("/gatitopedia/imagenes/operaciones.png")));
		operacionesMenu.setMnemonic(KeyEvent.VK_O);
		operacionesMenu.setToolTipText("Accede al menú de operaciones");

		catalogoMenu = new JMenuItem("Catálogo");
		catalogoMenu.setIcon(new ImageIcon(getClass().getResource("/gatitopedia/imagenes/catalogo.png")));
		catalogoMenu.setMnemonic(KeyEvent.VK_C);
		catalogoMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
		catalogoMenu.setToolTipText("Abrir el catálogo de gatos");
		catalogoMenu.addActionListener(new MenuActionListener());

		consultasMenu = new JMenuItem("Consultas");
		consultasMenu.setIcon(new ImageIcon(getClass().getResource("/gatitopedia/imagenes/consultar.png")));
		consultasMenu.setMnemonic(KeyEvent.VK_O);
		consultasMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
		consultasMenu.setToolTipText("Realizar consultas sobre el catálogo");
		consultasMenu.addActionListener(new MenuActionListener());

		operacionesMenu.add(catalogoMenu);
		operacionesMenu.addSeparator();
		operacionesMenu.add(consultasMenu);

		// Ayuda Menú
		ayudaMenu = new JMenu("Ayuda");
		ayudaMenu.setIcon(new ImageIcon(getClass().getResource("/gatitopedia/imagenes/ayuda.png")));
		ayudaMenu.setMnemonic(KeyEvent.VK_Y);
		ayudaMenu.setToolTipText("Acceso al menú de ayuda y créditos del sistema");

		acercaDeMenu = new JMenuItem("Acerca de...");
		acercaDeMenu.setIcon(new ImageIcon(getClass().getResource("/gatitopedia/imagenes/acercade.png")));
		acercaDeMenu.setMnemonic(KeyEvent.VK_A);
		acercaDeMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		acercaDeMenu.setToolTipText("Mostrar información acerca de la aplicación");
		acercaDeMenu.addActionListener(new MenuActionListener());

		ayudaMenu.add(acercaDeMenu);
	}

	private void configurarBarraMenu() {
		// Creación de la barra de menú
		barraMenu = new JMenuBar();
		barraMenu.add(archivoMenu);
		barraMenu.add(operacionesMenu);
		barraMenu.add(ayudaMenu);
		barraMenu.setBackground(Color.CYAN);
		this.setJMenuBar(barraMenu);
	}

	// Métodos de acción vinculados a los elementos de menú
	private void abrir() {
		JOptionPane.showMessageDialog(this, "Acción 'Abrir' ejecutada.", "Información",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void guardar() {
		JOptionPane.showMessageDialog(this, "Acción 'Guardar' ejecutada.", "Información",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void salir() {
		System.exit(0);
	}

	private void abrirCatalogo() {
		new DialogoGatitopedia(this);
	}

	private void abrirConsultas() {
		JOptionPane.showMessageDialog(this, "Acción 'Abrir Consultas' ejecutada.", "Información",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void mostrarAcercaDe() {
		JOptionPane.showMessageDialog(this,
				"Catálogo de gatos" + "\n\n" + "Realizada por:" + "\nJosé Enrique González Sánchez" + "\n\n"
						+ "Derechos reservados UMAR " + '\u00A9' + " 2024",
				"Acerca de... Catálogo de gatos", JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(getClass().getResource("/gatitopedia/imagenes/logo.png")));
	}
}
