package main;

import java.util.ArrayList;
import java.util.List;

import db.GestorBD;
import domain.Producto;
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

        // Crear lista de productos
        List<Producto> productos = new ArrayList<>();
        Producto p1 = new Producto("Patatas", "Bebida", "Café bravas", 5, 1, 1, "resources/images/patatas.jpeg");
        Producto p2 = new Producto("Pinta", "Comida", "Pinta grande", 2.5, 2, 1, "resources/images/pinta.png");
        Producto p3 = new Producto("Coca Cola", "Bebida", "Coca cola grande", 3.0, 3, 1, "resources/images/coca-cola.jpg");
        productos.add(p1);
        productos.add(p2);
        productos.add(p3);

        
        double presupuesto = 30;

        // Encontrar combinaciones
        List<List<Producto>> combinaciones = new ArrayList<>();
        encontrarCombinaciones(productos, presupuesto, new ArrayList<>(), combinaciones);

        
        System.out.println("Mostrando todas las combinaciones posibles con un presupuesto de... " + presupuesto + "€:");
        for (List<Producto> combinacion : combinaciones) {
            System.out.println("Combinación: ");
            for (Producto producto : combinacion) {
                System.out.println("- " + producto.getNombre() + " (" + producto.getPrecio() + " €)");
            }
        }
    }

    public static void encontrarCombinaciones(List<Producto> productos, double presupuesto, List<Producto> combinacionActual, List<List<Producto>> combinaciones) {
        if (presupuesto < 0) {
            return;
        }

        if (presupuesto == 0) {
            combinaciones.add(new ArrayList<>(combinacionActual));
            return;
        }

        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            combinacionActual.add(producto);

            encontrarCombinaciones(productos, presupuesto - producto.getPrecio(), combinacionActual, combinaciones);

            combinacionActual.remove(combinacionActual.size() - 1);
        }
    }


}
