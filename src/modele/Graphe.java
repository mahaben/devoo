
package modele;


import java.util.List;
public class Graphe {

	private static List<Intersection> intersections;
	private static List<Livraison> livraisons;
	private static List<PlageHoraire> plagesHoraires;
	private static List<Chemin> cheminsordonne;
	private static Itineraire itineraire;
	public static List<Chemin> getCheminsordonne() {
		return cheminsordonne;
	}

	public static void setCheminsordonne(List<Chemin> cheminsordonne) {
		Graphe.cheminsordonne = cheminsordonne;
	}

	private static int adresseEntrepot=-1;

	private static Graphe INSTANCE=null;
	
	private Graphe()
	{
	
	} 
	
	public static Intersection getIntersectionById(int id)
	{
		for(Intersection intersection: intersections)
		{
			if( intersection.getId() == id) { return intersection;}
		}
		return null;
	}
	public static Livraison getLivraisonByIdIntersection(int idIntersection)
	{
		for(Livraison livraison: livraisons)
		{
			if( livraison.getIdIntersection() == idIntersection) { return livraison;}
		}
		return null;
	}
	public static Graphe getInstance()
	{			
		if (INSTANCE == null)
		{ 	INSTANCE = new Graphe();	
		}
		return INSTANCE;
	}
	
	public int getMaxIdLivraison()
	{
		int id_max=0;
		for(int i=0;i<livraisons.size();i++)
		{
			if ( livraisons.get(i).getId() > id_max)
			{
				id_max= livraisons.get(i).getId() ;
			}
		}
		return id_max;
	}
	
	
	
	
	public static List<Intersection> getIntersections() {
		return intersections;
	}

	public static void setIntersections(List<Intersection> intersections) {
		Graphe.intersections = intersections;
	}

	public static void initIntersections()
	{

		if (intersections != null )
		{
			
			for(Intersection intersection : intersections)
			{
				intersection.getTroncons().clear();
			}
		intersections.clear();
		
		}
		
		
		
	}
	public static void initChemins()
	{

		if (cheminsordonne != null )
		{
			
			
		cheminsordonne.clear();
		
		}
	}
	public static void initlivraisons()
	{
		if (livraisons != null )livraisons.clear();
		if( plagesHoraires != null )plagesHoraires.clear();
	}

	public static List<Livraison> getLivraisons() {
		return livraisons;
	}

	public static void setLivraisons(List<Livraison> livraisons) {
		Graphe.livraisons = livraisons;
	}
	public static List<PlageHoraire> getPlagesHoraires() {
		return plagesHoraires;
	}

	public static void setPlagesHoraires(List<PlageHoraire> plagesHoraires) {
		Graphe.plagesHoraires = plagesHoraires;
	}

	public static int getAdresseEntrepot() {
		return adresseEntrepot;
	}

	public static void setAdresseEntrepot(int adresseEntrepot) {
		Graphe.adresseEntrepot = adresseEntrepot;
	}

	public static Itineraire getItineraire() {
		return itineraire;
	}

	public static void setItineraire(Itineraire itineraire) {
		Graphe.itineraire = itineraire;
	}

	
	
	
}
