package icesi.vip.alien.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import icesi.vip.alien.service.networks.AdjListGraph;
import icesi.vip.alien.service.networks.AdjVertex;
import icesi.vip.alien.service.networks.Edge;
import icesi.vip.alien.service.networks.Vertex;

@RestController
@RequestMapping("/api/networksModule")
@CrossOrigin(origins = "*")
public class NetworksRest {
	
	@RequestMapping("/shortestPath")
	public String buildGraph(

			@RequestParam(value = "rows", defaultValue = "1") String rows,
			@RequestParam(value = "cols", defaultValue = "1") String cols,
			@RequestParam(value = "graph", defaultValue = "1") String graph,
			@RequestParam(value = "destiny", defaultValue = "-1") String destiny,
			@RequestParam(value = "from", required = true, defaultValue = "0") String from)throws Exception{
		
		try {
			
			AdjListGraph<Integer> g = new AdjListGraph<>(true, true);
			String[] arr = graph.split("-");
			int n = Integer.parseInt(rows);
			int m = Integer.parseInt(cols);
			int k = 0;
			for(int i = 0; i < n; i++) g.addVertex(i);
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < m; j++) {
					String[] arr2 = arr[k].split(",");
					long weight =  Integer.parseInt(arr2[0]);
					if(weight != 0) {
						g.addEdge(i, j,weight, Integer.parseInt(arr2[1]));
					}
					k++;
				}
			}
			int source = Integer.parseInt(from);
			String ans = "";
			ans= g.dijkstra(g.getVertices().get(source));
			return ans;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}
	
	@CrossOrigin
	@RequestMapping("/getShortestPath")
	public String getPath(

			@RequestParam(value = "rows", defaultValue = "1") String rows,
			@RequestParam(value = "cols", defaultValue = "1") String cols,
			@RequestParam(value = "graph", defaultValue = "1") String graph,
			@RequestParam(value = "from", required = true, defaultValue = "0") String from,
			@RequestParam(value = "destiny", defaultValue = "-1") String destiny)throws Exception{
		
		try {
			
			AdjListGraph<Integer> g = new AdjListGraph<>(true, true);
			String[] arr = graph.split("-");
			int n = Integer.parseInt(rows);
			int m = Integer.parseInt(cols);
			int k = 0;
			for(int i = 0; i < n; i++) g.addVertex(i);
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < m; j++) {
					String[] arr2 = arr[k].split(",");
					long weight =  Integer.parseInt(arr2[0]);
					if(weight != 0) {
						g.addEdge(i, j,weight, Integer.parseInt(arr2[1]));
					}
					k++;
				}
			}
			int source = Integer.parseInt(from);
			String ans = "";
			g.dijkstra(g.getVertices().get(source));
			Vertex<Integer> dest =  g.getVertices().get(Integer.parseInt(destiny));
			Vertex<Integer> curr = g.getVertices().get(Integer.parseInt(destiny));
			while(curr.getPred()!=null) {
				AdjVertex<Integer> y = g.searchVertex(curr.getValue());
				AdjVertex<Integer> x = g.searchVertex(curr.getPred().getValue());
				Edge<Integer> e = x.findEdge(y);
				ans+=""+curr.getPred().getValue()+","+ e.getId()+","+curr.getValue()+"-";
				curr = curr.getPred();
			}
			ans+=String.valueOf(dest.getD());
			return ans;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}
	@CrossOrigin
	@RequestMapping("/mstKruskal")
	public String mstKruskal(

			@RequestParam(value = "rows", defaultValue = "1") String rows,
			@RequestParam(value = "cols", defaultValue = "1") String cols,
			@RequestParam(value = "graph", defaultValue = "1") String graph)throws Exception{
		
		try {
			
			AdjListGraph<Integer> g = new AdjListGraph<>(true, true);
			String[] arr = graph.split("-");
			int n = Integer.parseInt(rows);
			int m = Integer.parseInt(cols);
			int k = 0;
			for(int i = 0; i < n; i++) g.addVertex(i);
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < m; j++) {
					String[] arr2 = arr[k].split(",");
					long weight =  Integer.parseInt(arr2[0]);
					if(weight != 0) {
						g.addEdge(i, j,weight, Integer.parseInt(arr2[1]));
					}
					k++;
				}
			}
		
			String ans = g.kruskal();
			return ans;
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}
	
}
