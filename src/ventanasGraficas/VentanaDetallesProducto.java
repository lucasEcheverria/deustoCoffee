package ventanasGraficas;


import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import deustoCoffee.Producto;

public class VentanaDetallesProducto extends JFrame{
	/**
	 * Esta es una vetana que muestra los detalles de un producto y puede editar detalles como la descripción.
	 */
	private Producto producto;
	private JLabel lblCantidad, lblPrecio, lblDescripcion;
	private JPanel pInfo, pDescripcion;
	private JScrollPane scrollDescripcion;
	private JTextArea txtDescripcion;
	private VentanaPrincipal ventana;

	public VentanaDetallesProducto(Producto producto, VentanaPrincipal ventana) {
		super();
		this.producto = producto;
		this.ventana = ventana;
		
		setSize(200, 200);
		setLocationRelativeTo(null);
		setTitle(producto.getNombre());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setLayout(new GridLayout(2,1));
	
		//Panel superior
		pInfo = new JPanel();
		pInfo.setLayout(new GridLayout(3, 1));
		lblCantidad = new JLabel(String.format("Cantidad: %d", producto.getCantidad()));
		lblPrecio = new JLabel(String.format("Precio: %.2f", producto.getPrecio()));
		lblDescripcion = new JLabel("Descripción: ");
		pInfo.add(lblCantidad);
		pInfo.add(lblPrecio);
		pInfo.add(lblDescripcion);
		
		getContentPane().add(pInfo);
		
		//txt descripción
		pDescripcion = new JPanel();
		pDescripcion.setLayout(new GridLayout(1,1));
		txtDescripcion = new JTextArea();
		txtDescripcion.setText(producto.getDescripcion());
		Document doc = txtDescripcion.getDocument();
		doc.addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				ventana.productos.get(producto.getIdProducto()).setDescripcion(txtDescripcion.getText());
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				ventana.productos.get(producto.getIdProducto()).setDescripcion(txtDescripcion.getText());				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		scrollDescripcion = new JScrollPane(txtDescripcion);
		pDescripcion.add(scrollDescripcion);
		
		getContentPane().add(pDescripcion);
		
		
		setVisible(true);
		
	}

}
