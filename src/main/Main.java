package main;

import domain.Usuario;
import persistence.GestorUsuariosBD;

public class Main {
	public static void main(String[] args) {
		 GestorUsuariosBD gestorUsuarios = new GestorUsuariosBD();

	        gestorUsuarios.crearBBDD();
	        Usuario usuario1 = new Usuario("Paula", "Jimenez", "paula.j.urzanqui@opendeusto.es", "miContrasena123", 612345678);
	        Usuario usuario2 = new Usuario("Sergio", "Jimenez", "sergio.jimenez@opendeusto.es", "miContrasena456", 693629127);
	        gestorUsuarios.insertarDatos(usuario1);
	        gestorUsuarios.insertarDatos(usuario2);


	        System.out.println("Lista de usuarios:");
	        for (Usuario usuario : gestorUsuarios.obtenerDatos()) {
	            System.out.println(usuario.toString());
	        }

	        // Borrar todos los usuarios
	        // gestorUsuarios.borrarDatos();

	        // Borrar la base de datos
	        // gestorUsuarios.borrarBBDD();
	}
}
