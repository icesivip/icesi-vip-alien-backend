package icesi.vip.alien.service.routing;

public class CostCalculator {

		private static CostCalculator costCalculator;
		
		private CostCalculator() {
			
		}
		
		public static CostCalculator getInstance() {
			
			if(costCalculator == null) {
				costCalculator = new CostCalculator();
			}
			
			return costCalculator;
			
		}
		
		/*
		 * Calculates the cost of the tour
		 * 
		 * @return double  the cost of the tour
		 */
		public double calculateRouteCost(Node[] nodes, double[][] distMatrix) {
			
			double cost = 0;
			for(int i=0; i<nodes.length-1; i++) {
				cost += distMatrix[nodes[i].getId()][nodes[i+1].getId()];
			}
			
			// Adds the cost from the last node of the tour to the initial node of the tour
			cost += distMatrix[nodes[nodes.length-1].getId()][nodes[0].getId()];
			
			return cost;
		}
		
		
}
