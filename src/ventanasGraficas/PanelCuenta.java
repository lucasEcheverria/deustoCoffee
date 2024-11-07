package ventanasGraficas;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import deustoCoffee.Cuenta;

public class PanelCuenta extends JPanel  {
	/**
	 * Esta clase representa un panel que contiene una cuenta y la muestra completa con sus opciones administrativas.
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel pSur, pCentral;
	private JLabel lblTotal;
	private JButton btnImprimir;
	private VentanaPrincipal ventana;
	
	private Cuenta cuenta;

	public PanelCuenta(Cuenta cuenta, VentanaPrincipal ventana) {
		this.ventana = ventana;
		this.cuenta = cuenta;
		this.setLayout(new BorderLayout());
		
		//Panel Central
		pCentral = new JPanel();
		if(cuenta != null) {
			pCentral.setLayout(new GridLayout(cuenta.getProductos().size(), 1));
		
			for(Integer key : cuenta.getProductos().keySet()) {
				JLabel lbl = new JLabel(String.format("%s: %d || precio por unidad: %.2f€ || precio producto(s) %.2f €", ventana.productos.get(key).getNombre(), cuenta.getProductos().get(key),ventana.productos.get(key).getPrecio(), ventana.productos.get(key).getPrecio()* cuenta.getProductos().get(key)));
				pCentral.add(lbl);
			}
		}
		add(pCentral, BorderLayout.CENTER);
		
		//Panel Sur
		pSur = new JPanel();
		btnImprimir = new JButton("IMPRIMIR");
		btnImprimir.addActionListener(e -> {
			//TODO 
			//Generar fichero txt con información.
			//Eliminar cuenta y su boton.
		});
		pSur.add(btnImprimir);
		if(cuenta != null) {
			lblTotal = new JLabel(String.format("TOTAL: %.2f €", calcularTotal()));
			pSur.add(lblTotal);
		}else {
			lblTotal = new JLabel(String.format("TOTAL: %.2f €", 0.00));
			pSur.add(lblTotal);
		}
		
		add(pSur, BorderLayout.SOUTH);
	}
	
	private float calcularTotal() {
		float total = 0;
		for(Integer key : cuenta.getProductos().keySet()) {
			total += ventana.productos.get(key).getPrecio()* cuenta.getProductos().get(key);
		}
		return total;
	}
	
}
