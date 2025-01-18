package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import domain.Producto;
import domain.Usuario;

public class GestorBD {
	/**
	 * Esta clase gestionará las conexiones con la base de datos
	 */
	private static final String DRIVER_NAME = "org.sqlite.JDBC";
    private static final String DATABASE_FILE = "db/productos.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:" + DATABASE_FILE;
    private Connection conn;
    

    public GestorBD() {
		try {
            Class.forName(DRIVER_NAME);
            conn = DriverManager.getConnection(CONNECTION_STRING);
        } catch (ClassNotFoundException ex) {
            System.err.println("* Error al cargar el driver: " + ex.getMessage());
        } catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void setConn() {
    	try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
    	}catch (SQLException e) {
			e.printStackTrace();
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
    
    public void crearBD() {
    	// Crear tabla PRODUCTO
        String sqlProducto = """
            CREATE TABLE IF NOT EXISTS PRODUCTO (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                NAME TEXT NOT NULL,
                CANTIDAD INTEGER NOT NULL,
                PRECIO FLOAT NOT NULL,
                RUTA TEXT NOT NULL,
                TIPO INTEGER NOT NULL,
                DESCRIPCION TEXT,
                FOREIGN KEY (TIPO) REFERENCES TIPO(ID) ON DELETE CASCADE ON UPDATE CASCADE
            );
        """;

        // Crear tabla TIPO
        String sqlTipo = """
            CREATE TABLE IF NOT EXISTS TIPO (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                NAME TEXT NOT NULL
            );
        """;

        // Crear tabla USUARIO
        String sqlUsuario = """
            CREATE TABLE IF NOT EXISTS USUARIO (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                NAME TEXT NOT NULL,
                SURNAME TEXT NOT NULL,
                EMAIL TEXT NOT NULL,
                PASSWORD TEXT NOT NULL,
                PHONE INTEGER NOT NULL
            );
        """;

        try {
            Statement stmt = conn.createStatement();

            stmt.executeUpdate(sqlProducto);
            stmt.executeUpdate(sqlTipo);
            stmt.executeUpdate(sqlUsuario);

            stmt.close();

            System.out.println("- Tablas creadas o ya existentes");
        } catch (SQLException e) {
            System.err.println("* Error al crear las tablas: " + e.getMessage());
        }
    }

    
    public void insertarProducto(Producto p) {
    	String sql = "INSERT INTO PRODUCTO (ID, NAME, CANTIDAD, PRECIO, RUTA, TIPO, DESCRIPCION) VALUES (?,?,?,?,?,?,?)";
    	try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, p.getIdProducto());
			pstmt.setInt(3, p.getCantidad());
			pstmt.setString(2, p.getNombre());
			pstmt.setString(5, p.getIcon().toString());
			pstmt.setString(6, p.getTipo());
			pstmt.setString(7, p.getDescripcion());
			pstmt.setFloat(4, (float) p.getPrecio());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
    
    public void insertarTipo(String tipo) {
    	String sql = "INSERT INTO TIPO (NAME) VALUES (?)";
    	try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tipo);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
    
    public void actualizarProducto(Producto p) {
    	String sql = "UPDATE PRODUCTO SET CANTIDAD = ?, PRECIO = ?, DESCRIPCION = ? WHERE ID = ?";
    	try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,  p.getCantidad());
			pstmt.setFloat(2, (float) p.getPrecio());
			pstmt.setString(3, p.getDescripcion());
			pstmt.setInt(4, p.getIdProducto());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void borrarProducto(int id) {
    	String sql = "DELETE FROM PRODUCTO WHERE ID = ?";
    	try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public HashMap<Integer, Producto> descargarProductos(){
    	HashMap<Integer,Producto> productos = new HashMap<Integer, Producto>();
    	
    	String sql = "SELECT * FROM PRODUCTO";
    	try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Producto p = new Producto(rs.getString("NAME"), rs.getString("TIPO"),rs.getString("DESCRIPCION"),rs.getDouble("PRECIO"), rs.getInt("ID"), rs.getInt("CANTIDAD"), rs.getString("RUTA"));
				productos.put(p.getIdProducto(), p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return productos;
    }
    
    public List<String> descargarTipos(){
    	ArrayList<String> tipos = new ArrayList<String>();
    	
    	String sql = "SELECT * FROM TIPO";
    	try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				tipos.add(rs.getString("NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return tipos;
    }
    
    
    // Validar email
    private boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$"; //ChatGPT
        return Pattern.matches(regex, email); //ChatGPT
    }

    // Validar teléfono
    private boolean validarTelefono(int telefono) {
        String telefonoStr = String.valueOf(telefono);
        return telefonoStr.length() == 9;
    }

    // Comprobar si un usuario existe
 	public boolean existeUsuario(Usuario usuario) {
 		String sql = "SELECT * FROM USUARIO WHERE EMAIL = ?";
 		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
 				PreparedStatement pstmt = con.prepareStatement(sql)) {
 			pstmt.setString(1, usuario.getEmail().toLowerCase().trim());
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
   
    // Insertar un usuario
    public boolean insertarUsuario(Usuario u) {
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
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM USUARIO";
        try (Statement stmt = conn.createStatement();
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
        } catch (SQLException e) {
            System.err.println("* Error al obtener usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    // Actualizar un usuario
    public void actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE USUARIO SET NAME = ?, SURNAME = ?, EMAIL = ?, PASSWORD = ?, PHONE = ? WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellidos());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getContrasena());
            pstmt.setInt(5, usuario.getTelefono());
            pstmt.setInt(6, usuario.getId());
            pstmt.executeUpdate();
            System.out.println("- Usuario con ID " + usuario.getId() + " actualizado");
        } catch (SQLException e) {
            System.err.println("* Error al actualizar usuario: " + e.getMessage());
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

	// Iniciar sesion
	public boolean iniciarSesion(Usuario usuario) {
		String sql = "SELECT * FROM USUARIO WHERE EMAIL = ? AND PASSWORD = ?";
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, usuario.getEmail().toLowerCase().trim());
			pstmt.setString(2, usuario.getContrasena());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println("¡Bienvenido " + usuario.getEmail() + "!");
				return true;
			} else {
				System.out.println("El usuario no existe");
				return false;
			}
		} catch (Exception ex) {
			System.err.println("* Error al iniciar sesión: " + ex.getMessage());
			return false;
		}
	}


    // Borrar todos los usuarios
    public void borrarDatos() {
        ejecutarSQL("DELETE FROM USUARIO");
        System.out.println("- Todos los registros borrados");
    }
    
    public void cerrarBD() {
    	try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
}
