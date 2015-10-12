package utils;


public class PriorityNode {
    protected
            Integer id;
            double distance;
            public PriorityNode(double aDistance,Integer anID)
            {
                id=anID;
                distance= aDistance;
            }
			public Integer getId() {
				return id;
			}

			public double getDistance() {
				return distance;
			}
			public void setDistance(double distance) {
				this.distance = distance;
			}
    
}