package icesi.vip.alien.service.routing;

import java.util.*;

public class Sweep implements Solver {

	/*
	 * List of all nodes in the current instance
	 */
	private Node[] nodes;
	/*
	 * Cost of the tour in the current instance
	 */
	private double cost;
	/*
	 * The initial node of the tour
	 */
	private Node origin;
	/*
	 * Symmetric matrix that represents the distance between nodes, where
	 * distMatrix[i][j] is the distance from the node i to the node j
	 */
	private double[][] distMatrix;

	/*
	 * 
	 * Create an instance of the Sweep class
	 * 
	 * @param model model the current instance
	 * 
	 */
	public Sweep() {

	}

	/*
	 * Creates an instance of the Solution class using the Sweep algorithm
	 * 
	 * @param model model the current instance
	 */

	@Override
	public Solution solve(Model model) {

		this.nodes = model.getNodes();
		this.cost = 0;
		this.origin = model.getOrigin();
		this.distMatrix = model.getDistMatrix();

		calculateRoute();
		cost = CostCalculator.getInstance().calculateRouteCost(nodes, distMatrix);
		
		return new Solution(nodes, cost);
	}

	/*
	 * Returns the nodes matrix;
	 * 
	 * @return Node[] the matrix
	 */
	public Node[] getNodes() {
		return nodes;
	}

	/*
	 * Returns the central node
	 * 
	 * @return Node the origin condinade
	 */
	public Node getOrigin() {
		return origin;
	}

	/*
	 * Returns the cost of the tour
	 * 
	 * @return double the cost of the tour
	 */
	public double getCost() {
		return cost;
	}

	// this method will calculate the route based on the angles

	/*
	 * Calculate the tour by using the nodes matrix
	 * 
	 * <b> pre:</b> All nodes are in the first quadrant of a cartesian plane <br>
	 * 
	 * <b> post <b> The nodes matrix is reordered based on the tour
	 */
	
	
	/**
	 * calculates and assign the angle to specific node
	 * @param i integer position of node in array 
	 */
	public void calculateAngle(int i) {

		//Double x = nodes[i].getxCoord();
		//Double y = nodes[i].getyCoord();
		//Double angle = Math.atan(y/x);

		Double xDif = Math.abs(nodes[i].getxCoord() - origin.getxCoord());
		Double yDif = Math.abs(nodes[i].getxCoord() - origin.getyCoord());
		Double angle = Math.atan(yDif / xDif);

		// 0 degress from X+ axis and increases angle in anti-clockwise direction
		if (nodes[i].getxCoord() > origin.getxCoord() && nodes[i].getyCoord() >= origin.getyCoord()) { // First
																										// quadrant
			angle += 0;

		} else if (nodes[i].getxCoord() >= origin.getxCoord() && nodes[i].getyCoord() < origin.getyCoord()) { // Fourth
																												// quadrant
			angle = 180 - angle;

		} else if (nodes[i].getxCoord() < origin.getxCoord() && nodes[i].getyCoord() <= origin.getyCoord()) { // Third
																												// quadrant
			angle += 180;

		} else if (nodes[i].getxCoord() <= origin.getxCoord() && nodes[i].getyCoord() > origin.getyCoord()) { // Second
																												// quadrant
			angle = 360 - angle;

		}

		nodes[i].setAngle(angle);
	}
	
	
	
	public void calculateRoute() {

		if (origin == null) {
			// need create an auxiliary variable to select method to create origin Node 
			origin = generateOrigin(1);
		}

		for (int i = 0; i < nodes.length; i++) {
			calculateAngle(i);
			
			}

		orderArray();

		// Escojo el menor angulo

	}
	
		/**
		 * Order the array by angles and distances
		 */
		public void orderArray() {
			Arrays.sort(nodes, new Comparator<Node>() {

				@Override
				public int compare(Node n1, Node n2) {
					if (n1.getAngle() == n2.getAngle()) {
						if (calculateDistance(n1)== calculateDistance(n2)) {
							return 0;
						}else if (calculateDistance(n1)> calculateDistance(n2)) {
							return -1;
						}else {
							return 1;
						}
					}
					return Double.compare(n1.getAngle(), n2.getAngle());
				}
			});
		}
	

	/*
	 * this method will generate the origin cordinade
	 */
	public Node generateOrigin(int choice) {
		
		switch (choice) {
		case 1: 
			double xPoint = 0;
			double yPoint = 0;

			for (int i = 0; i < nodes.length; i++) {
				xPoint += nodes[i].getxCoord();
				yPoint += nodes[i].getyCoord();
			}
			Node origin = new Node(-1, xPoint / nodes.length, yPoint / nodes.length);
			return origin;
			
		case 2: 
			double xCoord = 0;
			double yCoord = 0;
			for (Node x: nodes) {
				xCoord += x.getxCoord();
				yCoord += x.getyCoord();
			}
			
			
		
		return new Node(-1,xCoord,yCoord);
		}
		
		return null;
	}
	
	public double calculateDistance(Node n) {
		double c1 = Math.pow(origin.getxCoord()-n.getxCoord(),2);
		double c2 = Math.pow(origin.getyCoord()-n.getyCoord(),2);
		
		return Math.sqrt(c1+c2);
		
	}

}
