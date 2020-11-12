package icesi.vip.alien.dto.networks;

public class GraphDTO {
	
	private String[][] graph;
	private String source;
	private String path;
	private String sink;
	
	public GraphDTO() {
		
	}

	public String[][] getGraph() {
		return graph;
	}

	public void setGraph(String[][] graph) {
		this.graph = graph;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSink() {
		return sink;
	}

	public void setSink(String sink) {
		this.sink = sink;
	}
	
	
}
