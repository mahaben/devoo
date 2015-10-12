package vue;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import modele.Graphe;
import modele.Intersection;

public class VueIntersection extends Vue{
	

	private Intersection intersection;
	
	private int nodeHeight,nodeWidth,nodeX,nodeY;
	
	



	public VueIntersection(Intersection intersection)
	{
		this.intersection=intersection;
	}
	
	
	@Override
	public void dessiner(Graphics g) {
		
		Color couleur;
		double unite=Vue.unite;
		int decalageX=Vue.decalageX;
		int decalageY=Vue.decalageY;
		if ( intersection.getId() == Graphe.getAdresseEntrepot())
		{
			couleur=Color.black;
		}
		else
		{
			couleur=Color.white;
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
    	 
    	  g.setColor(Color.red);
    	 
    	
          g.drawOval(x-nodeWidth/2+4,y-nodeHeight/2+4, 8, 8);
    	    
        //  g.drawString(""+inters, x-f.stringWidth("")/2, y+f.getHeight()/2);
		
	}

	@Override
	public Intersection clickerdessus(int x, int y) {
		
		if ( Graphe.getAdresseEntrepot() != intersection.getId())
		{
		int dx=(x-nodeX-4)/8;
		int dy=(y-nodeY-4)/8;
		
		
		  if (dx*dx+dy*dy<1)
		  {
		
		    return intersection;
		    }
		  else
		    return null;
		}
		return null;
		
	}

	public Intersection getIntersection() {
		return intersection;
	}


}
