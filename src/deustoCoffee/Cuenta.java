package deustoCoffee;

public class Cuenta {
	private int id;

	public Cuenta(int id) {
		this.id = id;
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
