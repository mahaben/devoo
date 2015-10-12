package utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import modele.Client;
import modele.Graphe;
import modele.Intersection;
import modele.Livraison;
import modele.PlageHoraire;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class LivraisonHandler extends DefaultHandler {
	
private static List<Livraison> livraisons=new ArrayList<Livraison>();
private Livraison livraison;
private static List<PlageHoraire> plagesHoraires=new ArrayList<PlageHoraire>();
private PlageHoraire plageHoraire;
private static List<Client> clients=new ArrayList<Client>();
static private String file_path;
private int id=1;
	
	

  
	
	public LivraisonHandler()
	{ 
		super(); 
	} 
	
	public static Date estformatdatevalide(String date)
	{
		Date dateparsed = null;
		List<DateFormat> dateformats=new ArrayList<DateFormat>();
		
		dateformats.add(new SimpleDateFormat("HH:mm:ss"));
		dateformats.add(new SimpleDateFormat("HH:mm:s"));
		dateformats.add(new SimpleDateFormat("HH:m:ss"));
		
		dateformats.add(new SimpleDateFormat("H:mm:ss"));
		dateformats.add(new SimpleDateFormat("H:mm:s"));
		dateformats.add(new SimpleDateFormat("H:m:ss"));
		dateformats.add(new SimpleDateFormat("H:m:s"));
		int cpt=0;
		try
		{
		  for(int i=0;i<dateformats.size();i++)
		     {dateparsed= dateformats.get(i).parse(date);}
		} catch (ParseException e) {
			cpt++;
		}
		if(cpt == dateformats.size()){ return null;}
		return dateparsed;
	}
	public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException{ 
		
		if(qName.equals("JourneeType"))
		{
			
		}
		else if(qName.equals("Entrepot"))
		{ 
			int adresse = Integer.parseInt(attributes.getValue("adresse")); 
			Graphe.getInstance();
			Graphe.setAdresseEntrepot(adresse);
		}
		else if(qName.equals("PlagesHoraires"))
		{ 
		}
		else if(qName.equals("Plage"))
		{ 
			plageHoraire=new PlageHoraire();
			plageHoraire.setId(id);
			try
			{ 
				String heuredebut  =attributes.getValue("heureDebut"); 
				String heurefin    =attributes.getValue("heureFin"); 
				
				
				
				
				    Date debut = null,fin = null;
				
					debut=estformatdatevalide(heuredebut);
					
					fin=estformatdatevalide(heurefin);
				
				
				
				if(debut == null || fin== null || debut.getHours()<0 || debut.getMinutes() <0 || debut
						.getSeconds() <0 ||  fin.getHours()<0 || fin.getMinutes() <0 || fin.getSeconds() <0)
				{
					throw new Exception("HeureDebut ou HeureFin invalide");
				}
				if (debut.compareTo(fin) ==1)
				{
					throw new Exception("Heure Debut superieur à Heure Fin");
				}
				
				plageHoraire.setHeureDebut(heuredebut);
				plageHoraire.setHeureFin(heurefin);
				
				plageHoraire.setDebut(debut);
				plageHoraire.setFin(fin);
				
				
			}
			catch(Exception e)
			{ 
				throw new SAXException(e); 
			} 
			
		}
		else if(qName.equals("Livraisons"))
		{ 
			
		}
		else if(qName.equals("Livraison"))
		{ 
			livraison=new Livraison();
			try
			{ 
				int id             = Integer.parseInt(attributes.getValue("id")); 
				int idClient       = Integer.parseInt(attributes.getValue("client")); 
				int idIntersection = Integer.parseInt(attributes.getValue("adresse")); 
				livraison.setId(id); 
				livraison.setIdClient(idClient);
				livraison.setIdIntersection(idIntersection);
                livraison.setPlageHoraire(plageHoraire);
            	Intersection intersection=Graphe.getIntersectionById(livraison.getIdIntersection());
        		if (intersection == null ) {  throw new Exception("adresse de livraison introuvable");}
        		
        		for(int i=0;i<livraisons.size();i++)
    			{
    				if ( livraisons.get(i).getIdIntersection() == livraison.getIdIntersection()) {
    					
    				throw new Exception("livraisons redondantes");
    				}
    			}
        		Client client=new Client();
        	    client.setAdresse(idIntersection);
        	    client.setId(idClient);
        		clients.add(client);
        		client=null;
              }
			catch(Exception e)
			{ 
				throw new SAXException(e); 
			} 
		}
		
		
		
	} 
	
	
	public void endElement(String uri, String localName, String qName) 	throws SAXException
	{ 
		
		
		if(qName.equals("JourneeType"))
		{
			
		}
		else if(qName.equals("Entrepot"))
		{ 
			
		}
		else if(qName.equals("PlagesHoraires"))
		{ 
			
		}
		else if(qName.equals("Plage"))
		{ 
			plagesHoraires.add(plageHoraire);
			id++;
		}
		else if(qName.equals("Livraisons"))
		{ 
		}
		else if(qName.equals("Livraison"))
		{ 
			
			livraisons.add(livraison); 
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
	public static List<Livraison> getLivraisons() throws ParserConfigurationException, SAXException, IOException
	{
		livraisons.clear();
		plagesHoraires.clear();
		SAXParserFactory fabrique = SAXParserFactory.newInstance(); 
		SAXParser parseur = fabrique.newSAXParser(); 
		File fichier = new File(file_path); 
		DefaultHandler gestionnaire = new LivraisonHandler(); 
		parseur.parse(fichier, gestionnaire);
		return ((LivraisonHandler) gestionnaire).livraisons;
	}
	public static  List<PlageHoraire> getPlagesHoraires() throws ParserConfigurationException, SAXException, IOException
	{
		livraisons.clear();
		plagesHoraires.clear();
		SAXParserFactory fabrique = SAXParserFactory.newInstance(); 
		SAXParser parseur = fabrique.newSAXParser(); 
		File fichier = new File(file_path); 
		DefaultHandler gestionnaire = new LivraisonHandler(); 
		parseur.parse(fichier, gestionnaire);
		return ((LivraisonHandler) gestionnaire).plagesHoraires;
	}

	public static String getFile_path() {
		return file_path;
	}

	public static void setFile_path(String file_path) {
		LivraisonHandler.file_path = file_path;
	}

	
	


}


