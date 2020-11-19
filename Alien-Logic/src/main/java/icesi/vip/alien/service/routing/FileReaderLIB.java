package icesi.vip.alien.service.routing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderLIB {

	/*
	public static void main(String[] args) throws IOException {
				
		Sweep sp = new Sweep();
		
		Solution sl = sp.solve(new Model(nodes));
		System.out.println("Cost: " + sl.getRouteCost());
		
		Node[] nodesTwo = sl.getRoute();
		
		for (int i = 0 ; i < nodesTwo.length; i++) {
			System.out.println(nodesTwo[i].getId());
		}
		
	}
	*/
	
	
	public Model readFile() throws IOException {
		
		String file = "data" + File.separator + "file2.txt";

		BufferedReader br = new BufferedReader(new FileReader(new File(file)));

		String line = null;

		for (int i = 0; i < 6; i++) {
			line = br.readLine();
		}
		
		String[] cases = line.split(" ");
		int nodesQuantity = Integer.parseInt(cases[2]);
		Node[] nodes = new Node[nodesQuantity];
		
		for (int i = 6; i<9; i++) {
			line = br.readLine();
		}
		
		int count = 0;
		while (count < nodesQuantity) {
			
			String[] camps = line.split(" "); 
			nodes[count] = new Node(count,Double.parseDouble(camps[1]),Double.parseDouble(camps[2]));
			

			line = br.readLine();
			count++;
		}
		
		br.close();
		
		Model model = new Model(nodes);
		
		/*
		for(int i=0; i<nodes.length; i++) {
			Node node = nodes[i];
			System.out.println(node.getxCoord() + " " + node.getyCoord());
		}
		*/
		
		
		return model;
		
	}

}
