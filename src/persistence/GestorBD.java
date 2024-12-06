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

import domain.Producto;

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
    
    public void crearBD() {
        String sql = """
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
        try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        sql = """
	            CREATE TABLE IF NOT EXISTS TIPO (
	                ID INTEGER PRIMARY KEY AUTOINCREMENT,
	                NAME TEXT NOT NULL
	            );
	        """;
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
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
    
    public void cerrarBD() {
    	try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
}
