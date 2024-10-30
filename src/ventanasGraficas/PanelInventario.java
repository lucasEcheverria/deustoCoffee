package ventanasGraficas;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PanelInventario extends JPanel {
	/**
	 * Este es el panel en el que saldra el inventario, en el cual también podremos añadir productos, eliminarlos y ver sus detalles.
	 * Además podremos añadir tipos por los que filtrar.
	 * Todos los datos que utiliza están almacenados en la ventana que le llama.
	 */
	private static final long serialVersionUID = 1L;
	
	private JFrame ventana;
	private JPanel pSur;
	private JButton btnAniadir, btnBorrar, btnDetalles;
	
	public PanelInventario(JFrame ventana) {
		this.ventana = ventana; //nos interesa saber la ventana porque ahi es donde tenemos los datos almacenados.
		
		this.setLayout(new BorderLayout());
		
		//Panel sur
		pSur = new JPanel();
		pSur.setLayout(new GridLayout(1, 3));
		btnAniadir = new JButton("AÑADIR");
		btnBorrar = new JButton("BORRAR");
		btnDetalles = new JButton("DETALLES");
		pSur.add(btnAniadir);
		pSur.add(btnBorrar);
		pSur.add(btnDetalles);
		
		this.add(pSur,BorderLayout.SOUTH);
		
	}
}
