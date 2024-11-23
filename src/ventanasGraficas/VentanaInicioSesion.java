package ventanasGraficas;
import javax.swing.*;

import domain.Usuario;
import persistence.GestorUsuariosBD;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class VentanaInicioSesion extends JFrame {
	private static final long serialVersionUID = 1L;
	 private ArrayList<Usuario> usuariosRegistrados = new ArrayList<>();
	 private ArrayList<Usuario> loginUsers = new ArrayList<>();
	 private GestorUsuariosBD gestorUsuarios = new GestorUsuariosBD();

	public VentanaInicioSesion() {
		// Configuración del JFrame
	    setTitle("Inicio de Sesión / Registro");
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setSize(500, 500);
		setLocationRelativeTo(null);

	    // Inicialización de componentes
	    JTabbedPane tabbedPane = new JTabbedPane();
	    
	    // Titulos
	    JLabel titulo = new JLabel("¡Bienvenid@ a Deusto Coffee!", JLabel.CENTER);
	    titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo.setForeground(new Color(139, 69, 19));
        
        JLabel titulo2 = new JLabel("¡Bienvenid@ a Deusto Coffee!", JLabel.CENTER);
        titulo2.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo2.setForeground(new Color(139, 69, 19));
	    
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
	    
	    imgPanel.add(titulo, BorderLayout.NORTH);
	    imgPanel.add(label, BorderLayout.CENTER);

	    panelRegistro.add(new JLabel("Usuario:"));
	    panelRegistro.add(usuarioRegistroField);
	    panelRegistro.add(new JLabel("Contraseña:"));
	    panelRegistro.add(contraseñaRegistroField);
	    panelRegistro.add(botonRegistrar);
	   	    
	    imgPanel.add(panelRegistro, BorderLayout.SOUTH);
	    
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
	    
	    img2Panel.add(titulo2, BorderLayout.NORTH);
	    img2Panel.add(label2, BorderLayout.CENTER);
	    img2Panel.add(panelLogIn, BorderLayout.SOUTH);
	    tabbedPane.addTab("Inicio De Sesión", img2Panel);
	    add(tabbedPane);
	    
	     // Action Listener para el botón de registro
	     botonRegistrar.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 Usuario usuario = new Usuario();
	        	 usuario.setNombre(usuarioRegistroField.getText());
	        	 usuario.setContrasena(new String(contraseñaRegistroField.getPassword()));
	        	 usuariosRegistrados.add(usuario);
	        	 gestorUsuarios.insertarDatos(usuario);
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
	        	 
	             usuarioLogInField.setText("");
	             contraseñaLogInField.setText("");{
	             if (gestorUsuarios.existeUsuario(usuario)) {
	            	 JOptionPane.showMessageDialog(null, "¡Bienvenido " + usuario.getNombre() + "!");
	            	 dispose();
	            	 new VentanaPrincipal().setVisible(true);
	             } else {
	            	 JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
	             }
	         }
	         }});
	    }
	
	public static void main(String[] args) {
		VentanaInicioSesion ventanaInicioSesion = new VentanaInicioSesion();
		ventanaInicioSesion.setVisible(true);
	}
}
