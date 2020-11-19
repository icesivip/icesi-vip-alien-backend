package icesi.vip.alien.service.routing;


import java.util.ArrayList;
import java.util.Random;

public class ClarkeAndWright implements Solver {

	/*
	 * Symmetric matrix that represents the distance between nodes, where
	 * distMatrix[i][j] is the distance from the node i to the node j
	 */
	private double[][] distMatrix;

	/*
	 * List of all nodes in the current instance
	 */
	private Node[] nodes;

	/*
	 * The initial node of the tour
	 */
	private Node origin;

	/*
	 * Number of tours
	 */
	private int tourNums;

	/*
	 * Savings matrix of joining the first node of tour i with the first node of the
	 * tour j
	 */
	private double[][] s1;

	/*
	 * Savings matrix of joining the first node of tour i with the last node of the
	 * tour j
	 */
	private double[][] s2;

	/*
	 * Savings matrix of joining the last node of tour i with the first node of tour
	 * j
	 */
	private double[][] s3;

	/*
	 * Savings matrix of joining the last node of tour i with the last node of tour
	 * j
	 */
	private double[][] s4;



	/*
	 * Creates an instance of the ClarkeAndWright class
	 */
	public ClarkeAndWright() {

	}

	@Override
	public Solution solve(Model model) {
		
		this.distMatrix = model.getDistMatrix();
		this.nodes = model.getNodes();
		this.origin = model.getOrigin();
		this.tourNums = nodes.length - 1;

		Node[] route = algorithm();
		double cost = CostCalculator.getInstance().calculateRouteCost(route, distMatrix);
		
		return new Solution(route, cost);
	}

	private Node[] algorithm() {
		
		ArrayList<ArrayList<Node>> tours = initializeTour();

		ArrayList<Node> newTour = null;
		
		
		while (tourNums > 1) {
			
			//Calculates each savings matrix
			s1 = calculateSMatrix(false, true, tours);
			s2 = calculateSMatrix(true, true, tours);
			s3 = calculateSMatrix(true, false, tours);
			s4 = calculateSMatrix(false, false, tours);
			
			//Finds the greater value in each savings matrix
			Point[] maxPoints = new Point[4];
			maxPoints[0] = getMaxValue(s1);
			maxPoints[1] = getMaxValue(s2);
			maxPoints[2] = getMaxValue(s3);
			maxPoints[3] = getMaxValue(s4);
			
			//Finds the best route merge
			Point maxPoint = maxPoints[0];
			
			for(int i=1; i<3; i++) {
				
				double[][] sMaxPoint = maxPoint.getS();
				double[][] sCurr = maxPoints[i].getS();
				
				
				double maxSaving = sMaxPoint[maxPoint.getI()][maxPoint.getJ()];
				double currentSaving = sCurr[maxPoints[i].getI()][maxPoints[i].getJ()];
				
				if( currentSaving > maxSaving ) {
					maxPoint = maxPoints[i];
				}
			}

			//System.out.println("i: " + maxPoint.getI());
			//System.out.println("j: " + maxPoint.getJ());
			
			//Merges the 2 tours with the highest savings
			newTour = mergeTours(maxPoint, tours);
			
			//Removes old tours
			if(maxPoint.getI() < maxPoint.getJ()) {
				tours.remove(maxPoint.getJ());
				tours.remove(maxPoint.getI());
			}else {
				tours.remove(maxPoint.getI());
				tours.remove(maxPoint.getJ());
			}
			
			tours.add(newTour);
			
			tourNums--;
		}

		ArrayList<Node>  aux = tours.get(0); 
		
		aux.add(0, origin);
		aux.add(origin);
		
		Node[] tour = new Node[aux.size()];
		
		for(int i=0; i<tour.length; i++) {
			tour[i] = aux.get(i);
		}
		
		
		
		return tour;
	}
	
	private ArrayList<ArrayList<Node>> initializeTour() {
		
		if (origin == null) {
			Random r = new Random(); 
			origin = nodes[r.nextInt(nodes.length)];
		}

		ArrayList<ArrayList<Node>> tours = new ArrayList<ArrayList<Node>>();
		
		//Inicializo los primeros tours
		for (int k = 0; k < nodes.length; k++) {

			if (nodes[k].equals(origin) == false) {
				ArrayList<Node> tour = new ArrayList<Node>();
				tour.add(nodes[k]);

				tours.add(tour);

			}

		}
		
		return tours;
		
	}
	
	private ArrayList<Node> mergeTours(Point maxPoint, ArrayList<ArrayList<Node>> tours) {
		
		ArrayList<Node> newTour = new ArrayList<Node>();
		
		ArrayList<Node> tourA = tours.get(maxPoint.getI());
		ArrayList<Node> tourB = tours.get(maxPoint.getJ());
		
		
		if(maxPoint.getS().equals(s1)) {
			
			// Last A - First B			
			for(int i=0; i<tourA.size(); i++) {
				newTour.add(tourA.get(i));
			}
			
			for(int i=0; i<tourB.size(); i++) {
				newTour.add(tourB.get(i));
			}
			
			
		}else if(maxPoint.getS().equals(s2)) {
			
			// First A - First B
			for(int i=0; i<tourA.size(); i++) {
				newTour.add(tourA.get(tourA.size()-1-i));
			}
			
			for(int i=0; i<tourB.size(); i++) {
				newTour.add(tourB.get(i));
			}
			
			
		}else if(maxPoint.getS().equals(s3)) {
			// First A - Last B
			for(int i=0; i<tourA.size(); i++) {
				newTour.add(tourA.get(tourA.size()-1-i));
			}
			
			for(int i=0; i<tourB.size(); i++) {
				newTour.add(tourB.get(tourB.size()-1-i));
			}
			
		}else {
			// Last A - Last B
			for(int i=0; i<tourA.size(); i++) {
				newTour.add(tourA.get(i)); 
			}
			
			for(int i=0; i<tourB.size(); i++) {
				newTour.add(tourB.get(tourB.size()-1-i));
			}
		}
		
		return newTour;
		
	}
	
	private Point getMaxValue(double[][] matrix) {
		
		int maxI = 0;
		int maxJ = 1;
		
		for(int i=0; i<matrix.length; i++) {
			
			for(int j=(i+1); j<matrix[0].length; j++) {
				
				if(matrix[i][j] > matrix[maxI][maxJ]) {
					maxI = i;
					maxJ = j;
				}
				
			}
			
		}
		
		return new Point(matrix, maxI, maxJ);
		
	}
	
	private double[][] calculateSMatrix(boolean firstNodeA, boolean firstNodeB, ArrayList<ArrayList<Node>> tours) {

		double[][] s1 = new double[tourNums][tourNums];

		ArrayList<Node> tourA;
		Node nodeA;

		ArrayList<Node> tourB;
		Node nodeB;
		
		
		for (int i = 0; i < s1.length; i++) {

			tourA = tours.get(i);
			
			if(firstNodeA == true) {
				nodeA = tourA.get(0); 
			}else {
				nodeA = tourA.get(tourA.size() - 1); 
			}
			

			for (int j = i + 1; j < s1[0].length; j++) {

				tourB = tours.get(j);
				
				if(firstNodeB == true) {
					nodeB = tourB.get(0);
				}else {
					nodeB = tourB.get(tourB.size()-1);
				}

				s1[i][j] = distMatrix[origin.getId()][nodeA.getId()]
						+ distMatrix[origin.getId()][nodeB.getId()]
						- distMatrix[nodeA.getId()][nodeB.getId()];

			}

		}

		return s1;

	}
	

}
