package Graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView.TableRow;

import modele.Graphe;
import modele.Livraison;

public class FenetreText extends JPanel{
	
	private JTable table;
	private boolean chargementLivraisons;
    private static  FenetreText INSTANCE;

    private  Color[] couleurs={Color.CYAN,Color.GREEN,Color.GRAY,Color.PINK,Color.RED,Color.yellow,Color.BLUE,Color.ORANGE};

    public static FenetreText getInstance()
	{
		if ( INSTANCE== null)
		{ 
			INSTANCE = new FenetreText();	
		}
		return INSTANCE;
	}
	private FenetreText()
	{
		
		
		setPreferredSize(new Dimension(500,500));
		setMaximumSize(new Dimension(500,500));
		setSize(500,500);
		
		
		table=new JTable();
        
   
		JScrollPane jscroll = new JScrollPane(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		this.add(jscroll);
		

		
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
	g.clearRect(0, 0, 500, 500);
		if ( chargementLivraisons)
		{
			drawTable();
			
		}
	}
	
	public void drawTable()
	{
		
		 
		List<Livraison> livraisons=Graphe.getLivraisons();
        String [] columnsNames = { "Plage Horaire", "Id" ,"Adresse" ,"Client"};
        Object [] [] data = new Object[livraisons.size()][4];
        
        for(int i=0;i<livraisons.size();i++)
        {
        	       if ( livraisons.get(i).getIdIntersection() != Graphe.getAdresseEntrepot())
        	       {
				    data[i][0] =livraisons.get(i).getPlageHoraire().toString();
				    data[i][1] =livraisons.get(i).getId();
				    data[i][2] =livraisons.get(i).getIdIntersection();
				    data[i][3] =livraisons.get(i).getIdClient();
        	       }
		}
		
		DefaultTableModel dtm = new DefaultTableModel(data, columnsNames);
		table.setModel(dtm);
		table.setVisible(true);
		TableColumn column = null;
	
		for (int i = 0; i < 4; i++) {
		    column = table.getColumnModel().getColumn(i);
		    if (i == 0) {
		        column.setPreferredWidth(110); 
		        table.setForeground(Color.RED);
		        
		    } else {
		        column.setPreferredWidth(80);
		        table.setForeground(Color.blue);
		    }
		}
		
		
		
		
	}

	public boolean isChargementLivraisons() {
		return chargementLivraisons;
	}


	public void setChargementLivraisons(boolean chargementLivraisons) {
		this.chargementLivraisons = chargementLivraisons;
	}
	

}
