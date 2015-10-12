package modele;

public class Livreur {
	private int id;
	private String nom;
	private String contact;
	private int x; 
	private int y;
	public Livreur(int id, String nom, String contact, int x, int y) {
		super();
		this.id = id;
		this.nom = nom;
		this.contact = contact;
		this.x = x;
		this.y = y;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
