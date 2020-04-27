package icesi.vip.alien.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import icesi.vip.alien.dto.inventoryManagement.InventorySystemDTO;
import icesi.vip.alien.dto.inventoryManagement.InventorySystemDTO.rsValidator;
import icesi.vip.alien.dto.inventoryManagement.InventorySystemDTO.sqValidator;
import icesi.vip.alien.dto.inventoryManagement.InventorySystemDTO.ssValidator;
import icesi.vip.alien.service.inventoryManagement.InventoryDetDemand;
import icesi.vip.alien.service.inventoryManagement.InventoryDetDemand.TimeUnit;
import lombok.extern.java.Log;

@RestController
@RequestMapping(value = "/api/inventoryManagementModule")
@CrossOrigin(origins = "*")
@Log
public class InventoryManagementRest {

	@RequestMapping(value = "/toFill", method = RequestMethod.GET)
	public TimeUnit[] sendDemandTypes()
			throws Exception {
		return InventoryDetDemand.TimeUnit.values();
	}

	@RequestMapping(value = "/solve"+InventoryDetDemand.CONTINUOUS_SQ, method = RequestMethod.POST)
	public ResponseEntity solveSystemSQ(@RequestBody @Validated({sqValidator.class}) InventorySystemDTO system, BindingResult bR) {
		if(bR.hasErrors())
			return ResponseEntity.badRequest().body(bR.getAllErrors());
		try {
			InventoryDetDemand inv = new InventoryDetDemand(InventoryDetDemand.CONTINUOUS_SQ);
			inv.configureSQ(system.getDemand(), system.getOrderCost(), system.getKeepingCost(), system.getLeadTime(),
					system.getServiceLevel(), system.getStandardDeviationDemand(), system.getStandardDeviationLeadTime(),
					system.getBusinessDays(), system.getDemandType());
			return ResponseEntity.ok().body(inv);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@RequestMapping(value = "/solve"+InventoryDetDemand.CONTINUOUS_SS, method = RequestMethod.POST)
	public ResponseEntity solveSystemSS(@RequestBody @Validated({ssValidator.class}) InventorySystemDTO system, BindingResult bR) throws Exception {
		if(bR.hasErrors())
			return ResponseEntity.badRequest().body(bR.getAllErrors());
		try {
			InventoryDetDemand inv = new InventoryDetDemand(InventoryDetDemand.CONTINUOUS_SS);
			inv.configureSS(system.getMaxLevelInventory(), system.getMinLevelInventory());
			return ResponseEntity.ok().body(inv);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@RequestMapping(value = "/solve"+InventoryDetDemand.PERIODIC_RS, method = RequestMethod.POST)
	public ResponseEntity solveSystemRS(@RequestBody @Validated({rsValidator.class}) InventorySystemDTO system, BindingResult bR) {
		if(bR.hasErrors())
			return ResponseEntity.badRequest().body(bR.getAllErrors());
		InventoryDetDemand inv = new InventoryDetDemand(InventoryDetDemand.PERIODIC_RS);
		try {
			inv.configureRS(system.getReviewTime(), system.getAvailableInventory(), system.getDemand(),
					system.getLeadTime(), system.getServiceLevel(), system.getStandardDeviationDemand(),
					system.getDemandType());
			return ResponseEntity.ok().body(inv);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
