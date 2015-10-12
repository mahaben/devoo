package Graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;

import modele.Graphe;

import org.xml.sax.SAXException;

import controleur.Controlleur;

import utils.CheckXml;
import utils.ExampleFileFilter;
import utils.GeneratePDF;


public class FenetrePrincipale extends JFrame implements ActionListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BorderLayout borderLayout;
   
	private JPanel       panneauControls;
	private FenetreGraphe fenetreGraphe;
	private FenetreText    fenetreText;
	private FenetreLegende fenetreLegende;
	
	
	private JButton choisirplan;
	private JButton choisirlivraisons;
	private JButton calculerTournee;
	private JButton editer;

	
	private boolean chargementPlan ,chargementLivraisons;
	
	
	
	public FenetrePrincipale() throws ParserConfigurationException, SAXException, IOException
	{

	    borderLayout=new BorderLayout();
        ButtonGroup buttongroup=new ButtonGroup();
        
        fenetreGraphe=FenetreGraphe.getInstance();
        fenetreText=FenetreText.getInstance();
        
		panneauControls=new JPanel(); 
		fenetreLegende=new FenetreLegende();
		
		choisirplan=new JButton("Charger plan");
		choisirplan.setSize(100, 100);
		choisirlivraisons=new JButton("Charger livraisons");
		choisirlivraisons.setSize(200, 200);
		calculerTournee=new JButton("Calculer tournee");
		calculerTournee.setSize(200, 200);
		editer=new JButton("Editer");
		editer.setSize(200, 200);
		
		
		
		buttongroup.add(choisirplan);
		buttongroup.add(choisirlivraisons);
		buttongroup.add(calculerTournee);
		buttongroup.add(editer);
	
  		panneauControls.add(choisirplan);
  		panneauControls.add(choisirlivraisons);
  		panneauControls.add(calculerTournee);
  		panneauControls.add(editer);

		choisirlivraisons.setEnabled(false);
		calculerTournee.setEnabled(false);
		editer.setEnabled(false);
		
		setLayout(borderLayout);
		
		
		choisirplan.addActionListener(this);
		choisirlivraisons.addActionListener(this);
		calculerTournee.addActionListener(this);
		editer.addActionListener(this);
		
		
		
		setSize(1100,700);
		panneauControls.setPreferredSize(new Dimension(40,40));
		panneauControls.setMaximumSize(new Dimension(50,50));
		panneauControls.setSize(40, 40);
		
		
		panneauControls.setBackground(Color.pink);
		
		
		setTitle("Acceuil");
		
		add(panneauControls,borderLayout.NORTH);
		add(fenetreGraphe,borderLayout.WEST);
	    add(fenetreText,borderLayout.EAST);
		add(fenetreLegende,borderLayout.SOUTH);
	}
	
	
	
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			JFileChooser choix = new JFileChooser("./xml");
			ExampleFileFilter filter = new ExampleFileFilter();
			
			filter.addExtension("xml");
			filter.setDescription("Fichier XML");
			
			choix.setFileFilter(filter);
			choix.setFileSelectionMode(JFileChooser.FILES_ONLY);
			
			int retour=0;
			
			if ( e.getSource() == calculerTournee)
			{
				Controlleur.getInstance().calculerTournee();
				
				fenetreGraphe.drawPlan();
				editer.setEnabled(true);
				
			}
			if(e.getSource() == editer)
			{
				GeneratePDF.writePdfFile(Graphe.getInstance().getItineraire().getInstruction());
				JOptionPane.showMessageDialog(this, "le fichier Guide des livraisons a été généré dans le dossier courant");
			}
			if (  e.getSource() == choisirplan ||  e.getSource() == choisirlivraisons)
			{  retour=choix.showOpenDialog(null);
			
			}
			
			
			File file=choix.getSelectedFile();						
			String resultat_validite = "";
		     
				
			 if(retour==JFileChooser.APPROVE_OPTION)
			 {     
							
				 if ( e.getSource() == choisirplan )
					{
					       
					       
						   try {
							resultat_validite=CheckXml.testerPlan(file);
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
							
					       
							if (resultat_validite.equals("ok"))
							{
								
								Controlleur.getInstance();
								Controlleur.init();
								
								try {
									Controlleur.constructionIntersections();
								} catch (ParserConfigurationException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (SAXException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								 chargementPlan=true;
								 fenetreGraphe.setChargementPlan(true);
								 choisirplan.setText("Changer plan");
								 choisirlivraisons.setEnabled(true);
								 fenetreGraphe.drawPlan();
								 
							}
				            
				            		
				
							else
							{
								choisirlivraisons.setEnabled(false);
								choisirplan.setText("Charger plan");
								chargementLivraisons=false;
								chargementPlan=false;
								fenetreGraphe.setChargementPlan(false);
								JOptionPane.showMessageDialog(this,resultat_validite);
						  
							}
					
				 }
				 else if(e.getSource() == choisirlivraisons)
				 {
					
						try {
							resultat_validite=CheckXml.testerLivraisons(file);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
				       
						if (resultat_validite.equals("ok"))
						{
							
							chargementLivraisons=true;
							fenetreGraphe.setChargementLivraisons(true);
							fenetreText.setChargementLivraisons(true);
							
							
							
							try {
								Controlleur.getInstance();
								Controlleur.init();
								Controlleur.constructionIntersections();
								Controlleur.constructionLivraisons();
								fenetreGraphe.drawLivraisons();
								fenetreText.drawTable();
							} catch (ParserConfigurationException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (SAXException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							choisirlivraisons.setText("Changer livraisons");
							fenetreGraphe.drawLivraisons();
							fenetreText.drawTable();
							calculerTournee.setEnabled(true);
							
						}
			            
			            		
			
						else
						{
							fenetreGraphe.setChargementLivraisons(false);
							fenetreText.setChargementLivraisons(false);
							choisirlivraisons.setText("Charger livraisons");
							JOptionPane.showMessageDialog(this,resultat_validite);
					  
						}
					 
				 }
				
		   }
	
			
		
	}
		
		
		public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
		{
			FenetrePrincipale fenetreprincipale =new FenetrePrincipale();
			fenetreprincipale.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fenetreprincipale.setBackground(Color.white);
			fenetreprincipale.setVisible(true);
			
		}

}
