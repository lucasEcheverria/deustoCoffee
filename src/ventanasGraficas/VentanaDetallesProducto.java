package ventanasGraficas;

import java.awt.HeadlessException;

import javax.swing.JFrame;

import deustoCoffee.Producto;

public class VentanaDetallesProducto extends JFrame{
	private Producto producto;

	public VentanaDetallesProducto(Producto producto) {
		super();
		this.producto = producto;
		
		setSize(200, 200);
		setLocationRelativeTo(null);
		setTitle(producto.getNombre());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setVisible(true);
		
	}

}
