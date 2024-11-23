package domain;

import java.util.Objects;

public class Usuario {
	protected String nombre;
	protected String apellidos;
	protected String email;
	protected String contrasena;
	protected int telefono;
	protected int idUsuario;
	
	// Constructor con argumentos
	public Usuario(String nombre, String apellidos, String email, String contrasena, int telefono, int idUsuario) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.contrasena = contrasena;
		this.telefono = telefono;
		this.idUsuario = idUsuario;
	}
	// Constructor sin argumentos
	public Usuario() {
		super();
		this.nombre = "";
		this.apellidos = "";
		this.email = "";
		this.contrasena = "";
		this.telefono = 0;
		this.idUsuario = 0;
	}
	// Constructor copia
	public Usuario(Usuario otro) {
		super();
		this.nombre = otro.nombre;
		this.apellidos = otro.apellidos;
		this.email = otro.email;
		this.contrasena = otro.contrasena;
		this.telefono = otro.telefono;
		this.idUsuario = otro.idUsuario;
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
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	// HashCode & Equals
	@Override
	public int hashCode() {
		return Objects.hash(apellidos, contrasena, email, idUsuario, nombre, telefono);
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
				&& Objects.equals(email, other.email) && idUsuario == other.idUsuario
				&& Objects.equals(nombre, other.nombre) && telefono == other.telefono;
	}
	
	// toString
	@Override
	public String toString() {
		return "Datos del usuario= [nombre:" + nombre + ", apellidos:" + apellidos + ", email:" + email + ", contraseña:"
				+ contrasena + ", teléfono:" + telefono + ", id del Usuario:" + idUsuario + "]";
	}
		
}
