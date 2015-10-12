package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import modele.Intersection;
import modele.Livraison;
import modele.PlageHoraire;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;






public class CheckXml
{
	public static boolean chevauchement() throws ParserConfigurationException, SAXException, IOException, ParseException 
	{
		
		List<PlageHoraire> plagesHoraires=LivraisonHandler.getPlagesHoraires();
		
		for(int i=0;i<plagesHoraires.size();i++)
		{
			String heureDebut =plagesHoraires.get(i).getHeureDebut();
			String heureFin   =plagesHoraires.get(i).getHeureFin();
			
			Date debut = LivraisonHandler.estformatdatevalide(heureDebut);
			Date fin   = LivraisonHandler.estformatdatevalide(heureFin);
			      for(int j=0;j<plagesHoraires.size();j++)
			      {	
			    	    String heureDebut1 =plagesHoraires.get(j).getHeureDebut();
						String heureFin1   =plagesHoraires.get(j).getHeureFin();
						
						
						
						Date debut1 = LivraisonHandler.estformatdatevalide(heureDebut1);
						Date fin1   =LivraisonHandler.estformatdatevalide(heureFin1);
						
			    	   if( debut.compareTo(debut1)==1 && debut.compareTo(fin1) == -1)
							{
								return true;
							}
			    	   if( fin.compareTo(debut1)==1 && fin.compareTo(fin1) == -1)
						{
							return true;
						}
			    	   
			      }
		}
		return false;
	}

	public static String testerPlan(File file) throws ParseException, Exception
	{
		String xsd;
		if ( file== null)
		{
			return "Fichier non Xml";
		}
		else
		{
			   xsd="./xsd/plan.xsd";
			   SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI) ;	
		     
				InputSource sourceentree = null;
				try
				{
					sourceentree = new InputSource(new FileInputStream(new File(xsd)));
				} 
				catch (FileNotFoundException e1)
				{
					 return e1.getMessage();
					
				}
				SAXSource sourceXSD = new SAXSource(sourceentree);
				Schema schema = null;
				try 
				{
					schema = factory.newSchema(sourceXSD);
					Validator validator = schema.newValidator() ;
					validator.validate(new StreamSource(new File(file.getPath()))) ;
					IntersectionHandler.setFilePath(file.getPath());
					List<Intersection>intersections=IntersectionHandler.getIntersections();
					
					
				
				} 
				catch(NumberFormatException e)
				{
					return e.getMessage();
				}
				
				catch (SAXException e) 
				{
					return e.getMessage();
					
					
				} 
				catch (IOException e) 
				{
					return e.getMessage();
				
				}
				return "ok";
		}
	}
	public static String testerLivraisons(File file)
	{
		
		String xsd;
		if ( file== null)
		{
			return "Fichier non Xml";
		}
		else
		{
			   xsd="./xsd/demandeliv.xsd";
			   SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI) ;	
		     
				InputSource sourceentree = null;
				try
				{
					sourceentree = new InputSource(new FileInputStream(new File(xsd)));
				} 
				catch (FileNotFoundException e1)
				{
					 return e1.getMessage();
					
				}
				SAXSource sourceXSD = new SAXSource(sourceentree);
				Schema schema = null;
				try 
				{
					schema = factory.newSchema(sourceXSD);
					Validator validator = schema.newValidator() ;
					validator.validate(new StreamSource(new File(file.getPath()))) ;
					
					LivraisonHandler.setFile_path(file.getPath());
					List<Livraison>livraisons=LivraisonHandler.getLivraisons();
					if (chevauchement()) { throw new Exception("chevauchement de plages horaires");}
					
					
				
				} 
				catch(NumberFormatException e)
				{
					return e.getMessage();
				}
				
				catch (SAXException e) 
				{
					return e.getMessage();
					
					
				} 
				catch (IOException e) 
				{
					return e.getMessage();
				
				}
				catch(Exception e)
				{
					return e.getMessage();
				}
				return "ok";
		}
	}

	

}
