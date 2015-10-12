package controleur;

import java.util.List;

import Graphics.FenetreGraphe;
import Graphics.FenetreText;
import modele.Graphe;
import modele.Livraison;

public class ActionAdd extends Action {
	private Livraison livraison;
	private Livraison livraisonPrec;
	
	public ActionAdd(Livraison livraison, Livraison livraisonPrec)
	{
		this.livraison = livraison;
		this.livraisonPrec = livraisonPrec;
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
		Graphe.getInstance();
		List<Livraison> livraisons= Graphe.getLivraisons();
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

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		Graphe.getInstance();
		Graphe.getLivraisons().remove(livraison);
		
	}

}
