package Graphics;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import modele.Intersection;
import modele.Livraison;
import vue.Vue;
import vue.VuePlan;
import controleur.Controlleur;

public class FenetreGraphe extends JPanel implements MouseListener, ActionListener{
	
	private static VuePlan vuePlan;
	
	private boolean chargementPlan,chargementLivraisons;
	private JPopupMenu menu;
	private JMenuItem inserer;
	private JMenuItem supprimer;
	private JMenuItem informations;
	private int idIntersection,adresseLivraison;
	private static FenetreGraphe INSTANCE=null;
	private Object object;
	private FenetreGraphe()
	
	{
		
		
		menu =new JPopupMenu("Inserer/Supprimer");
	    inserer= new JMenuItem("Inserer Livraison");
	    supprimer= new JMenuItem("Supprimer Livraison");
	    informations=new JMenuItem("Plus d'informations");
	    vuePlan=vuePlan.getInstance();
	    inserer.addActionListener(this);
	    supprimer.addActionListener(this);
	    informations.addActionListener(this);
	   
	  
		menu.add(inserer);
		menu.add(supprimer);
		menu.add(informations);
		
		setPreferredSize(new Dimension(500,600));
		setMaximumSize(new Dimension(500,600));
		setSize(500,600);
		
		add(menu);
		addMouseListener(this);
		
	}
	public static FenetreGraphe getInstance()
	{
		if ( INSTANCE== null)
		{ 	INSTANCE = new FenetreGraphe();	
		}
		return INSTANCE;
	}
	@Override
	public void paintComponent(Graphics g)
	{
	
       
		if (chargementPlan) 
		{
			vuePlan.dessiner(g);
	    }
		if(chargementLivraisons)
		{
			 	
		    vuePlan.dessiner(g);
		}
	
	}
	public void drawPlan()
	{
		setBackground(Color.white);
		Graphics g=getGraphics();
		vuePlan.dessiner(g);
	}
	public void drawLivraisons()
    {
	   
		setBackground(Color.white);
		Graphics g=getGraphics();
        vuePlan.dessiner(g);
       
        
    }
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	   setBackground(Color.white);
	   object=vuePlan.clickerdessus(arg0.getX(),arg0.getY());
		if ( object !=null) 
		{
			    if ( object instanceof Livraison)
			    {
			    	inserer.setEnabled(false);
					supprimer.setEnabled(true);
		            menu.show(this, arg0.getX(), arg0.getY());
		            adresseLivraison=((Livraison) object).getIdIntersection();
		            
			    }
			    else if ( object instanceof Intersection)
			    {
			    	 supprimer.setEnabled(false);
					 inserer.setEnabled(true);
					 menu.show(this, arg0.getX(), arg0.getY());
					 idIntersection=((Intersection) object).getId();
			    }
	
			
		}
	   
	}
	public  void clear()
	{
		getGraphics().clearRect(0, 0, 500, 500);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		if ( arg0.getSource() == inserer)
		{
			if ( chargementLivraisons)
			{
			FenetreAjoutLivraison f=new FenetreAjoutLivraison(idIntersection);
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Veuillez charger une demande de livraison");
			}
			
			
		}
		else if(arg0.getSource() == supprimer)
		{
			int dialogResult =JOptionPane.showConfirmDialog(this, "Etes vous sur de vouloir supprimer ce point de livraison??");
			if(dialogResult == JOptionPane.YES_OPTION)
			{
			    Controlleur.getInstance();
				Controlleur.suppressionLivraison(adresseLivraison);
				
			}
		}
		else if(arg0.getSource() ==informations)
		{
			if(object!=null)
			{
				if ( object instanceof Livraison)
			  
				{JOptionPane.showMessageDialog(this, "Ce point Correspond à une livraison avec les informations suivantes:" +object);
			
				}
				else if ( object instanceof Intersection)
				{
					JOptionPane.showMessageDialog(this, "Ce point Correspond à une intersection avec les informations suivantes:" +object);
				}
				}
				
		}
		
	}
	
	
	
	
	
	
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public boolean isChargementPlan() {
		return chargementPlan;
	}
	public void setChargementPlan(boolean chargementPlan) {
		this.chargementPlan = chargementPlan;
	}
	public boolean isChargementLivraisons() {
		return chargementLivraisons;
	}
	public void setChargementLivraisons(boolean chargementLivraisons) {
		this.chargementLivraisons = chargementLivraisons;
	}
	
}
