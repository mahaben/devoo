package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import modele.Chemin;
import modele.Graphe;
import modele.Intersection;
import modele.Troncon;

public class VueChemin extends Vue{
	
	private Chemin chemin;
	private static boolean dernierChemin=false;
	
	private  static Color[] couleurs={Color.CYAN,Color.GREEN,Color.ORANGE,Color.PINK,Color.MAGENTA,Color.yellow,Color.BLUE,Color.PINK};
	private static  Color couleur;
	public VueChemin(Chemin chemin)
	{
		this.chemin=chemin;
	}
	@Override
	public void dessiner(Graphics g) {
		
		Intersection start=chemin.getStart();
		Intersection target=chemin.getTarget();
		Graphe.getInstance();
	
        couleur=couleurs[Graphe.getLivraisonByIdIntersection(target.getId()).getPlageHoraire().getId()-1];
        
        List<Troncon> troncons=chemin.getTroncons();
            
		   for(Troncon troncon:troncons)
		   {
			  
			  VueTroncon vuetroncon=new VueTroncon(troncon);
			  vuetroncon.setCouleur(couleur);
			  vuetroncon.setDrawArrow(true);
			  if ( dernierChemin){ vuetroncon.setDernierchemin(true);}
			  vuetroncon.dessiner(g);
			  vuetroncon.setDrawArrow(false);
			  vuetroncon.setDernierchemin(false);
			  
		   }
        
        
		   
	}

	@Override
	public Object clickerdessus(int x, int y) {
		
		return false;
	}
	public boolean isDernierChemin() {
		return dernierChemin;
	}
	public void setDernierChemin(boolean dernierChemin) {
		this.dernierChemin = dernierChemin;
	}

}
