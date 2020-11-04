package icesi.vip.alien.service.networks;

public class Edge<Integer> implements Comparable<Edge<Integer>>{

	private double weight;
	private int id;
	private int rev;
    private long flow;
    private long capacity;
	private Vertex<Integer> source;
	private Vertex<Integer> destination;

	public Edge(Vertex<Integer> source, Vertex<Integer> destination) {
		this(source, destination, 1D);
	}
	
	public Edge(Vertex<Integer> source, Vertex<Integer> destination, double weight) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}

	public Edge(Vertex<Integer> source, Vertex<Integer> destination, long capacity) {
		this.source = source;
		this.destination = destination;
		this.capacity = capacity;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Vertex<Integer> getSource() {
		return source;
	}

	public Vertex<Integer> getDestination() {
		return destination;
	}

	@Override
	public int compareTo(Edge<Integer> o) {
		return Double.compare(weight, o.weight);
	}

	public int getId() {
		return id;
	}
	
	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}

	public void setRev(int rev) {
		this.rev = rev;
	}
	
	public void setFlow(long flow) {
		this.flow = flow;
	}
	
	public long getFlow() {
		return flow;
	}
	
	public long getCapacity() {
		return capacity;
	}
	
	public int getRev() {
		return rev;
	}
}	
