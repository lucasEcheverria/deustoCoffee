package ventanasGraficas;
import javax.swing.JFrame;

public class VentanaInicioSesion extends JFrame {
	private static final long serialVersionUID = 1L;

	public VentanaInicioSesion() {
		super("Inicio de Sesión");
		this.setSize(300, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
	}
	
	public static void main(String[] args) {
		VentanaInicioSesion ventanaInicioSesion = new VentanaInicioSesion();
		ventanaInicioSesion.setVisible(true);
	}
}
