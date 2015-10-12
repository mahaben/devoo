package vue;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;
import java.util.List;

import modele.Graphe;
import modele.Intersection;
import modele.Livraison;
import modele.PlageHoraire;

public class VueLivraison extends Vue{
	
	
	private Livraison livraison;
	private int nodeHeight,nodeWidth,nodeX,nodeY;
	private  Color[] couleurs={Color.CYAN,Color.GREEN,Color.ORANGE,Color.PINK,Color.magenta,Color.yellow,Color.BLUE,Color.PINK};
    private boolean infaisable=false;
	public VueLivraison(Livraison livraison) {
		super();
		this.livraison = livraison;
		
		List<PlageHoraire> plagesHoraires=Graphe.getPlagesHoraires();
		int nombreCouleurs=plagesHoraires.size();
		
	}

	@Override
	public void dessiner(Graphics g) {
		// TODO Auto-generated method stub
		
		Color couleur;
		double unite=Vue.unite;
		int decalageX=Vue.decalageX;
		int decalageY=Vue.decalageY;
		Intersection intersection=Graphe.getIntersectionById(livraison.getIdIntersection());
		
		
		
		
		couleur= couleurs[livraison.getPlageHoraire().getId()-1];
		
		if ( Graphe.getAdresseEntrepot() == intersection.getId())
		{
			couleur=Color.black;
		}
		 
		if( infaisable) { couleur=Color.red;
		this.infaisable = false;}else{
			
			couleur= couleurs[livraison.getPlageHoraire().getId()-1];;
			
		}
		int x_old=intersection.getX();
		int y_old=intersection.getY();
		
		int x=(int)(x_old/unite+decalageX);
		int y=(int)(y_old/unite+decalageY);
	
		FontMetrics f = g.getFontMetrics();
        nodeHeight = Math.max(13, f.getHeight());
        nodeWidth = Math.max(13, f.stringWidth("01")+10/2);
    	    
    	  g.setColor(couleur);
    	    
    	  nodeX=x-nodeWidth/2;
    	  nodeY=y-nodeHeight/2;
    	  g.fillOval(x-nodeWidth/2+4,y-nodeHeight/2+4,8, 8);
    	 
    	  g.setColor(couleur);
    	 
    	
          g.drawOval(x-nodeWidth/2+4,y-nodeHeight/2+4, 8, 8);
          
          

		
	}

	@Override
	public Object clickerdessus(int x, int y) {
	
		
		
		
		int dx=(x-nodeX-4)/8;
		int dy=(y-nodeY-4)/8;
		
		
		  if (dx*dx+dy*dy<1)
		  {
			
		    return livraison;
		    }
		  else
		    return null;
		
        
		
	}

	public Livraison getLivraison() {
		return livraison;
	}

	public boolean isInfaisable() {
		return infaisable;
	}

	public void setInfaisable(boolean infaisable) {
		this.infaisable = infaisable;
	}

	
	
	

}
