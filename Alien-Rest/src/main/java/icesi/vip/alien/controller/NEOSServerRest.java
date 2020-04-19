package icesi.vip.alien.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import icesi.vip.alien.service.neosServer.AMPLModel;
import icesi.vip.alien.service.neosServer.NeosClient;
import icesi.vip.alien.service.neosServer.NeosJob;
import icesi.vip.alien.service.neosServer.NeosJobXml;

@RestController
@RequestMapping("/api/neosModule")
@CrossOrigin(origins = "*")
public class NEOSServerRest {
	@Validated
	@RequestMapping(value = "/neosServer", method = RequestMethod.POST)
	public String neosServerRemoteJob(@Valid @RequestBody AMPLModel model) throws Exception {

		String HOST = "neos-server.org";
		String PORT = "3333";
		NeosClient client = new NeosClient(HOST, PORT);
		
		NeosJobXml exJob = new NeosJobXml("milp", "CPLEX", "AMPL");


		/* add contents of string example to model field of job XML */

		exJob.addParam("model", model.model);

		/* add contents of string example to data field of job XML */

		exJob.addParam("data", model.data);

		/* add contents of string example to commands field of job XML */

		exJob.addParam("commands", model.commands);

		/* convert job XML to string and add it to the parameter vector */

		/* add contents of string example to email field of job XML */
		exJob.addParam("email", model.email);

		/* call submitJob() method with string representation of job XML */
		NeosJob job = client.submitJob(exJob.toXMLString());
		/* print results to standard output */
		return job.getResult();

	}
	
}
