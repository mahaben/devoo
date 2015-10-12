package vue;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import modele.Graphe;
import modele.Intersection;
import modele.Troncon;

public class VueTroncon  extends Vue{

	
	private Troncon troncon;
	private Color couleur=Color.black;
	private static boolean drawArrow=false;
	private static boolean dernierChemin=false;

	public VueTroncon(Troncon troncon) {
		super();
		this.troncon = troncon;
	}

	@Override
	public void dessiner(Graphics g) {
	
		
		int x_depart=troncon.getX_depart();
		int y_depart=troncon.getY_depart();
		int x_arrivee = 0,y_arrivee = 0;
		
		Graphe.getInstance();
		
		for(Intersection intersection: Graphe.getIntersections())
		{
			if (intersection.getId() == troncon.getIdNoeudDestination())
			{
				x_arrivee=intersection.getX();
				y_arrivee=intersection.getY();
				break;
			}
		}
		double unite=Vue.unite;

		int decalageX=Vue.decalageX;
		int decalageY=Vue.decalageY;
	
		g.setColor(couleur);
	    g.drawLine((int)(x_depart/unite+decalageX), (int)(y_depart/unite+decalageY), (int)(x_arrivee/unite+decalageX), (int)(y_arrivee/unite+decalageY));
	   
	    
	    if(drawArrow)
	    {
	    int x_arrow_center= ((int)(x_arrivee/unite+decalageX)+(int)(x_depart/unite+decalageX))/2;
	    int y_arrow_center= ((int)(y_arrivee/unite+decalageY)+(int)(y_depart/unite+decalageY))/2;
	    
	    Graphics2D g2 = (Graphics2D)g;  
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  
                            RenderingHints.VALUE_ANTIALIAS_ON);  
         
        Point sw = new Point((int)(x_depart/unite+decalageX), (int)(y_depart/unite+decalageY)); 
        Point sw2 = new Point((int)(x_arrivee/unite+decalageX), (int)(y_arrivee/unite+decalageY));  
        Point ne = new Point(x_arrow_center, y_arrow_center);  
        g2.draw(new Line2D.Double(sw, ne));  
        
        
        if ( dernierChemin)
        {
        	drawArrowHead(g2, ne, sw2, Color.blue);  
        }
        else
        	{drawArrowHead(g2, ne, sw, Color.blue);  }
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	   
		
		
		
	}

	public Color getCouleur() {
		return couleur;
	}

	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}
	 private void drawArrowHead(Graphics2D g2, Point tip, Point tail, Color color)  
	    {  
		 
		double phi = Math.toRadians(40);  
	      int   barb = 20; 
	        g2.setPaint(color);  
	        double dy = tip.y - tail.y;  
	        double dx = tip.x - tail.x;  
	        double theta = Math.atan2(dy, dx);   
	        double x, y, rho = theta + phi;  
	        for(int j = 0; j < 2; j++)  
	        {  
	            x = tip.x - barb * Math.cos(rho);  
	            y = tip.y - barb * Math.sin(rho);  
	            g2.draw(new Line2D.Double(tip.x, tip.y, x, y));  
	            rho = theta - phi;  
	        }  
	    }
	@Override
	public Object clickerdessus(int x, int y) {
		
		return false;
	}

	public static boolean isDrawArrow() {
		return drawArrow;
	}

	public static void setDrawArrow(boolean drawArrow) {
		VueTroncon.drawArrow = drawArrow;
	}

	public static boolean isDernierchemin() {
		return dernierChemin;
	}

	public static void setDernierchemin(boolean dernierchemin) {
		VueTroncon.dernierChemin = dernierchemin;
	}

}
