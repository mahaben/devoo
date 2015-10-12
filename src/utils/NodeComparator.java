package utils;

import java.util.Comparator;

public class NodeComparator implements Comparator<PriorityNode> {

	@Override
	public int compare(PriorityNode o1, PriorityNode o2) {
		// TODO Auto-generated method stub
		 return  (int)(o1.distance-o2.distance);
	}

}
