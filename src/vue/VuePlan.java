package vue;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import modele.Chemin;
import modele.Graphe;
import modele.Intersection;
import modele.Livraison;
import modele.Troncon;
import utils.IntersectionHandler;

public class VuePlan extends Vue{
	
	private static List<VueIntersection>  vueIntersections;
	private static List<VueTroncon> vueTroncons;
	private static List<VueLivraison> vueLivraisons;
	private static List<VueLivraison> vueLivraisonsInfaisables;
	private static List<VueChemin> vueChemins;
	private static VuePlan INSTANCE;
	private static int[] repere;
	
/*	public static VuePlan getInstance()
	{
		if ( INSTANCE == null)
		{
			INSTANCE= new VuePlan();
		}
		return INSTANCE;
	}*/
	private VuePlan()
	{
		vueIntersections=new ArrayList<VueIntersection>();
		vueTroncons=new ArrayList<VueTroncon>();
		vueChemins=new ArrayList<VueChemin>();
		vueLivraisons=new ArrayList<VueLivraison>();
		vueLivraisonsInfaisables=new ArrayList<VueLivraison>();
		
		
	}
	
	public void init()
	{
		repere=IntersectionHandler.getRepere();
		Vue.calculRepere(repere);
		
		List<Intersection> intersections= Graphe.getInstance().getIntersections();
		List<Livraison> livraisons=Graphe.getInstance().getLivraisons();
		List<Chemin> chemins=Graphe.getInstance().getCheminsordonne();
		
		
		if ( vueIntersections != null) { vueIntersections.clear();}
		if ( vueTroncons !=null) { vueTroncons.clear();}
		if( vueLivraisons != null) { vueLivraisons.clear();}
		if(vueChemins != null)  { vueChemins.clear();}
		
		for(Intersection intersection : intersections)
		{
		VueIntersection vueintersection=new VueIntersection(intersection);
		vueIntersections.add(vueintersection);
		                  
		List<Troncon> troncons=intersection.getTroncons();
		   for(Troncon troncon:troncons)
		   {
		   VueTroncon vuetroncon=new VueTroncon(troncon);
		  
		   
		   vueTroncons.add(vuetroncon);
		
		   
		   }
		
		}
		
	
		if( livraisons != null)
			{
					for(Livraison livraison: livraisons)
					{
						VueLivraison vuelivraison=new VueLivraison(livraison);
						vueLivraisons.add(vuelivraison);
					}
		}
		
		
		
		
		if (chemins != null)
		{

			for(Chemin chemin: chemins)
			{
				VueChemin vuechemin=new VueChemin(chemin);
				vueChemins.add(vuechemin);
			}
			List<Livraison>livraisonsInfaisables=Graphe.getInstance().getItineraire().getListInfaisable();
		    System.out.println("livinfais:"+livraisonsInfaisables);
		    for(Livraison livraison: livraisonsInfaisables)
			{
				VueLivraison vuelivraison=new VueLivraison(livraison);
				vueLivraisonsInfaisables.add(vuelivraison);
			}
		}
		
	}
	@Override
	public void dessiner(Graphics g) {
		
		
		init();
		
		g.clearRect(0, 0, 500, 600);
		for(VueIntersection vueintersection : vueIntersections)
		{
		vueintersection.dessiner(g);
		}
		
		for(VueTroncon vuetroncon : vueTroncons)
		{ 
			
			vuetroncon.setDernierchemin(false);
			vuetroncon.setDrawArrow(false);
			vuetroncon.dessiner(g);
			
		
		}
	if ( vueLivraisons !=null)
		{
		     for(VueLivraison vuelivraison: vueLivraisons)
				{
		    	  vuelivraison.setInfaisable(false);
					vuelivraison.dessiner(g);
				}
		     
		     if ( vueLivraisonsInfaisables !=null)
		     {
		     for(VueLivraison vuelivraison:vueLivraisonsInfaisables)
		     {
		    	 vuelivraison.setInfaisable(true);
		    	 vuelivraison.dessiner(g);
		    	 vuelivraison.setInfaisable(false);
		     }
		     }
		     
		}
	if ( vueChemins != null)
	{
		for(int i=0;i<vueChemins.size();i++)
		{
			vueChemins.get(i).setDernierChemin(false);
			
		}
		for(int i=0;i<vueChemins.size();i++)
		{ 
			if ( i== vueChemins.size()-1)
			{
				vueChemins.get(i).setDernierChemin(true);
			}
			vueChemins.get(i).dessiner(g);
			vueChemins.get(i).setDernierChemin(false);
			if ( i== vueChemins.size()-1)
			{
				vueChemins.get(i).setDernierChemin(true);
			}
		}
	}
		
	}

	
	public Object clickerdessus(int x, int y) {
		
		
		for(VueLivraison vuelivraison: vueLivraisons)
		{
			if(vuelivraison.clickerdessus(x, y) != null)  { return vuelivraison.getLivraison();}
		}
		for(VueIntersection vueintersection : vueIntersections)
		{
		    if (vueintersection.clickerdessus(x, y)  !=null ) {  return vueintersection.getIntersection();}
		}
		
		
		
		return null;
		
	}

}
