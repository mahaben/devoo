package controleur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;

import modele.Chemin;
import modele.Graphe;
import modele.Intersection;
import modele.Itineraire;
import modele.Livraison;
import modele.PlageHoraire;

import org.xml.sax.SAXException;

import utils.ChocoSolver;
import utils.IntersectionHandler;
import utils.LivraisonHandler;
import utils.NodeComparator;
import utils.PriorityNode;
import Graphics.FenetreGraphe;
import Graphics.FenetreText;



public class Controlleur 
{
	private static Controlleur INSTANCE = null;
	private static Stack<Action> actionStack = new Stack<Action>();
	private static Stack<Action> actionRedoStack = new Stack<Action>();
	
	private Controlleur()
	{
		
	}
	public static Controlleur getInstance()
	{			
		if (INSTANCE == null)
		{ 	INSTANCE = new Controlleur();	
		}
		return INSTANCE;
	}
	
	public static void  init()
	{
		Graphe.getInstance();
		Graphe.initIntersections();
		Graphe.initlivraisons();
		Graphe.initChemins();
		
	}
  
	public static void ajoutLivraison(Livraison livraison, Livraison livraisonPrec)
	{
		Action actionAdd = new ActionAdd(livraison, livraisonPrec);
		executeCommand(actionAdd);
	}
	public static void suppressionLivraison(int  adresseLivraison)
	{
		Action actionDelete = new ActionDelete(adresseLivraison);
		executeCommand(actionDelete);
	}
	
	
	
	public static void constructionIntersections() throws ParserConfigurationException, SAXException, IOException
	{
		Graphe.getInstance();
	//	Graphe.initIntersections();
		Graphe.setIntersections(IntersectionHandler.getIntersections());
		
	
	}
	public static void constructionLivraisons() throws ParserConfigurationException, SAXException, IOException
	{
		Graphe.getInstance();
		Graphe.initlivraisons();
		Graphe.setLivraisons(LivraisonHandler.getLivraisons());
		Graphe.setPlagesHoraires(LivraisonHandler.getPlagesHoraires());
		
	}
	  public static void executeCommand(Action action)
	    {
	        action.execute();
	        if (action  instanceof ActionAdd || action instanceof ActionDelete)
	        {
	            actionStack.push(action);
	        }
	        FenetreGraphe.getInstance().drawPlan();
			FenetreGraphe.getInstance().drawLivraisons();
			FenetreText.getInstance().drawTable();
	    }
	    public static void undo()
	    {
	        if (actionStack.size()> 0)
	        {
	            Action action = actionStack.pop();
	            actionRedoStack.push(action);
	            action.undo();
	        
	        FenetreGraphe.getInstance().drawPlan();
			FenetreGraphe.getInstance().drawLivraisons();
			FenetreText.getInstance().drawTable();
	        }
	    }
	    public static void redo()
	    {
	        if (actionRedoStack.size()> 0)
	        {
	            Action action = actionRedoStack.pop();
	            actionStack.push(action);
	            action.execute();
	        
	        FenetreGraphe.getInstance().drawPlan();
			FenetreGraphe.getInstance().drawLivraisons();
			FenetreText.getInstance().drawTable();
	        }
	    }
	
	

		public  ArrayList<Chemin> calculPlusCourtChemin(Intersection source, ArrayList<Intersection> destinations)

	    {
			
			final double infinity =1000000;
	    	ArrayList<Intersection> toCompute= (ArrayList<Intersection>) destinations.clone();
	        HashMap<Integer,Double> distances = new HashMap<Integer, Double>(); 
	        HashMap<Integer,Integer> previous = new HashMap<Integer, Integer>(); 
	   
	        for(Intersection intersection : Graphe.getIntersections())
	        {
	            distances.put(intersection.getId(),infinity);
	            previous.put(intersection.getId(),-1);
	        }
	        distances.put(source.getId(),new Double(0));
	        Comparator<PriorityNode> comparator = new NodeComparator();
	         PriorityQueue<PriorityNode> queue = new PriorityQueue<PriorityNode>((int)infinity, comparator);
	         
	        for(Intersection intersection :Graphe.getIntersections())
	        {
	            PriorityNode newNode = new PriorityNode(infinity,intersection.getId());
	            queue.add(newNode);
	        }
	        PriorityNode aNode =  new PriorityNode(0,source.getId());
	        queue.add(aNode);
	        while((queue.size()!=0)&& (destinations.size()!=0))
	        {
	        	
	        	PriorityNode uPN=queue.poll();
	            Intersection intersection1 = Graphe.getIntersectionById(uPN.getId());
	            if(distances.get(intersection1.getId())==infinity)
	            {
	            }
	            else
	            {
	                for(Integer integer : intersection1.getOutIntersections())
	                {
	                    double alt=distances.get(intersection1.getId())+intersection1.getTronconById(integer).getDuration();

	                    if(alt<distances.get(integer))
	                    {
	                        distances.put(integer,alt);
	                        previous.put(integer, intersection1.getId());
	                        PriorityNode newNode=  new PriorityNode(alt,integer);
	                        queue.add(newNode) ;
	                    }
	                }
	                
	                for(Intersection intersection : toCompute)
		            {
	                	if(intersection.getId()==intersection1.getId())
	                	{
		            		destinations.remove(intersection);
	                	}
		            }
		            
	            }
	        }
	        ArrayList<Chemin> solution= new ArrayList<Chemin>();
	        for(Intersection intersection : toCompute)
	        {
	        	double distance=0;
	        	ArrayList<Intersection> contenu = new ArrayList<Intersection>();
	        	Intersection temp=Graphe.getIntersectionById(intersection.getId());
	        	contenu.add(temp);
	        	while(temp.getId()!=source.getId())
	        	{
	        		Integer theID =previous.get(temp.getId());
	        		Intersection toADD=Graphe.getIntersectionById(theID);
	        		contenu.add(toADD);
	            	distance+=Math.round(toADD.getTronconById(temp.getId()).getDuration());
	            	temp=Graphe.getIntersectionById(theID);
	        	}
	        	Collections.reverse(contenu);
	        	Chemin newChemin = new Chemin(contenu,distance);        
	        	solution.add(newChemin);
	        }
	        	
	        return solution;
	    }

		
		public void calculerTournee()
		{
			
	
			// Recuperer tous les intersections
			List<Intersection> itersections = Graphe.getIntersections();

			int adresseEntrepot=Graphe.getAdresseEntrepot();
			Livraison livraison=new Livraison(0,0,adresseEntrepot,"",null);
			PlageHoraire plagehoraire=Graphe.getPlagesHoraires().get(0);
			livraison.setPlageHoraire(plagehoraire);
			
			// Recuperer tous les livraisons a plannifier
			List<Livraison> livraisons = Graphe.getLivraisons();
			livraisons.add(0, livraison);
			// Recuperer l'entrpot
					ArrayList<Intersection> destinations = new ArrayList<Intersection>();
					Graphe.getInstance();
					Intersection entrepot = Graphe.getIntersectionById(Graphe.getAdresseEntrepot());
					destinations.add(entrepot);
			
			// Recuperer  les intersections correspondant les Livraisons a plannifier
					
			for (Livraison livraison1 : livraisons) {
				
				int idIntersection = livraison1.getIdIntersection();
				Graphe.getInstance();
				destinations.add(Graphe.getIntersectionById(idIntersection));
					
			}
			
			
			// Calcul les plus courts chemins liant tous les points livraison
			List<Chemin> solution = new ArrayList<Chemin>() ;			
			ArrayList<Intersection> sources= ((ArrayList<Intersection>) destinations.clone());
			HashMap<Integer,HashMap> mapChemins =new HashMap<Integer,HashMap>();
			for(Intersection source :sources){
				HashMap<Integer,Chemin> map_destinations=new HashMap<Integer,Chemin>();
				
					for (Chemin n : calculPlusCourtChemin( source, (ArrayList<Intersection>) destinations.clone())) 
					{		
						map_destinations.put(n.getTarget().getId(), n);
						if(n.getStart().getId()!=n.getTarget().getId()){
						solution.add(n);

						}
	
					}
					mapChemins.put(source.getId(), map_destinations);
			}
			
			// Ordonnancer les livraison
		
			List<Livraison> livraisonOrdonne=ChocoSolver.choco(livraisons, solution);
			
			// Calcul l'heure arrive et depart pour tous les livraisons et cree l'itineraire
			
			List<Chemin> cheminsOrdonne= new ArrayList<Chemin>();
			for(int i=0; i<livraisonOrdonne.size();i++)
			{	
				int livraisonactuel=livraisonOrdonne.get(i).getIdIntersection();
				int livraisonprochaine = 0;
				if(i==(livraisonOrdonne.size()-1))
				{
					livraisonprochaine=livraisonOrdonne.get(0).getIdIntersection();
					
				}
				else
				{
					livraisonprochaine=livraisonOrdonne.get(i+1).getIdIntersection();
					
				}
				
				Chemin chemin;
				if(i==(livraisonOrdonne.size()-1))
				{
				chemin=(Chemin) mapChemins.get(livraisonprochaine).get(livraisonactuel);
				}
				else
					chemin=(Chemin) mapChemins.get(livraisonactuel).get(livraisonprochaine);
				cheminsOrdonne.add(chemin);
			}
			
			Graphe.setCheminsordonne(cheminsOrdonne);
			Graphe.getLivraisons().remove(0);
			
			
			List<List<Livraison>> tournee= ChocoSolver.classerListLivraisonsSelonPlageHoraire(Graphe.getLivraisons());
			
			
			// Calcul l'heure arrive et depart pour tous les livraisons et cree l'itineraire
			
			Itineraire itineraire =new Itineraire(1,mapChemins,tournee);
			Graphe.getInstance();
			Graphe.setItineraire(itineraire);
		}
	
}
