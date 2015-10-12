package Graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vue.Vue;
import controleur.Controlleur;

public class FenetreLegende extends JPanel implements ActionListener{
	
	private JLabel entrepot;
	private JLabel livraisonsNonRespectes;
	private JButton undo=new JButton();
	private JButton redo=new JButton();
	
	public FenetreLegende()
	{
		undo.setPreferredSize(new Dimension(20,20));
		redo.setPreferredSize(new Dimension(20,20));
		undo.setIcon(new ImageIcon("./images/undo-icon.png"));
		redo.setIcon(new ImageIcon("./images/redo-icon.png"));
		
	
	    undo.addActionListener(this);
	    redo.addActionListener(this);
	
		add(undo);
		add(redo);
		add(new JLabel("   "));
	
        
	    ButtonGroup buttongroup=new ButtonGroup();
	    buttongroup.add(undo);
	    buttongroup.add(redo);

		entrepot=new JLabel("Entrepot:");
		livraisonsNonRespectes=new JLabel("livraisonsNonRespectees:");
		add(entrepot);
		add(new JLabel("     "));
		
		add(livraisonsNonRespectes);
	    setPreferredSize(new Dimension(40,40));
	    setMaximumSize(new Dimension(50,50));
	    setSize(40, 40);
	}
	@Override
	public void paintComponent(Graphics g)
	{
        g.drawOval(520,10, 8, 8);
        g.setColor(Color.black);
        g.fillOval(520, 10, 8, 8);
        g.drawString("     ", 400, 10);
        
        g.drawOval(690,10, 8, 8);
        g.setColor(Color.RED);
        g.fillOval(690, 10, 8, 8);
        
      
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	    if ( arg0.getSource() == undo)
		{
			
			 Controlleur.getInstance();
			 Controlleur.undo();
		}
		else if ( arg0.getSource() == redo)
		{
			
			 Controlleur.getInstance();
			 Controlleur.redo();
		}
	
		
		
	}

}
