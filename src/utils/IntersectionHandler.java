package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import modele.Intersection;
import modele.Troncon;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class IntersectionHandler extends DefaultHandler{ 
	
	private static List<Intersection> reseau=new ArrayList<Intersection>(); 
	
	private Intersection intersection; 
	
	private Troncon troncon;

	static private String filePath;
  
	
	public IntersectionHandler()
	{ 
		super(); 
	} 
	
	public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException{ 
		
		
		if(qName.equals("Reseau"))
		{ 
			
		}
		else if(qName.equals("Noeud"))
		{ 
			intersection = new Intersection(); 
			
			try
			{ 
				int id = Integer.parseInt(attributes.getValue("id")); 
				int x  = Integer.parseInt(attributes.getValue("x")); 
				int y  = Integer.parseInt(attributes.getValue("y")); 
				intersection.setId(id); 
				intersection.setX(x); 
				intersection.setY(y); 
			}
			catch(Exception e)
			{ 
				throw new SAXException(e); 
			} 
		}
		 else if(qName.equals("LeTronconSortant"))
		{ 
			
			try
			{ 
				String nom       = attributes.getValue("nomRue"); 
				double vitesse   = Double.parseDouble(attributes.getValue("vitesse").replace(",",".")); 
				double longueur  = Double.parseDouble(attributes.getValue("longueur").replace(",", ".")); 
				int idnoeuddestination           = Integer.parseInt(attributes.getValue("idNoeudDestination"));
				if (vitesse<0 || longueur<0) throw new Exception("vitesse/longueur non positive");
				
				troncon=new Troncon(idnoeuddestination,intersection.getX(),intersection.getY(),nom,vitesse,longueur);
				
				
			}
			
			catch(Exception e)
			{ 
				throw new SAXException(e); 
			} 
			
		}
		
		
	} 
	
	
	public static String getFilePath() {
		return filePath;
	}

	public static void setFilePath(String filePath) {
		IntersectionHandler.filePath = filePath;
	}

	public void endElement(String uri, String localName, String qName) 	throws SAXException
	{ 
		if(qName.equals("Reseau"))
		{ 
		}
		else if(qName.equals("Noeud"))
		{ 
			reseau.add(intersection); 
			intersection.init();
			intersection = null; 
		}
		else if(qName.equals("LeTronconSortant"))
		{
			intersection.getTroncons().add(troncon);
			troncon=null;
		}
		
		else
		{ 
			
			throw new SAXException("Balise "+qName+" inconnue."); 
		}           
	} 
	
	
	
	//début du parsing 
	public void startDocument() throws SAXException 
	{ 
		
	} 
	//fin du parsing 
	public void endDocument() throws SAXException
	{ 
		
	} 
	public static  List<Intersection> getIntersections() throws ParserConfigurationException, SAXException, IOException
	{
		if ( reseau != null){ reseau.clear();}
		
		SAXParserFactory fabrique = SAXParserFactory.newInstance(); 
		SAXParser parseur = fabrique.newSAXParser(); 
		File fichier = new File(filePath); 
		DefaultHandler gestionnaire = new IntersectionHandler(); 
		parseur.parse(fichier, gestionnaire);
		
		
		return ((IntersectionHandler) gestionnaire).reseau;
		
	}
	public static int[] getRepere()
	{
		int x_min=90000,y_min=9000,x_max=0,y_max=0;
	     //System.out.println(reseau);
		for(int i=0;i<reseau.size();i++)
		{
			if ( reseau.get(i).getX() <x_min)
			{
				x_min=reseau.get(i).getX();
			}
			if ( reseau.get(i).getY() <y_min)
			{
				y_min=reseau.get(i).getY();
			}
			if ( reseau.get(i).getX() >x_max)
			{
				x_max=reseau.get(i).getX();
			}
			if ( reseau.get(i).getY() >y_max)
			{
				y_max=reseau.get(i).getY();
			}
		}
		int tab[]=new int[4];
		tab[0]=x_min;
		tab[1]=y_min;
		tab[2]=x_max;
		tab[3]=y_max;
		
		return tab;
		
	}

	
}