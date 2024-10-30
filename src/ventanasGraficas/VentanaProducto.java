package ventanasGraficas;
import javax.swing.JFrame;

public class VentanaProducto extends JFrame  {
	private static final long serialVersionUID = 1L;
	
	public VentanaProducto() {
		super("Producto");
		this.setSize(300, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
	}
	
	public static void main(String[] args) {
		VentanaProducto ventanaProducto = new VentanaProducto();
		ventanaProducto.setVisible(true);
	}
	
}
