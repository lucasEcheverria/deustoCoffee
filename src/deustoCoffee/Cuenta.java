package deustoCoffee;

import java.util.HashMap;

public class Cuenta {
	private int id;
	protected HashMap<Integer, Integer> productos; //Id del producto es la clave, y el que contiene es la cantidad.

	public Cuenta(int id) {
		this.id = id;
		productos = new HashMap<Integer, Integer>();
	}
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}

	public boolean equals(Cuenta c) {
		return this.id == c.getId();
	}
	
}
