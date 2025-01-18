package main;

import db.GestorBD;
import domain.Usuario;

public class Main {
	public static void main(String[] args) {
		 
	        GestorBD gestorBD = new GestorBD();
			gestorBD.crearBD();
	        Usuario usuario1 = new Usuario("Paula", "Jimenez", "paula.j.urzanqui@opendeusto.es", "miContrasena123", 612345678);
	        Usuario usuario2 = new Usuario("Sergio", "Jimenez", "sergio.jimenez@opendeusto.es", "miContrasena456", 693629127);
	        Usuario usuario3 = new Usuario("Lucas", "Echeverria", "lucas.echeverria@opendeusto.es", "miContrasena890", 632984025);
	        Usuario usuario4 = new Usuario("Pedro", "Lopez", "pedrolopez123@opendeusto.es", "miContrasena148", 608532764);
	        Usuario usuario5 = new Usuario("Marta", "Perez", "martaperez843@opendeusto.es", "miContrasena073", 603741648);
	        gestorBD.insertarUsuario(usuario1);
	        gestorBD.insertarUsuario(usuario2);
	        gestorBD.insertarUsuario(usuario3);
	        gestorBD.insertarUsuario(usuario4);
	        gestorBD.insertarUsuario(usuario5);
	        
	        System.out.println("Lista de usuarios:");
	        for (Usuario usuario : gestorBD.obtenerUsuarios()) {
	            System.out.println(usuario.toString());
	        }
	        
	        // Borrar todos los usuarios
	        //gestorUsuarios.borrarDatos();

	}
}
