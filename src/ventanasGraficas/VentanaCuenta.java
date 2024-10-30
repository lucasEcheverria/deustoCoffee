package ventanasGraficas;
import javax.swing.JFrame;

public class VentanaCuenta extends JFrame  {
	private static final long serialVersionUID = 1L;

	public VentanaCuenta() {
		super("Cuenta");
		this.setSize(300, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
	}
	
	public static void main(String[] args) {
		VentanaCuenta ventanaCuenta = new VentanaCuenta();
		ventanaCuenta.setVisible(true);
	}
}
