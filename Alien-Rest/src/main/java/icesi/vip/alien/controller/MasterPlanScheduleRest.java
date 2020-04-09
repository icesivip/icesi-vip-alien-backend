package icesi.vip.alien.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import icesi.vip.alien.dto.masterPlanSchedule.MpsDTO;
import icesi.vip.alien.service.masterPlanSchedule.MasterPlanSchedule;

@RestController
@RequestMapping("/api/mpsModule")
@CrossOrigin(origins = "*")
public class MasterPlanScheduleRest {
		
	@PostMapping("/solveWithOneRule")
	public ResponseEntity<MpsDTO> masterPlanSchedule(@RequestBody MpsDTO MpsDTO){
		MasterPlanSchedule MPS = new MasterPlanSchedule(MpsDTO.getLotSizingRule(), MpsDTO.getLeadTime(),
				MpsDTO.getInitialInventory(), MpsDTO.getSecurityStock(), MpsDTO.getItemCode(), MpsDTO.getItemName(),
				MpsDTO.getCostOfTheArticle(), MpsDTO.getOrderingCost(), MpsDTO.getMaintenanceCost(), 
				MpsDTO.getPeriodicity(), MpsDTO.getPeriodsOfSupply());
		
		MPS.setBruteRequirements(MpsDTO.getGrossRequirements());
		MPS.setScheduledReceptions(MpsDTO.getScheduledRequirements());
		
		MPS.createMPS();
		
		MpsDTO.setAvailInventProgrammed(MPS.getScheduledAvailableStock());
		MpsDTO.setNetRequirements(MPS.getNetRequirements());
		MpsDTO.setReceptionPlannedOrders(MPS.getPlanOrders());
		MpsDTO.setReleasePlannedOrders(MPS.getReleasedPlanOrders());

		return ResponseEntity.ok().body(MpsDTO);
	}
}
