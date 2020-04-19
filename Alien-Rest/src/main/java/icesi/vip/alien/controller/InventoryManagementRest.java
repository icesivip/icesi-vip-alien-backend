package icesi.vip.alien.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import icesi.vip.alien.service.inventoryManagement.InventoryForDeterministicDemand;
import icesi.vip.alien.service.inventoryManagement.InventorySystem;
import icesi.vip.alien.service.inventoryManagement.NumberException;
import io.swagger.models.parameters.BodyParameter;

@RestController
@RequestMapping(value = "/api/inventoryManagementModule")
@CrossOrigin(origins = "*")
public class InventoryManagementRest {

	@RequestMapping(value = "/solve(s,Q)", method = RequestMethod.GET)
	public ResponseEntity solveSystemSQ(@RequestParam(value = "system", required = true) String system,
			@RequestParam(value = "annualDemand", required = true) String annualDemand,
			@RequestParam(value = "orderCost", required = true) String orderCost,
			@RequestParam(value = "keepingCost", required = true) String keepingCost,
			@RequestParam(value = "leadTime") String leadTime,
			@RequestParam(value = "serviceLevel", required = true) String serviceLevel,
			@RequestParam(value = "standardDeviationDailyDemand", required = true) String standardDeviationDailyDemand,
			@RequestParam(value = "standardDeviationLeadTime", required = true) String standardDeviationLeadTime,
			@RequestParam(value = "businessDays", required = true) String businessDays) {
			
		InventoryForDeterministicDemand inv = new InventoryForDeterministicDemand(system);
		try {
		inv.configureSQ(Float.parseFloat(annualDemand), Float.parseFloat(orderCost), Float.parseFloat(keepingCost),
				Float.parseFloat(leadTime), Float.parseFloat(serviceLevel),
				Float.parseFloat(standardDeviationDailyDemand), Float.parseFloat(standardDeviationLeadTime),
				Short.parseShort(businessDays));
		return ResponseEntity.ok().body(inv);
		} catch (NumberException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (NumberFormatException e) {
			return ResponseEntity.badRequest().body("Business days should be a natural number");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("An unexpected error has occurred");
		}
	}

	@RequestMapping(value = "/solve(s,S)", method = RequestMethod.GET)
	public ResponseEntity solveSystemSS(@RequestParam(value = "system", required = true) String system,
			@RequestParam(value = "maxLevelInventory", required = true) String maxLevelInventory,
			@RequestParam(value = "minLevelInventory", required = true) String minLevelInventory) throws Exception {
		try {
		InventoryForDeterministicDemand inv = new InventoryForDeterministicDemand(system);
		inv.configureSS(Integer.parseInt(maxLevelInventory), Integer.parseInt(minLevelInventory));
		return ResponseEntity.ok().body(inv);
		} catch (NumberException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (NumberFormatException e) {
			return ResponseEntity.badRequest().body("The inventory levels can't be deciaml");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("An unexpected error has occurred");
		}
	}

	@RequestMapping(value = "/solve(R,S)", method = RequestMethod.GET)
	public ResponseEntity solveSystemRS(@RequestParam(value = "system", required = true) String system,
			@RequestParam(value = "reviewTime", required = true) String reviewTime,
			@RequestParam(value = "availableInventory", required = true) String availableInventory,
			@RequestParam(value = "dailyDemand", required = true) String dailyDemand,
			@RequestParam(value = "standardDeviationDailyDemand", required = true) String standardDeviationDailyDemand,
			@RequestParam(value = "leadTime", required = true) String leadTime,
			@RequestParam(value = "serviceLevel", required = true) String serviceLevel) {
		try {
		InventoryForDeterministicDemand inv = new InventoryForDeterministicDemand(system);
		try {
			Integer.parseInt(availableInventory);
		} catch (NumberFormatException e) {
			return ResponseEntity.badRequest().body("The available inventory has to be a natural number");
		}
		inv.configureRS(Byte.parseByte(reviewTime), Integer.parseInt(availableInventory), Float.parseFloat(dailyDemand),
				Float.parseFloat(leadTime), Float.parseFloat(serviceLevel),
				Float.parseFloat(standardDeviationDailyDemand));
		return ResponseEntity.ok().body(inv);
	} catch (NumberException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	} catch (NumberFormatException e) {
		return ResponseEntity.badRequest().body("The review time can't be too large");
	} catch (Exception e) {
		return ResponseEntity.status(500).body("An unexpected error has occurred");
	}
	}
}
