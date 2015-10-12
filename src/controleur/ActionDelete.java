package controleur;

import java.util.List;

import modele.Graphe;
import modele.Livraison;
import Graphics.FenetreGraphe;
import Graphics.FenetreText;

public class ActionDelete extends Action{
	private int adresseLivraison; 
	private Livraison livraisonPrec;
	private Livraison livraisonSup;
	
	public ActionDelete(int adresseLivraison){
		this.adresseLivraison = adresseLivraison;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		Graphe.getInstance();
		Livraison livraison=Graphe.getLivraisonByIdIntersection(adresseLivraison);
		livraisonSup = livraison;
		for(int i=0;i< Graphe.getLivraisons().size();i++)
		{
			if ( Graphe.getLivraisons().get(i).equals(livraison))
			{
				if(i==0)
				{
					livraisonPrec=null;
				}
				else
					{ livraisonPrec= Graphe.getLivraisons().get(i-1);}
			}
		}
		
		Graphe.getInstance();
		
		Graphe.getLivraisons().remove(livraison);
		if ( Graphe.getCheminsordonne() !=null)
		{Graphe.getCheminsordonne().clear();}
		FenetreGraphe.getInstance().drawPlan();
		FenetreGraphe.getInstance().drawLivraisons();
		FenetreText.getInstance().drawTable();
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		Graphe.getInstance();
		Livraison livraison=livraisonSup;
		List<Livraison> livraisons= Graphe.getLivraisons();
		if(livraisonPrec != null){
		int j=0;
		for(int i=0;i<livraisons.size();i++)
		{
			if( livraisons.get(i).equals(livraisonPrec))
			{
				j=i; 
			}
		}
		
		Graphe.getInstance();
		Graphe.getLivraisons().add(j+1,livraison);
		}
		else
		{
			Graphe.getInstance();
			Graphe.getLivraisons().add(0,livraison);
		}

		
	}

}
