package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Intersection {
	private int id;
	private List<Troncon> troncons=new ArrayList<Troncon>();
	private int x;
	private int y;
	private ArrayList<Integer> destinations=null;
	private HashMap<Integer,Integer> links=null;
	public Intersection()
	{
		
	}
	public void init() 
	{
		destinations = new ArrayList<Integer>();
		links= new HashMap<Integer,Integer>();
		int i=1;
		for (Troncon a : troncons)
		{	
			int id_destination=a.getIdNoeudDestination();
			destinations.add(id_destination);
			links.put(id_destination, i);
			i++;		
		}			
	}
	public Intersection(int id, List<Troncon> troncons,  int x,int y) 
	{
		super();
		this.id = id;
		
		this.x = x;
		this.y = y;
		this.setTroncons(troncons);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getX() {
		return x;
	}
	
	@Override
	public String toString() {
		String troncon="";
		for(int i=0;i<this.troncons.size();i++)
		{
			troncon += troncons.get(i).toString() +"\n";
		}
		return "Intersection [id="+ id +" x=" + x +" y=" +y +"]"+"\n" + troncon ;
				
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public List<Troncon> getTroncons() {
		return troncons;
	}
	public void setTroncons(List<Troncon> troncons) {
		this.troncons = troncons;
	}
	public Troncon getTronconById(int next_i) {
		// TODO Auto-generated method stub
		for( Troncon troncon: troncons)
		{
			if ( troncon.getIdNoeudDestination() == next_i)
			{
				return troncon;
			}
		}
		return null;
	}
	public ArrayList<Integer> getOutIntersections()
	{
		return destinations;
	}
}