package domain;

import java.util.Objects;

public class Usuario {
	private static int idCounter = -1;
	private int id;
	protected String nombre;
	protected String apellidos;
	protected String email;
	protected String contrasena;
	protected int telefono;
	
	
	// Constructor con argumentos
	public Usuario(String nombre, String apellidos, String email, String contrasena, int telefono) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.contrasena = contrasena;
		this.telefono = telefono;
		this.id = ++idCounter;
	}
	// Constructor sin argumentos
	public Usuario() {
		super();
		this.nombre = "";
		this.apellidos = "";
		this.email = "";
		this.contrasena = "";
		this.telefono = 0;
	}
	// Constructor copia
	public Usuario(Usuario otro) {
		super();
		this.nombre = otro.nombre;
		this.apellidos = otro.apellidos;
		this.email = otro.email;
		this.contrasena = otro.contrasena;
		this.telefono = otro.telefono;
	}
	// Getters & Setters
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(apellidos, other.apellidos) && Objects.equals(contrasena, other.contrasena)
				&& Objects.equals(email, other.email) && id == other.id && Objects.equals(nombre, other.nombre)
				&& telefono == other.telefono;
	}
	@Override
	public String toString() {
		return "Datos del usuario con id "+id + ": nombre=" + nombre + ", apellidos=" + apellidos + ", email=" + email
				+ ", contraseña=" + contrasena + ", telefono=" + telefono;
	}
}
	
	