package icesi.vip.alien.service.routing;

import java.util.Random;

public class NearestNeighbour implements Solver {

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
	 * Creates an instance of the NearestNeighbour class
	 */
	public NearestNeighbour() {

	}

	@Override
	public Solution solve(Model model) {

		this.nodes = model.getNodes();
		this.cost = 0;
		this.origin = model.getOrigin();
		this.distMatrix = model.getDistMatrix();

		nodes = calculateTour();
		cost = CostCalculator.getInstance().calculateRouteCost(nodes, distMatrix);

		return new Solution(nodes, cost);
	}

	/*
	 * Calculates the tour of nodes
	 * 
	 * @return Node[] Matrix of nodes in tour order
	 */
	public Node[] calculateTour() {

		Node[] tour = initializeTour();

		for (int z = 1; z < nodes.length; z++) {

			boolean firstValue = false;
			int min = -1;

			int i = tour[z - 1].getId();

			for (int j = 0; j < nodes.length; j++) {

				if (firstValue && !nodes[j].isOnRoute() && distMatrix[i][j] < distMatrix[i][min] && i != j) {
					min = j;
				}

				if (!nodes[j].isOnRoute() && i != j && !firstValue) {
					min = j;
					firstValue = true;
				}

			}

			tour[z] = nodes[min];
			nodes[min].setOnRoute(true);

		}

		tour[tour.length - 1] = origin;

		return tour;
	}

	public Node[] initializeTour() {
		Node[] tour = new Node[nodes.length + 1];

		if (origin == null) {
			Random r = new Random();
			origin = nodes[r.nextInt(nodes.length)];
		}

		tour[0] = origin;
		origin.setOnRoute(true);

		return tour;
	}

}
