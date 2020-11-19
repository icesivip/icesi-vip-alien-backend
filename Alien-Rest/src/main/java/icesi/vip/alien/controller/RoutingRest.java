package icesi.vip.alien.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import icesi.vip.alien.service.routing.*;
import icesi.vip.alien.dto.routing.*;

@RestController
@RequestMapping("/api/routingModule")
@CrossOrigin(origins = "*")
public class RoutingRest {

	@PostMapping("/solveNearestNeighbours")
	public String solveNearestNeighbours(@RequestBody NodesObjDTO NodesObjDTO) {

		ArrayList<NodeDTO> nodes = (ArrayList<NodeDTO>) NodesObjDTO.getNodes();
		Node[] n = new Node[nodes.size()];

		for (int i = 0; i < nodes.size(); i++) {
			NodeDTO curr = nodes.get(i);
			n[i] = new Node(curr.getId(), curr.getX(), curr.getY());
		}

		NearestNeighbour nn = new NearestNeighbour();
		Solution s = nn.solve(new Model(n));

		JSONArray ja = new JSONArray();
		
		DecimalFormat df = new DecimalFormat("#,###.##");
		double routeCost = s.getRouteCost();
		
		//Add Route Cost
		JSONObject jo = new JSONObject();
			jo.put("cost", df.format(routeCost) + "");
		ja.put(jo);

		//Add Route Order
		for (int i = 0; i < s.getRoute().length; i++) {
			Node currNode = s.getRoute()[i];
				jo = new JSONObject();
				jo.put("id", currNode.getId());
				jo.put("x", currNode.getxCoord());
				jo.put("y", currNode.getyCoord());
				ja.put(jo);
		}

		return ja.toString();
	}

	@PostMapping("/solveClarkAndWright")
	public String solveClarkAndWright(@RequestBody NodesObjDTO NodesObjDTO) {

		ArrayList<NodeDTO> nodes = (ArrayList<NodeDTO>) NodesObjDTO.getNodes();
		Node[] n = new Node[nodes.size()];

		for (int i = 0; i < nodes.size(); i++) {
			NodeDTO curr = nodes.get(i);
			n[i] = new Node(curr.getId(), curr.getX(), curr.getY());
		}

		ClarkeAndWright nn = new ClarkeAndWright();
		Solution s = nn.solve(new Model(n));

		JSONArray ja = new JSONArray();
		
		DecimalFormat df = new DecimalFormat("#,###.##");
		double routeCost = s.getRouteCost();
		
		//Add Route Cost
		JSONObject jo = new JSONObject();
			jo.put("cost", df.format(routeCost) + "");
		ja.put(jo);

		//Add Route Order
		for (int i = 0; i < s.getRoute().length; i++) {
			Node currNode = s.getRoute()[i];
				jo = new JSONObject();
				jo.put("id", currNode.getId());
				jo.put("x", currNode.getxCoord());
				jo.put("y", currNode.getyCoord());
				ja.put(jo);
		}

		return ja.toString();
	}
	
	
	@PostMapping("/solveSweep")
	public String solveSweep(@RequestBody NodesObjDTO NodesObjDTO) {

		ArrayList<NodeDTO> nodes = (ArrayList<NodeDTO>) NodesObjDTO.getNodes();
		Node[] n = new Node[nodes.size()];

		for (int i = 0; i < nodes.size(); i++) {
			NodeDTO curr = nodes.get(i);
			n[i] = new Node(curr.getId(), curr.getX(), curr.getY());
		}

		Sweep nn = new Sweep();
		Solution s = nn.solve(new Model(n));

		JSONArray ja = new JSONArray();
		
		DecimalFormat df = new DecimalFormat("#,###.##");
		double routeCost = s.getRouteCost();
		
		//Add Route Cost
		JSONObject jo = new JSONObject();
			jo.put("cost", df.format(routeCost) + "");
		ja.put(jo);

		//Add Route Order
		for (int i = 0; i < s.getRoute().length; i++) {
			Node currNode = s.getRoute()[i];
				jo = new JSONObject();
				jo.put("id", currNode.getId());
				jo.put("x", currNode.getxCoord());
				jo.put("y", currNode.getyCoord());
				ja.put(jo);
		}

		return ja.toString();
	}

}
