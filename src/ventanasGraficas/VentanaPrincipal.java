package ventanasGraficas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class VentanaPrincipal extends JFrame{
	/**
	 * Esta es la ventan principal en la que tienen lugar la mayoría de las interacciones con el programa.
	 * En esta se incorporan los distintos paneles como las distintas cuentas, el inventario o la ventana de 
	 * productos.
	 */
	
	//Atributos
	private JPanel pBotones;
	private JScrollPane scrollBotones;
	private ArrayList<JButton> botonesLaterales;
	private JButton btnProductos, btnInventario;
	
	public VentanaPrincipal(){
		super();
		setTitle("DEUSTOCOFFEE");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//Panel oeste
		pBotones = new JPanel();
		btnProductos = new JButton("PRODUCTOS");
		btnInventario = new JButton("INVENTARIO");
		
		botonesLaterales = new ArrayList<JButton>();
		botonesLaterales.add(btnProductos);
		botonesLaterales.add(btnInventario);
		cargarBotones(botonesLaterales);
		
		scrollBotones = new JScrollPane(pBotones);
		getContentPane().add(scrollBotones, BorderLayout.WEST);
		
		setVisible(true);
	}
	
	private void cargarBotones(ArrayList<JButton> botonesLaterales) {
		/**
		 * Este metodo coge los el todos los botones que hay y los mete todos en el panel de botones al lateral izquierdo de la pantalla.
		 */
		pBotones.setLayout(new GridLayout(botonesLaterales.size(), 1));
		
		for(int i=0; i<botonesLaterales.size(); i++) {
			pBotones.add(botonesLaterales.get(i));
		}
		repaint();
	}
	
	public static void main(String[] args) {
		VentanaPrincipal v = new VentanaPrincipal();
	}
	
}
