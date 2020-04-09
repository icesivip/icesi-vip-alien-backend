package icesi.vip.alien.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import icesi.vip.alien.service.neosServer.NeosClient;
import icesi.vip.alien.service.neosServer.NeosJob;
import icesi.vip.alien.service.neosServer.NeosJobXml;

@RestController
@RequestMapping("/api/neosModule")
@CrossOrigin(origins = "*")
public class NEOSServerRest {
	
	@RequestMapping(value = "/neosServer", method = RequestMethod.POST)
	public String neosServerRemoteJob(@RequestParam(value = "model", required = true) String model,
			@RequestParam(value = "data", required = true) String data,
			@RequestParam(value = "commands", required = true) String commands,
			@RequestParam(value = "email", defaultValue = "") String email) throws Exception {

		String HOST = "neos-server.org";
		String PORT = "3333";
		NeosClient client = new NeosClient(HOST, PORT);

		NeosJobXml exJob = new NeosJobXml("milp", "CPLEX", "AMPL");

		String example = model;

		/* add contents of string example to model field of job XML */

		exJob.addParam("model", example);

		example = data;

		/* add contents of string example to data field of job XML */

		exJob.addParam("data", example);

		example = commands;

		/* add contents of string example to commands field of job XML */

		exJob.addParam("commands", example);

		/* convert job XML to string and add it to the parameter vector */

		example = email;
		/* add contents of string example to email field of job XML */
		exJob.addParam("email", example);

		/* call submitJob() method with string representation of job XML */
		NeosJob job = client.submitJob(exJob.toXMLString());
		/* print results to standard output */
		return job.getResult();

	}
	
}
