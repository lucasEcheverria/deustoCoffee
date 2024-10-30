package ventanasGraficas;
import javax.swing.JFrame;
import javax.swing.JPanel;

import deustoCoffee.Cuenta;

public class PanelCuenta extends JPanel  {
	private static final long serialVersionUID = 1L;
	
	private Cuenta cuenta;

	public PanelCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	
}
