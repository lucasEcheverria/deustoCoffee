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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import deustoCoffee.Cuenta;
import deustoCoffee.Producto;

public class VentanaPrincipal extends JFrame{
	/**
	 * Esta es la ventan principal en la que tienen lugar la mayoría de las interacciones con el programa.
	 * En esta se incorporan los distintos paneles como las distintas cuentas, el inventario o la ventana de 
	 * productos.
	 */
	
	//Atributos
	private JPanel pBotones, pCentral;
	private JScrollPane scrollBotones;
	private ArrayList<JButton> botonesLaterales;
	private JButton btnProductos, btnInventario;
	
	private HashMap<Integer, Cuenta> cuentas;
	
	protected ArrayList<Producto> productos;
	protected ArrayList<String> tipos;
	
	public VentanaPrincipal(){
		super();
		setTitle("DEUSTOCOFFEE");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//Inicialización de parametros
		cuentas = new HashMap<Integer, Cuenta>();
		botonesLaterales = new ArrayList<JButton>();
		tipos = new ArrayList<String>();
		tipos.add("CERVEZA");
		productos = new ArrayList<Producto>();
		Producto p = new Producto("Pinta", "CERVEZA", "Cerveza grande", 2, 1, 0);
		productos.add(p);
		
		//Panel central
		pCentral = new PanelInventario(this);
		getContentPane().add(pCentral, BorderLayout.CENTER);
		
		//Panel oeste
		pBotones = new JPanel();
		
		btnProductos = new JButton("PRODUCTOS");
		btnProductos.addActionListener(e -> {
			getContentPane().remove(pCentral);
			pCentral = new PanelProductos();
			getContentPane().add(pCentral, BorderLayout.CENTER);
			pCentral.revalidate();
			pCentral.repaint();
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
			Cuenta c = new Cuenta(cuentas.size());
			cuentas.put(c.getId(), c);
			JButton btnCuenta = new JButton(String.format("Cuenta %d", c.getId()));
			btnCuenta.addActionListener(a -> {
				getContentPane().remove(pCentral);
				pCentral = new PanelCuenta(c);
				getContentPane().add(pCentral, BorderLayout.CENTER);
				pCentral.revalidate();
				pCentral.repaint();
			});
			botonesLaterales.add(btnCuenta);
			cargarBotones(botonesLaterales);
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
