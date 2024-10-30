package ventanasGraficas;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PanelInventario extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public PanelInventario() {
	}
	
	public static void main(String[] args) {
		PanelInventario ventanaInventario = new PanelInventario();
		ventanaInventario.setVisible(true);
	}
}
