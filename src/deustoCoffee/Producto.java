package deustoCoffee;

import java.util.Objects;

public class Producto {
	protected String nombre, tipo, descripcion;
	protected double precio;
	protected int idProducto, cantidad;
	
	// Constructor con argumentos
	public Producto(String nombre, String tipo, String descripcion, double precio, int idProducto) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.precio = precio;
		this.idProducto = idProducto;
	}
	
	// Constructor sin argumentos
	public Producto() {
		super();
		this.nombre = "";
		this.tipo = null;
		this.descripcion = "";
		this.precio = 0.0;
		this.idProducto = 0;
	}
	
	// Constructor copia
	public Producto(Producto otro) {
		super();
		this.nombre = otro.nombre;
		this.tipo = otro.tipo;
		this.descripcion = otro.descripcion;
		this.precio = otro.precio;
		this.idProducto = otro.idProducto;
	}

	// Getters & Setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public int hashCode() {
		return Objects.hash(descripcion, idProducto, nombre, precio, tipo);
	}

	// HashCode & Equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return Objects.equals(descripcion, other.descripcion) && idProducto == other.idProducto
				&& Objects.equals(nombre, other.nombre)
				&& Double.doubleToLongBits(precio) == Double.doubleToLongBits(other.precio) && tipo == other.tipo;
	}

	// toString
	@Override
	public String toString() {
		return "Detalles del Producto [nombre:" + nombre + ", tipo:" + tipo + ", descripción:" + descripcion + ", precio:" + precio
				+ ", id del Producto:" + idProducto + "]";
	}
	
}
