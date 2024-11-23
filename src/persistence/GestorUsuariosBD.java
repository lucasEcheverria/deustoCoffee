package persistence;

import java.nio.file.Files;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import domain.Usuario;

public class GestorUsuariosBD {
	private static final String DRIVER_NAME = "org.sqlite.JDBC";
    private static final String DATABASE_FILE = "db/userDatabase.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:" + DATABASE_FILE;

	public GestorUsuariosBD() {
		try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException ex) {
            System.err.println("* Error al cargar el driver: " + ex.getMessage());
        }
    }

    // Método genérico para ejecutar sentencias SQL sin resultados (CREATE, UPDATE, DELETE)
    private void ejecutarSQL(String sql) {
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (Exception ex) {
            System.err.println("* Error ejecutando SQL: " + ex.getMessage());
        }
    }

    // Crear base de datos y tabla USUARIO
    public void crearBBDD() {
        String sql = """
            CREATE TABLE IF NOT EXISTS USUARIO (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                NAME TEXT NOT NULL,
                SURNAME TEXT NOT NULL,
                EMAIL TEXT NOT NULL,
                PASSWORD TEXT NOT NULL,
                PHONE INTEGER NOT NULL
            );
        """;
        ejecutarSQL(sql);
        System.out.println("- Tabla USUARIO creada o ya existente");
    }

    // Borrar tabla USUARIO y archivo de base de datos
    public void borrarBBDD() {
        ejecutarSQL("DROP TABLE IF EXISTS USUARIO");
        try {
            Files.deleteIfExists(Paths.get(DATABASE_FILE));
            System.out.println("- Archivo de base de datos eliminado");
        } catch (Exception ex) {
            System.err.println("* Error al eliminar archivo de la BBDD: " + ex.getMessage());
        }
    }
    // Método para validar email ----- CHATGPT
    private boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$"; // ChatGPT
		if (Pattern.matches(regex, email)) /*ChatGPT*/ {
			return true;
		} else {
			return false;
		}
    }

    // Método para validar teléfono 
    private boolean validarTelefono(int telefono) {
        String telefonoStr = String.valueOf(telefono); // ChatGPT 
        if (telefonoStr.length() == 9) {
        	return true;
        } else {
        	return false;
        }
    }

    // Insertar usuario
    public boolean insertarDatos(Usuario u) {
    	boolean resultado = false;
        String sqlTemplate = "INSERT INTO USUARIO (NAME, SURNAME, EMAIL, PASSWORD, PHONE) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
        PreparedStatement pstmt = con.prepareStatement(sqlTemplate)) {
			if (existeUsuario(u)) {
				System.err.println("El usuario ya existe");
			} else if (validarEmail(u.getEmail()) && validarTelefono(u.getTelefono()) && 
				u.getNombre() != null && u.getApellidos() != null && u.getContrasena() != null) {
				pstmt.setString(1, u.getNombre());
				pstmt.setString(2, u.getApellidos());
				pstmt.setString(3, u.getEmail());
				pstmt.setString(4, u.getContrasena());
				pstmt.setInt(5, u.getTelefono());
				pstmt.executeUpdate();
				System.out.println("Usuario insertado correctamente");
				resultado = true;
			} else {
				System.err.println("Los datos son incorrectos. Usuario no insertado");
			}
		} catch (Exception ex) {
			System.err.println("* Error al insertar datos: " + ex.getMessage());
        }
		return resultado;
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerDatos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM USUARIO";
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
        	while (rs.next()) {
        		Usuario usuario = new Usuario(
        			rs.getString("NAME"),
            		rs.getString("SURNAME"),
            		rs.getString("EMAIL"),
            		rs.getString("PASSWORD"),
       				rs.getInt("PHONE")
            );
        	usuario.setId(rs.getInt("ID"));
            usuarios.add(usuario);
        	}
        } catch (Exception ex) {
            System.err.println("* Error al obtener datos: " + ex.getMessage());
        }
        return usuarios;
    }

    // Comprobar si un usuario existe
	public boolean existeUsuario(Usuario usuario) {
		String sql = "SELECT * FROM USUARIO WHERE EMAIL = ? AND PASSWORD = ?";
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, usuario.getEmail().toLowerCase().trim());
			pstmt.setString(2, usuario.getContrasena().trim());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println("El usuario ya existe");
				return true;
			} else {
				System.out.println("El usuario no existe");
				return false;
			}
		} catch (Exception ex) {
			System.err.println("* Error al comprobar usuario: " + ex.getMessage());
			return false;
		}
	}
    
    // Borrar un usuario
	public void borrarUsuario(Usuario usuario) {
		int id = usuario.getId();
		String sql = "DELETE FROM USUARIO WHERE ID = ?";
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			System.out.println("- Usuario con ID " + id + " borrado");
		} catch (Exception ex) {
			System.err.println("* Error al borrar datos: " + ex.getMessage());
		}
	}
    // Borrar todos los usuarios
    public void borrarDatos() {
        ejecutarSQL("DELETE FROM USUARIO");
        System.out.println("- Todos los registros borrados");
    }
    
}
