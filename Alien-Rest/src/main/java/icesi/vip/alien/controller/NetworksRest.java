package icesi.vip.alien.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import icesi.vip.alien.service.networks.AdjListGraph;
import icesi.vip.alien.service.networks.AdjVertex;
import icesi.vip.alien.service.networks.Edge;
import icesi.vip.alien.service.networks.Vertex;
import icesi.vip.alien.dto.networks.*;
@RestController
@RequestMapping("/api/networksModule")
@CrossOrigin(origins = "*")
public class NetworksRest {
	
	@PostMapping("/solveDijkstra")
	public String solveDijkstra(@RequestBody GraphDTO GraphDTO) {
		
			
			AdjListGraph<Integer> g = new AdjListGraph<>(true, true);
			int n = GraphDTO.getGraph().length;
			int m = GraphDTO.getGraph()[0].length;
			int k = 0;
			for(int i = 0; i < n; i++) g.addVertex(i);
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < m; j++) {
					if(!GraphDTO.getGraph()[i][j].isEmpty()) {
						long weight =  Integer.parseInt(GraphDTO.getGraph()[i][j]);
						g.addEdge(i, j,weight, k);
						k++;
					}
					
				}
			}
			int source = Integer.parseInt(GraphDTO.getSource());
			String ans = "";
			ans= g.dijkstra(g.getVertices().get(source));
			return ans;
		

	}
	
	@PostMapping("/getShortestPath")
	public String getPath(@RequestBody GraphDTO GraphDTO) {
			
			AdjListGraph<Integer> g = new AdjListGraph<>(true, true);
			int n = GraphDTO.getGraph().length;
			int m = GraphDTO.getGraph()[0].length;
			int k = 0;
			for(int i = 0; i < n; i++) g.addVertex(i);
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < m; j++) {
					if(!GraphDTO.getGraph()[i][j].isEmpty()) {
						long weight =  Integer.parseInt(GraphDTO.getGraph()[i][j]);
						g.addEdge(i, j,weight, k);
						k++;
					}
				}
			}
			int source = Integer.parseInt(GraphDTO.getSource());
			String ans = "";
			g.dijkstra(g.getVertices().get(source));
			Vertex<Integer> dest =  g.getVertices().get(Integer.parseInt(GraphDTO.getSink()));
			Vertex<Integer> curr = g.getVertices().get(Integer.parseInt(GraphDTO.getSink()));
			while(curr.getPred()!=null) {
				AdjVertex<Integer> y = g.searchVertex(curr.getValue());
				AdjVertex<Integer> x = g.searchVertex(curr.getPred().getValue());
				Edge<Integer> e = x.findEdge(y);
				ans+=""+curr.getPred().getValue()+","+ e.getId()+","+curr.getValue()+"-";
				curr = curr.getPred();
			}
			ans+=String.valueOf(dest.getD());
			return ans;
		

	}
	@PostMapping("/solveMST")
	public String mstKruskal(@RequestBody GraphDTO GraphDTO) {
			
		AdjListGraph<Integer> g = new AdjListGraph<>(true, true);
		int n = GraphDTO.getGraph().length;
		int m = GraphDTO.getGraph()[0].length;
		int k = 0;
		for(int i = 0; i < n; i++) g.addVertex(i);
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				if(!GraphDTO.getGraph()[i][j].isEmpty()) {
					long weight =  Integer.parseInt(GraphDTO.getGraph()[i][j]);
					g.addEdge(i, j,weight, k);
					k++;
				}
			}
		}
		
			String ans = g.kruskal();
			return ans;
			

	}
	
	
	// para mfp  poner addEdgeToMFP
	
	@PostMapping("/solveFlow")
	public String mfp(@RequestBody GraphDTO GraphDTO) {
			
		AdjListGraph<Integer> g = new AdjListGraph<>(true, true);
		int n = GraphDTO.getGraph().length;
		int m = GraphDTO.getGraph()[0].length;
		int k = 0;
		for(int i = 0; i < n; i++) g.addVertex(i);
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				if(!GraphDTO.getGraph()[i][j].isEmpty()) {
					long weight =  Integer.parseInt(GraphDTO.getGraph()[i][j]);
					g.addEdgeToMFP(i, j,weight, k);
					k+=2;
				}
			}
		}
		g.max_flow(Integer.parseInt(GraphDTO.getSource()), Integer.parseInt(GraphDTO.getSink()));
		String ans = g.getAnsMFP();
		return ans;
			

	}
	
}
