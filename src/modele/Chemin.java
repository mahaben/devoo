package modele;

import java.util.ArrayList;
import java.util.List;
public class Chemin
{
	
protected List<Intersection> trajectoire;
protected List<Troncon> troncons = new ArrayList<Troncon>();
protected Intersection start;
protected Intersection target;
protected double duration;

public Chemin(ArrayList<Intersection> contenu, double duration)
{
	super();
	this.trajectoire = contenu;
	this.start = contenu.get(0);
	this.target = contenu.get(contenu.size()-1);
	this.duration = duration;
	for(int i=0; i<trajectoire.size()-1;i++)
	{	
		int next_i= trajectoire.get(i+1).getId();			
		troncons.add( contenu.get(i).getTronconById(next_i));	
	}
}

@Override
public String toString() {
	return "Chemin [start=" + start.getId() + ", target=" + target.getId() + ", duration="
			+ duration + "]";
}

public List<Intersection> getTrajectory() {
	return trajectoire;
}

public void setTrajectory(List<Intersection> trajectory) {
	this.trajectoire = trajectoire;
}

public List<Troncon> getTroncons() {
	return troncons;
}

public void setTroncons(List<Troncon> troncons) {
	this.troncons = troncons;
}

public Intersection getStart() {
	return start;
}

public void setStart(Intersection start) {
	this.start = start;
}

public Intersection getTarget() {
	return target;
}

public void setTarget(Intersection target) {
	this.target = target;
}

public double getDuration() {
	return duration;
}

public void setDuration(double duration) {
	this.duration = duration;
}
}