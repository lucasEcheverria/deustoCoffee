package ventanasGraficas;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PanelInventario extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JPanel pSur;
	private JButton btnAniadir, btnBorrar, btnDetalles;
	
	public PanelInventario() {
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
