package ventanasGraficas;
import javax.swing.*;

import deustoCoffee.Usuario;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class VentanaInicioSesion extends JFrame {
	private static final long serialVersionUID = 1L;
	 private ArrayList<Usuario> usuariosRegistrados = new ArrayList<>();
	 private ArrayList<Usuario> loginUsers = new ArrayList<>();

	public VentanaInicioSesion() {
		// Configuración del JFrame
	    setTitle("Inicio de Sesión / Registro");
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setSize(500, 500);
		setLocationRelativeTo(null);

	    // Inicialización de componentes
	    JTabbedPane tabbedPane = new JTabbedPane();
	    
	    // Añadir imagen
	    ImageIcon logo = new ImageIcon("img/logo.jpg");
	    JLabel label = new JLabel(logo);
	    ImageIcon logo2 = new ImageIcon("img/logo.jpg");
	    JLabel label2 = new JLabel(logo2);
	    
	    // Panel de Registro
	    JPanel imgPanel = new JPanel(new BorderLayout());
	    JPanel panelRegistro = new JPanel(new GridLayout(5, 1, 10, 10));
	    JTextField usuarioRegistroField = new JTextField();
	    JPasswordField contraseñaRegistroField = new JPasswordField();
	    JButton botonRegistrar = new JButton("Registrar");

	    imgPanel.add(label, BorderLayout.NORTH);

	    panelRegistro.add(new JLabel("Usuario:"));
	    panelRegistro.add(usuarioRegistroField);
	    panelRegistro.add(new JLabel("Contraseña:"));
	    panelRegistro.add(contraseñaRegistroField);
	    panelRegistro.add(botonRegistrar);
	    
	    imgPanel.add(panelRegistro, BorderLayout.CENTER);
	    tabbedPane.addTab("Registro", imgPanel);

	    // Panel de Inicio de Sesión
	    JPanel img2Panel = new JPanel(new BorderLayout());
	    JPanel panelLogIn = new JPanel(new GridLayout(5, 1, 10, 10));
	    JTextField usuarioLogInField = new JTextField();
	    JPasswordField contraseñaLogInField = new JPasswordField();
	    JButton botonLogIn = new JButton("Iniciar Sesión");

	    panelLogIn.add(new JLabel("Usuario:"));
	    panelLogIn.add(usuarioLogInField);
	    panelLogIn.add(new JLabel("Contraseña:"));
	    panelLogIn.add(contraseñaLogInField);
	    panelLogIn.add(botonLogIn);

	    img2Panel.add(label2, BorderLayout.NORTH);
	    img2Panel.add(panelLogIn, BorderLayout.CENTER);
	    tabbedPane.addTab("Inicio De Sesión", img2Panel);
	    add(tabbedPane);
	    
	     // Action Listener para el botón de registro
	     botonRegistrar.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 Usuario usuario = new Usuario();
	        	 usuario.setNombre(usuarioRegistroField.getText());
	        	 usuario.setContrasena(new String(contraseñaRegistroField.getPassword()));
	        	 usuariosRegistrados.add(usuario);
	             usuarioRegistroField.setText("");
	             contraseñaRegistroField.setText("");
	         }
	     });

	     // Action Listener para el botón de inicio de sesión
	     botonLogIn.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 Usuario usuario = new Usuario();
	        	 usuario.setNombre(usuarioLogInField.getText());
	        	 usuario.setContrasena(new String(contraseñaLogInField.getPassword()));
	             loginUsers.add(usuario);
	             usuarioLogInField.setText("");
	             contraseñaLogInField.setText("");
	         }
	     });
	    }
	
	public static void main(String[] args) {
		VentanaInicioSesion ventanaInicioSesion = new VentanaInicioSesion();
		ventanaInicioSesion.setVisible(true);
	}
}
