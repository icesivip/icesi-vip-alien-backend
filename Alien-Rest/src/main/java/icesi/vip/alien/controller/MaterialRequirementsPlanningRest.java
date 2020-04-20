package icesi.vip.alien.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnore;

import icesi.vip.alien.dto.masterPlanSchedule.MrpDTO;
import icesi.vip.alien.dto.masterPlanSchedule.ProductMrpDTO;
import icesi.vip.alien.service.materialRequirementsPlanning.MaterialRequirementsPlanning;

@RestController
@RequestMapping("/api/mrpModule")
@CrossOrigin(origins = "*")
public class MaterialRequirementsPlanningRest {
	
	@PostMapping("/solveMRP")
	public ResponseEntity<List<ProductMrpDTO>> solveMRP(@RequestBody MrpDTO requestDTO){
		MaterialRequirementsPlanning mrp = new MaterialRequirementsPlanning();
		
		List<ProductMrpDTO> MrpsDTOs = requestDTO.getProducts();
		
		for(int i = 0; i < MrpsDTOs.size(); i++) {
			mrp.addProduct(MrpsDTOs.get(i).getItemCode(), MrpsDTOs.get(i).getItemName(), MrpsDTOs.get(i).getParentProductId(), 
					MrpsDTOs.get(i).getAmountOfParent(), MrpsDTOs.get(i).getLotSizingRule(), MrpsDTOs.get(i).getLeadTime(), 
					MrpsDTOs.get(i).getInitialInventory(), MrpsDTOs.get(i).getSecurityStock(), MrpsDTOs.get(i).getCostOfTheArticle(), 
					MrpsDTOs.get(i).getOrderingCost(), MrpsDTOs.get(i).getMaintenanceCost(), requestDTO.getPeriodicity(), 1, 
					MrpsDTOs.get(i).getGrossRequirements(), MrpsDTOs.get(i).getScheduledRequirements());
			MrpsDTOs.get(i).setGrossRequirements(mrp.search(MrpsDTOs.get(i).getItemCode()).getMPS().getBruteRequirements());
			MrpsDTOs.get(i).setAvailInventProgrammed(mrp.search(MrpsDTOs.get(i).getItemCode()).getMPS().getScheduledAvailableStock());
			MrpsDTOs.get(i).setNetRequirements(mrp.search(MrpsDTOs.get(i).getItemCode()).getMPS().getNetRequirements());
			MrpsDTOs.get(i).setReceptionPlannedOrders(mrp.search(MrpsDTOs.get(i).getItemCode()).getMPS().getPlanOrders());
			MrpsDTOs.get(i).setReleasePlannedOrders(mrp.search(MrpsDTOs.get(i).getItemCode()).getMPS().getReleasedPlanOrders());
			
		}
		
		return ResponseEntity.ok().body(MrpsDTOs);
	}
	
}
