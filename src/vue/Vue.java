package vue;

import java.awt.Graphics;

public abstract class Vue {
	
	public  static double unite=1;
	public static int decalageX=10;
	public static int decalageY=10;
	public static int margin = 160;
	public static int marginRepere = 25;
	public abstract  void dessiner(Graphics g);
	public abstract  Object clickerdessus(int x,int y);
	
	public static void calculRepere(int[] repere){
		
		decalageX = -repere[0]+marginRepere;
		decalageY = -repere[1]+marginRepere;
		double rayon1 = Math.sqrt((repere[2]-repere[0]+margin)*(repere[2]-repere[0]+margin) + (repere[3]-repere[1]+margin)*(repere[3]-repere[1]+margin));
		double rayon2 = Math.sqrt(500*500 + 650*650);
		unite = rayon1/rayon2;
	}

}
