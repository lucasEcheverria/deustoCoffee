package ventanasGraficas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import domain.Cuenta;
import domain.Producto;

public class VentanaPrincipal extends JFrame{
	/**
	 * Esta es la ventan principal en la que tienen lugar la mayoría de las interacciones con el programa.
	 * En esta se incorporan los distintos paneles como las distintas cuentas, el inventario o la ventana de 
	 * productos.
	 */
	
	//Atributos
	protected JPanel pBotones, pCentral, p;
	private JScrollPane scrollBotones;
	private ArrayList<JButton> botonesLaterales;
	private JButton btnProductos, btnInventario;
	
	protected HashMap<Integer, Cuenta> cuentas;
	private int total;
	
	protected HashMap<Integer, Producto> productos;
	protected ArrayList<String> tipos;
	
	protected PanelProductos panelProductos;
	
	public VentanaPrincipal(){
		super();
		setTitle("DEUSTOCOFFEE");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				cerrarsesion();
			}
		});
		
		//Inicialización de parametros
		cuentas = new HashMap<Integer, Cuenta>();
		botonesLaterales = new ArrayList<JButton>();
		tipos = new ArrayList<String>();
		tipos.add("CERVEZA");
		tipos.add("PATATAS");
		productos = new HashMap<Integer, Producto>();
		Producto p1 = new Producto("Pinta", "CERVEZA", "Cerveza grande", 2, 1, 0, "img/pinta.png");
		p1.setIcon(new ImageIcon("img/pinta.png"));
		productos.put(p1.getIdProducto(), p1);
		
		total = 1;
		
		panelProductos = new PanelProductos(this);
		
		//Panel central
		pCentral = new PanelInventario(this);
		getContentPane().add(pCentral, BorderLayout.CENTER);
		
		//Panel oeste
		pBotones = new JPanel();
		
		btnProductos = new JButton("PRODUCTOS");
		btnProductos.addActionListener(e -> {
			getContentPane().remove(pCentral);
			pCentral = new PanelProductos(this);
			getContentPane().add(pCentral, BorderLayout.CENTER);
			pCentral.revalidate();
			pCentral.repaint();
			cargarBotones(botonesLaterales);
			panelProductos.cargarComboBox();
		});
		btnInventario = new JButton("INVENTARIO");
		btnInventario.addActionListener(e -> {
			getContentPane().remove(pCentral);
			pCentral = new PanelInventario(this);
			getContentPane().add(pCentral, BorderLayout.CENTER);
			pCentral.revalidate();
			pCentral.repaint();
		});
		JButton btnAniadir = new JButton("AÑADIR CUENTA");
		btnAniadir.addActionListener(e -> {
			Cuenta c = new Cuenta(total);
			total ++;
			cuentas.put(c.getId(), c);
			getContentPane().remove(pCentral);
			pCentral = new PanelProductos(this);
			getContentPane().add(pCentral, BorderLayout.CENTER);
			pCentral.revalidate();
			pCentral.repaint();
			cargarBotones(botonesLaterales);
			panelProductos.cargarComboBox();
			JOptionPane.showMessageDialog(this, "NUEVA CUENTA CREADA");
		});
		/*
		 * Los action listener de los botones hacen que al accionarlos se cambie el panel central por un nuevo panel,
		 * que este será del tipo nuevo que queremos.
		 */
		
		botonesLaterales.add(btnProductos);
		botonesLaterales.add(btnInventario);
		botonesLaterales.add(btnAniadir);
		cargarBotones(botonesLaterales);
		
		scrollBotones = new JScrollPane(pBotones);
		getContentPane().add(scrollBotones, BorderLayout.WEST);
		
		
		setVisible(true);
	}
	
	private int cerrarsesion() {
		if (JOptionPane.showConfirmDialog(this, "¿Estás seguro de que quieres cerrar sesión?", "Cerrar sesión",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			dispose();
		} else {}
		return 0;
	}

	private void cargarBotones(ArrayList<JButton> botonesLaterales) {
		/**
		 * Este metodo coge los el todos los botones que hay y los mete todos en el panel de botones al lateral izquierdo de la pantalla.
		 */
		pBotones.removeAll();
		pBotones.setLayout(new GridLayout(botonesLaterales.size(), 1));
		botonesLaterales.forEach(b -> {pBotones.add(b);});
		pBotones.revalidate();
		pBotones.repaint();
	}
	
	public static void main(String[] args) {
		VentanaPrincipal v = new VentanaPrincipal();
	}
	
}
