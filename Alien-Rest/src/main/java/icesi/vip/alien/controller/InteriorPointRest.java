package icesi.vip.alien.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import icesi.vip.alien.model.linearProgramming.Model;
import icesi.vip.alien.service.interiorPoint.InteriorPointContainer;

@RestController
@RequestMapping("/api/interiorPointModule")
@CrossOrigin(origins = "*")
public class InteriorPointRest {
	
	@RequestMapping(value="/interiorPoint",method=RequestMethod.GET)
	public InteriorPointContainer interiorPoint(@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "vars", required = true) String vars,
			@RequestParam(value = "objectiveFunction", required = true) String objectiveFunction,
			@RequestParam(value = "constraints", defaultValue = "") String constraints) throws Exception {
		Model m = new Model(type);
		String[] varsS = vars.split(",");
		String[] coef = objectiveFunction.split(",");
		String[] cons = constraints.split(";");
		for (int i = 0; i < varsS.length; i++) {
			m.addVariable(varsS[i].split(":")[0], varsS[i].split(":")[1], Double.parseDouble(coef[i]));
		}
		int varcount = varsS.length;
		for (int i = 0; i < cons.length; i++) {
			double[] c = new double[varcount];
			String[] cons2 = cons[i].split(",");
			for (int j = 0; j < c.length; j++) {
				c[j] = Double.parseDouble(cons2[j]);
			}
			m.addConstraint(c, cons2[cons2.length - 2], Double.parseDouble(cons2[cons2.length - 1]), "C" + i);
		}

		return new InteriorPointContainer(m);

	}
	
}
