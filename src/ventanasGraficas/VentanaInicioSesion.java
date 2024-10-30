package ventanasGraficas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaInicioSesion extends JFrame {
	private static final long serialVersionUID = 1L;

	public VentanaInicioSesion() {
		super("Inicio de Sesión");
		this.setSize(900, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		// Crear paneles
		JPanel panelInicioSesion = new JPanel();
		JPanel panelRegistro = new JPanel();
		JPanel panelCampos = new JPanel();
		panelCampos.setLayout(new GridLayout(2, 2));
	
		// Crear componentes
		JLabel labelInicioSesion = new JLabel("Inicio de Sesión");
		JLabel labelRegistro = new JLabel("Registro");
		JLabel labelUsuario = new JLabel("Usuario:");
		JTextField campoUsuario = new JTextField(20);
		JLabel labelContrasena = new JLabel("Contraseña:");
		JTextField campoContrasena = new JTextField(20);
		
		// Configurar componentes
		panelCampos.add(labelUsuario);
		panelCampos.add(campoUsuario);
		panelCampos.add(labelContrasena);
		panelCampos.add(campoContrasena);
		panelInicioSesion.add(labelInicioSesion, BorderLayout.NORTH);
		panelInicioSesion.add(panelCampos, BorderLayout.SOUTH);
		
		
		panelRegistro.add(labelRegistro, BorderLayout.NORTH);
		
		// Añadir paneles a la ventana
		this.add(panelInicioSesion, BorderLayout.WEST);
	    this.add(panelRegistro, BorderLayout.EAST);
	}
		public static void main(String[] args) {
			VentanaInicioSesion ventanaInicioSesion = new VentanaInicioSesion();
			ventanaInicioSesion.setVisible(true);
		}
}
