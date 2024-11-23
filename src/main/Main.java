package main;

import domain.Usuario;
import persistence.GestorUsuariosBD;

public class Main {
	public static void main(String[] args) {
		 GestorUsuariosBD gestorUsuarios = new GestorUsuariosBD();

	        gestorUsuarios.crearBBDD();
	        Usuario usuario1 = new Usuario("Paula", "Jimenez", "paula.j.urzanqui@opendeusto.es", "miContrasena123", 612345678);
	        Usuario usuario2 = new Usuario("Sergio", "Jimenez", "sergio.jimenez@opendeusto.es", "miContrasena456", 693629127);
	        Usuario usuario3 = new Usuario("Lucas", "Echeverria", "lucas.echeverria@opendeusto.es", "miContrasena890", 632984025);
	        Usuario usuario4 = new Usuario("Pedro", "Lopez", "pedrolopez123@opendeusto.es", "miContrasena148", 608532764);
	        Usuario usuario5 = new Usuario("Marta", "Perez", "martaperez843@opendeusto.es", "miContrasena073", 603741648);
	        gestorUsuarios.insertarDatos(usuario1);
	        gestorUsuarios.insertarDatos(usuario2);
	        gestorUsuarios.insertarDatos(usuario3);
	        gestorUsuarios.insertarDatos(usuario4);
	        gestorUsuarios.insertarDatos(usuario5);
	        
	        System.out.println("Lista de usuarios:");
	        for (Usuario usuario : gestorUsuarios.obtenerDatos()) {
	            System.out.println(usuario.toString());
	        }
	        
	        // Borrar todos los usuarios
	        //gestorUsuarios.borrarDatos();

	        // Borrar la base de datos
	        // gestorUsuarios.borrarBBDD();
	}
}
