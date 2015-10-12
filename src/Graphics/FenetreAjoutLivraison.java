package Graphics;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modele.Graphe;
import modele.Livraison;
import modele.PlageHoraire;
import controleur.Controlleur;

public class FenetreAjoutLivraison extends JFrame {
	
	
	public FenetreAjoutLivraison(int id)
	{
		Panneau panneau=new Panneau(id);
		
	}
	
	
	
	
	
	
	public class Panneau extends JPanel implements ActionListener
	{
	
   private JLabel adresse;
   private JLabel adresseInfo;
	private JLabel client;
	private JLabel plageHoraire;
	private JLabel livraisonPrec;
	private JTextField clientInfo;
	private JComboBox<PlageHoraire>  plagesHoraires;
	private JComboBox<Livraison>  livraisons;
	private JButton ajouter;
	private JButton annuler;
	private Livraison livraison;
	private Livraison livraisonBefore;
	
	public Panneau(int id)
	{	
        
	
		adresse=new JLabel("Adresse:");
		adresseInfo=new JLabel(""+id);
		client=new JLabel("Client:");
		plageHoraire=new JLabel("PlageHoraire:");
		livraisonPrec=new JLabel("LivraisonPrecedente:");
		
		ajouter=new JButton("Ajouter");
		annuler=new JButton("Annuler");
		
		clientInfo=new JTextField();
		clientInfo.setSize(100, 200);
		
		livraison=new Livraison();
		livraison.setIdIntersection(id);
		
		plagesHoraires=new JComboBox<PlageHoraire>();
		livraisons=new JComboBox<Livraison>();
		
		GridLayout gridLayout=new GridLayout(0,1);
        setLayout(gridLayout);
	    
       
        add(adresse);
        add(adresseInfo);
       add(client);
       add(clientInfo);
        
      add(plageHoraire);
      add(plagesHoraires);
        
      add(livraisonPrec);
       add(livraisons);
        
      
       ajouter.addActionListener(this);
       
       chargerComboBox();
     
     plagesHoraires.addItemListener(new ItemListener(){
         public void itemStateChanged(ItemEvent e){
         	
         	livraison.setPlageHoraire((modele.PlageHoraire)plagesHoraires.getSelectedItem());
         	
         	livraisons.removeAllItems();
    	      
    	        Graphe.getInstance();
				List<Livraison> liv=Graphe.getLivraisons();
    	      
    	      for(Livraison livraison:liv)
    	      {
    	    	 
    	    	  if ( livraison.getPlageHoraire().equals(plagesHoraires.getSelectedItem()))
    	    	  {
    	    		 livraisons.addItem(livraison);
    	    	  }
    	      }
    	      
    	      
    	               	
         }
     });
     livraisons.addItemListener(new ItemListener(){
         public void itemStateChanged(ItemEvent e){
         
         	livraisonBefore=(Livraison) livraisons.getSelectedItem();
         	
         }
     });
        
	 int result = JOptionPane.showConfirmDialog(null, this, "Ajouter Livraison",
	            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	        if (result == JOptionPane.OK_OPTION) {
	         ajouter();
	       
	               
	        } else {
	           
	        }

	      
      
        
	}
	
	
	public void ajouter()
	{
		boolean b=false;
		livraison.setPlageHoraire((modele.PlageHoraire) plagesHoraires.getSelectedItem());
		
		if ( clientInfo.getText() != null) 
		{
			try
			{
			livraison.setIdClient(Integer.parseInt(clientInfo.getText()));
			}
			catch(NumberFormatException e)
			{
				b=true;
				JOptionPane.showMessageDialog(this, "Veuillez saisir un entier");
			}
			
		}
		
	if(!b)
	{	Controlleur.getInstance();
	    Controlleur.ajoutLivraison(livraison, livraisonBefore);}
	}
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
	
		boolean b=false;
		livraison.setPlageHoraire((modele.PlageHoraire) plagesHoraires.getSelectedItem());
		
		if ( clientInfo.getText() != null) 
		{
			try
			{
			
			livraison.setIdClient(Integer.parseInt(clientInfo.getText()));
			
			}
			catch(NumberFormatException e)
			{
				b=true;
				JOptionPane.showMessageDialog(this, "Veuillez saisir un entier");
			}
			
			if(!b) 
			{ Controlleur.getInstance();
		    Controlleur.ajoutLivraison(livraison, livraisonBefore);
		    }
			
		}
		
	
		
		
	}

	public void chargerComboBox()
	{
		  Graphe.getInstance();
		  List<PlageHoraire> plages=Graphe.getPlagesHoraires();
	
		  for(PlageHoraire plageHoraire: plages)
		  {
			  
			  plagesHoraires.addItem(plageHoraire);
			 
		  }
		  
		 
	      
	      plagesHoraires.setSelectedIndex(0);
	      
	      Graphe.getInstance();
		 List<Livraison> liv=Graphe.getLivraisons();
	      int idlivraison = 0;
	      for(Livraison livraison:liv)
	      {
	    	  if ( livraison.getPlageHoraire().equals(plagesHoraires.getSelectedItem()))
	    	  {
	    		  if ( livraison.getIdIntersection() != Graphe.getAdresseEntrepot())
	    		  {
	    		   livraisons.addItem(livraison);
	    		  }
	    	  }
	      }
	      
	     idlivraison=Graphe.getInstance().getMaxIdLivraison();
	     livraison.setId(idlivraison+1);
	      
	}
	
	}




}
