package icesi.vip.alien.service.routing;

import java.util.Stack;

public class Opt3Move implements Solver {
	
	/*
	 * List of all nodes in the current instance
	 */
	private Node[] nodes;
	
	private int size;
	
	/*
	 * Symmetric matrix that represents the distance between nodes, where
	 * distMatrix[i][j] is the distance from the node i to the node j
	 */
	private double[][] distMatrix;
	
	private Node[] tour;
	
	public enum OptCase {
		CASE0, CASE1, CASE2, CASE3, CASE4, CASE5, CASE6, CASE7;
	}
	
	public Opt3Move() {
		
	}
	
	@Override
	public Solution solve(Model model) {
		
		this.nodes = model.getNodes();
		this.size = nodes.length;
		this.distMatrix = model.getDistMatrix();
		this.tour = model.getRoute();
		
		return Opt3MoveAlgorithm(model);
	}

	/*
	 * La ruta es de tamaño de la cantidad de nodos, se sobreentiende que el ultimo
	 * nodo esta conectado con el primer nodo de la ruta
	 */
	public Solution Opt3MoveAlgorithm(Model model) {

		OptCase[] optCases = OptCase.class.getEnumConstants();

//		Node[] nodes = model.getNodes();
//		int size = nodes.length;
//
//		double[][] distMatrix = model.getDistMatrix();
//		Node[] tour = model.getRoute();

		boolean foundImprovement = false;

		while (!foundImprovement) {

			foundImprovement = true;
			for (int counter_1 = 0; counter_1 < size && foundImprovement; counter_1++) {

				int i = counter_1;
				int X1 = tour[i].getId();
				int X2 = tour[(i + 1) % size].getId();

				for (int counter_2 = 1; counter_2 < size - 2 && foundImprovement; counter_2++) {

					int j = (i + counter_2) % size;
					int Y1 = tour[j].getId();
					int Y2 = tour[(j + 1) % size].getId();

					for (int counter_3 = counter_2 + 1; counter_3 < size && foundImprovement; counter_3++) {

						int k = (i + counter_3) % size;
						int Z1 = tour[k].getId();
						int Z2 = tour[(k + 1) % size].getId();

						for (int optCase = 0; optCase < optCases.length && foundImprovement; optCase++) {

							double ge = gainExpected(X1, X2, Y1, Y2, Z1, Z2, optCases[optCase], model);

							if (ge < 0 && !(Math.abs(ge) < 0.00000001)) {

								tour = make3OptMove(tour, i, j, k, optCases[optCase]);
								foundImprovement = false;

								break;
							}
						}

					} // End z for
				} // End j for
			} // End i for
		} // End While

		return new Solution(tour, CostCalculator.getInstance().calculateRouteCost(tour, distMatrix));
	}

	public Node[] reverseSegment(Node[] tour, int start, int finish) {

		Stack<Node> reversed = new Stack<>();

		int z = start;
		boolean stop = true;
		// Llenamos el stack
		while (stop) {
			reversed.push(tour[z % tour.length]);
			if (z % tour.length == finish) {
				stop = false;
			}
			z = z + 1;
		}

		z = start;
		stop = true;
		while (stop) {
			tour[z % tour.length] = reversed.pop();
			if (z % tour.length == finish) {
				stop = false;
			}
			z = z + 1;
		}
		return tour;
	}

	public Node[] make3OptMove(Node[] tour, int i, int j, int k, OptCase optCase) {

		switch (optCase) {

		case CASE0: // abc
			// No hay intercambio
			break;
		case CASE1: // a'bc
			reverseSegment(tour, (k + 1) % tour.length, i);
			break;
		case CASE2: // abc'
			reverseSegment(tour, (j + 1) % tour.length, k);
			break;
		case CASE3: // ab'c
			reverseSegment(tour, (i + 1) % tour.length, j);
			break;
		case CASE4: // ab'c'
			reverseSegment(tour, (j + 1) % tour.length, k);
			reverseSegment(tour, (i + 1) % tour.length, j);
			break;
		case CASE5: // a'b'c
			reverseSegment(tour, (k + 1) % tour.length, i);
			reverseSegment(tour, (i + 1) % tour.length, j);
			break;
		case CASE6: // a'bc'
			reverseSegment(tour, (k + 1) % tour.length, i);
			reverseSegment(tour, (j + 1) % tour.length, k);
			break;
		case CASE7: // a'b'c'
			reverseSegment(tour, (k + 1) % tour.length, i);
			reverseSegment(tour, (i + 1) % tour.length, j);
			reverseSegment(tour, (j + 1) % tour.length, k);
			break;
		}

		return tour;
	}

	public double gainExpected(int X1, int X2, int Y1, int Y2, int Z1, int Z2, OptCase optCase, Model model) {

		double[][] distMatrix = model.getDistMatrix();
		double delLength = 0;
		double addLength = 0;

		switch (optCase) {

		case CASE0:
			return 0;

		// 2-Opt Moves
		case CASE1:
			delLength = distMatrix[X1][X2] + distMatrix[Z1][Z2];
			addLength = distMatrix[X1][Z1] + distMatrix[X2][Z2];
			break;
		case CASE2:
			delLength = distMatrix[Y1][Y2] + distMatrix[Z1][Z2];
			addLength = distMatrix[Y1][Z1] + distMatrix[Y2][Z2];
			break;
		case CASE3:
			delLength = distMatrix[X1][X2] + distMatrix[Y1][Y2];
			addLength = distMatrix[X1][Y1] + distMatrix[X2][Y2];
			break;

		// 3-Opt Moves
		case CASE4:
			// Cambio Z2 por Z1
			addLength = distMatrix[X1][Y1] + distMatrix[X2][Z1] + distMatrix[Y2][Z2];
			break;
		case CASE5:
			addLength = distMatrix[X1][Z1] + distMatrix[Y2][X2] + distMatrix[Y1][Z2];
			break;
		case CASE6:
			addLength = distMatrix[X1][Y2] + distMatrix[Z1][Y1] + distMatrix[X2][Z2];
			break;
		case CASE7:
			addLength = distMatrix[X1][Y2] + distMatrix[Z1][X2] + distMatrix[Y1][Z2];
			break;

		}

		if (optCase == OptCase.CASE4 || optCase == OptCase.CASE5 || optCase == OptCase.CASE6
				|| optCase == OptCase.CASE7) {
			delLength = distMatrix[X1][X2] + distMatrix[Y1][Y2] + distMatrix[Z1][Z2];
		}
		return addLength - delLength;
	}

}
