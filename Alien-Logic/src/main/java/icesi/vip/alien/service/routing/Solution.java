package icesi.vip.alien.service.routing;

public class Solution {
	
	
	/*
	 * Symmetric matrix that represents the distance between nodes, 
	 * where distMatrix[i][j] is the distance from the
	 * node i to the node j
	 */

	private Node[] route;
	
	/*
	 * Cost of the route in the current instance
	 */

	private double routeCost;
	
	
	/*
	 * Creates an instance of the class Solution
	 * 
	 *@param route a node matrix
	 *
	 *@param rutecost a double which represent the cost of the tour 
	 */
	public Solution(Node[] route, double routeCost) {
		this.route = route;
		this.routeCost = routeCost;
	}
	
	
	/*
	 * Returns the node matrix
	 * 
	 * return Node[] matrix with the nodes of the tour
	 */
	public Node[] getRoute() {
		return route;
	}

	/*
	 * Returns the cost of the tour
	 * 
	 * return double the cost of the tour
	 */
	public double getRouteCost() {
		return routeCost;
	}
	
	
	
}
