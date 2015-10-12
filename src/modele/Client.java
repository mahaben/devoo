package modele;

public class Client {
	private int id;
	private String nom;
	private String contact;
	private int adresse;
	
	public Client()
	{
		
	}
	public int getAdresse() {
		return adresse;
	}
	public void setAdresse(int adresse) {
		this.adresse = adresse;
	}
	public Client(int id, String nom, String contact) {
		super();
		this.id = id;
		this.nom = nom;
		this.contact = contact;
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

}
