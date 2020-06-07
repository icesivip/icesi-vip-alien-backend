package icesi.vip.alien.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import icesi.vip.alien.dto.inventoryManagement.DeterministicDemandDTO;
import icesi.vip.alien.dto.inventoryManagement.InventorySystemDTO;
import icesi.vip.alien.dto.inventoryManagement.InventorySystemDTO.commonValidator;
import icesi.vip.alien.dto.inventoryManagement.InventorySystemDTO.rsValidator;
import icesi.vip.alien.dto.inventoryManagement.InventorySystemDTO.sqValidator;
import icesi.vip.alien.dto.inventoryManagement.InventorySystemDTO.ssValidator;
import icesi.vip.alien.service.inventoryManagement.ContinuousRevSQ;
import icesi.vip.alien.service.inventoryManagement.ContinuousRevSS;
import icesi.vip.alien.service.inventoryManagement.InventoryDetDemand;
import icesi.vip.alien.service.inventoryManagement.InventoryDetDemand.TimeUnit;
import icesi.vip.alien.service.inventoryManagement.InventorySystem;
import icesi.vip.alien.service.inventoryManagement.PeriodicRevRS;

@RestController
@RequestMapping(value = "/api/inventoryManagementModule")
@CrossOrigin(origins = "*")
public class InventoryManagementRest {

	@RequestMapping(value = "/toFill", method = RequestMethod.GET)
	public ResponseEntity<?> sendDemandTypes() throws Exception {
		return ResponseEntity.ok().body(InventoryDetDemand.TimeUnit.values());
	}

	@RequestMapping(value = "/solve" + InventoryDetDemand.CONTINUOUS_SQ, method = RequestMethod.POST)
	public ResponseEntity solveSystemSQ(@Validated({ sqValidator.class, commonValidator.class }) @RequestBody InventorySystemDTO system,
			BindingResult bR) {
		InventoryDetDemand inv = new InventoryDetDemand(InventoryDetDemand.CONTINUOUS_SQ);
		ContinuousRevSQ sq = (ContinuousRevSQ) inv.getInvSystem();
		return bindAndConvert(inv, system, bR, sq);
	}

	@RequestMapping(value = "/solve" + InventoryDetDemand.CONTINUOUS_SS, method = RequestMethod.POST)
	public ResponseEntity solveSystemSS(
			@RequestBody @Validated({ ssValidator.class, commonValidator.class }) InventorySystemDTO system,
			BindingResult bR) {
		InventoryDetDemand inv = new InventoryDetDemand(InventoryDetDemand.CONTINUOUS_SS);
		ContinuousRevSS ss = (ContinuousRevSS) inv.getInvSystem();
		return bindAndConvert(inv, system, bR, ss);
	}

	@RequestMapping(value = "/solve" + InventoryDetDemand.PERIODIC_RS, method = RequestMethod.POST)
	public ResponseEntity solveSystemRS(
			@RequestBody @Validated({ rsValidator.class, commonValidator.class }) InventorySystemDTO system,
			BindingResult bR) {
		InventoryDetDemand inv = new InventoryDetDemand(InventoryDetDemand.PERIODIC_RS);
		PeriodicRevRS rs = (PeriodicRevRS) inv.getInvSystem();
		return bindAndConvert(inv, system, bR, rs);
	}

	private ResponseEntity bindAndConvert(InventoryDetDemand inv, InventorySystemDTO systemDTO, BindingResult bR, InventorySystem invSystem) {
		if(systemDTO.getUnitTAvgDemand() == TimeUnit.Daily && systemDTO.getBusinessDays() == 0) {
			FieldError bD = new FieldError("inventorySystemDTO", "businessDays", "For average daily demand you have to fill the business days.");
			bR.addError(bD);
			return ResponseEntity.badRequest().body(bR.getAllErrors());
		}			else if (bR.hasErrors())
				return ResponseEntity.badRequest().body(bR.getAllErrors());
		try {
			ModelMapper mapper = new ModelMapper();
			mapper.map(systemDTO, invSystem);
			inv.writeMessageSolution();
			return ResponseEntity.ok().body(mapper.map(inv, DeterministicDemandDTO.class));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}		
	}
}
