package icesi.vip.alien.service.routing;

public class Model{
		
	/*
	 * Matrix of all the nodes in the current instance
	 */
	private Node[] nodes; 
	
	/*
	 * Symmetric matrix that represents the distance between nodes, 
	 * where distMatrix[i][j] is the distance from the
	 * node i to the node j
	 */
	private double[][] distMatrix;
	
	/*
	 * Ordered matrix of size i+1 nodes that represent the tour in the current instance
	 */
	private Node[] route; //Cambiar route por tour
	
	/*
	 * Cost of the tour in the current instance
	 */
	private double routeCost;
	
	/*
	 * The initial node of the tour
	 */
	private Node origin; 

	
	
	/*
	 * Creates an instance of the Model class
	 * 
	 * @param nodes Matrix of all the nodes in the current instance
	 */
	public Model(Node[] nodes) {
		this.nodes = nodes;
		this.distMatrix = calculateDistMatrix();
		this.route = null;
		this.routeCost = 0;
		this.origin = null;
	}
	
	/*
	 * Creates an instance of the Model class
	 * 
	 * @param origin The initial node of the tour
	 * @param nodes Matrix of all the nodes in the current instance
	 */
	public Model(Node[] nodes, Node origin) {
		this.nodes = nodes;
		this.distMatrix = calculateDistMatrix();
		this.route = null;
		this.routeCost = 0;
		this.origin = origin;
	}
	
	/*
	 * Creates an instance of the Model class
	 * 
	 * @param nodes Matrix of all the nodes in the current instance
	 * @param route Ordered matrix of size i+1 nodes that represent the tour in the current instance
	 */
	public Model(Node[] nodes, Node[] route) {
		this.nodes = nodes;
		this.distMatrix = calculateDistMatrix();
		this.route = route;
		this.routeCost = calculateRouteCost();
		this.origin = null;
	}
	
	/*
	 * Creates an instance of the Model class
	 * 
	 * @param nodes Matrix of all the nodes in the current instance
	 * @param route Ordered matrix of size i+1 nodes that represent the tour in the current instance
	 * @param origin The initial node of the tour
	 */
	public Model(Node[] nodes, Node[] route, Node origin) {
		this.nodes = nodes;
		this.distMatrix = calculateDistMatrix();
		this.route = route;
		this.routeCost = calculateRouteCost();
		this.origin = origin;
	}
	
	
	/*
	 * Calculates the distance matrix of the nodes
	 * 
	 * @return double[][] Symmetric matrix that represents the distance between nodes, 
	 * 					  	where distMatrix[i][j] is the distance from the
	 * 						node i to the node j
	 */
	public double[][] calculateDistMatrix(){
		
		int size = nodes.length;
		
		double[][] distMatrix = new double[size][size];
 		
		for(int i=0; i<size; i++) {
			for(int j=i; j<size; j++) {
			
				
				if(i == j) {
					distMatrix[i][j] = 0; 
					
				} else {
					double distance = calculateEuclideanDist(nodes[i].getxCoord(), nodes[i].getyCoord(), nodes[j].getxCoord(), nodes[j].getyCoord());
					distMatrix[i][j] = distance;
					distMatrix[j][i] = distance; 
				}
			}
		}
		
		return distMatrix;
		
	}
	
	
	/*
	 * Calculates the euclidean distance between two points of a cartesian plane
	 * 
	 * @param x1 X coordinate of the first point
	 * @param y1 Y coordinate of the first point
	 * @param x2 X coordinate of the second point
	 * @param y2 Y coordinate of the second point
	 * 
	 * @return double The euclidean distance between the two points
	 * 
	 */
	public double calculateEuclideanDist(double x1, double y1, double x2, double y2) {
		
		return Math.sqrt(Math.pow((x1-x2), 2)+Math.pow((y1-y2),2)); 
	}

	/*
	 * Calculate the cost of the tour
	 * 
	 * @return double Cost of the tour 
	 */
	public double calculateRouteCost() {
		
		double routeCost = 0;
		
		for(int i=0; i<nodes.length-1; i++) {
			
			routeCost += distMatrix[route[i].getId()][route[i+1].getId()];
			
		}
		
		routeCost += distMatrix[route[route.length-1].getId()] [route[0].getId()];
		
		return routeCost;
	}
	
	/*
	 * Returns the nodes matrix
	 * 
	 * @return Node[] Nodes matrix
	 */
	public Node[] getNodes() {
		return nodes;
	}

	/*
	 * Returns the distance matrix of the nodes
	 * @return double[][] distance matrix
	 */
	public double[][] getDistMatrix() {
		return distMatrix;
	}
	
	/*
	 * Returns the tour matrix
	 * @return Node[] Tour matrix of nodes
	 */
	public Node[] getRoute() {
		return route;
	}
	
	/*
	 * Returns the cost of the tour
	 * 
	 * @return double Cost of the tour
	 */
	public double getRouteCost() {
		return routeCost;
	}
	
	/*
	 * Returns the initial node of the tour
	 * @return Node initial node
	 */
	public Node getOrigin() {
		return origin;
	}
	
	/*
	public void displayDistMatrix() {
		
		double[][] distMatrix = control.getDistMatrix();
		
		for(int i=0; i<distMatrix.length; i++) {
			for(int j=0; j<distMatrix[0].length; j++) {
				
				System.out.print(distMatrix[i][j] + " ");
			}
			
			System.out.println();
		}
		
	}
	*/
	
	
	/*
	
	public ArrayList<City> readCities(){
		
		ArrayList<City> cities = new ArrayList<City>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(PATH)));
		
			String line = br.readLine();
			
			
			while(line != null) {
				
				String[] city = line.split(" ");
				
				cities.add(new City(Double.parseDouble(city[0]), Double.parseDouble(city[1])));
				
				
				line = br.readLine();
			}
			
			
		
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return cities;
		
	}
	
	*/
	
}
