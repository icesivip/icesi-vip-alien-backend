package icesi.vip.alien.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import icesi.vip.alien.service.simplexAlgorithm.Simplex;

@RestController
@RequestMapping("/api/simplexAlgorithmModule")
@CrossOrigin(origins = "*")
public class SimplexAlgorithmRest {
	
	@RequestMapping(value="/simplexMethod",method=RequestMethod.GET)
	public Simplex simplexMethod(@RequestParam(value = "type", required = true) String opti,
			@RequestParam(value = "iteration", defaultValue = "F") String iteration,
			@RequestParam(value = "equations", required = true) String equations) throws Exception {
		equations.replaceAll("%20", " ");
		String[] equas = equations.split("n");
		Simplex simplex = new Simplex(opti, equas);
		if (iteration.equals("F")) {
			simplex.solve(null);
		} else {
			for (int i = 0; i < Integer.parseInt(iteration); i++) {
				simplex.nextIteration();
			}
		}
		if (simplex.getMessageSol() != null) {
			simplex.buildAnalysis();
			simplex.getIntervals();
		}
		return simplex;
	}
	
}
