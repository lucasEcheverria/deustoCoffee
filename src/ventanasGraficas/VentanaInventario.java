package ventanasGraficas;
import javax.swing.JFrame;

public class VentanaInventario extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public VentanaInventario() {
		super("Inventario");
		this.setSize(300, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
	}
	
	public static void main(String[] args) {
		VentanaInventario ventanaInventario = new VentanaInventario();
		ventanaInventario.setVisible(true);
	}
}
