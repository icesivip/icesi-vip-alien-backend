package icesi.vip.alien.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnore;

import icesi.vip.alien.service.materialRequirementsPlanning.MaterialRequirementsPlanning;

@RestController
@RequestMapping("/api/mrpModule")
@CrossOrigin(origins = "*")
public class MaterialRequirementsPlanningRest {
	
	@JsonIgnore()
	@RequestMapping(value="/createMRP", method=RequestMethod.GET)
	public MaterialRequirementsPlanning showMRP(@RequestParam(value = "fatherIds") String paramFatherId,
			@RequestParam(value = "id") String paramId, @RequestParam(value = "name") String paramName,
			@RequestParam(value = "leadTime") String paramLeadTime, @RequestParam(value = "amount") String paramAmount,
			@RequestParam(value = "initialInv") String paramInitialInv,
			@RequestParam(value = "securityInv") String paramSecuriInv, @RequestParam(value = "date") String paramDate,
			@RequestParam(value = "articleCost") String paramArticleCost, @RequestParam(value = "maintenanceCost") String paramMaintenanceCost,
			@RequestParam(value = "orderingCost") String paramOrderingCost, @RequestParam(value = "lotSizingRule") String paramLotSizingRule,
			@RequestParam(value = "programDelivery") String paramProgramDelivery,
			@RequestParam(value = "rqB") String paramRqB, @RequestParam(value = "rqBDates") String paramRqBDates, 
			@RequestParam(value = "periodicity") String paramPeriodicity) {

		MaterialRequirementsPlanning mrp = new MaterialRequirementsPlanning();

		String[] id = paramId.split(";");

		String[] fatherId = paramFatherId.split(";");

		String[] name = paramName.split(";");

		String[] leadTime = paramLeadTime.split(";");

		String[] amount = paramAmount.split(";");

		String[] initialInv = paramInitialInv.split(";");

		String[] securiInv = paramSecuriInv.split(";");

		String[] dates = new String[Integer.parseInt(paramDate)];
		
		String[] articleCost = paramArticleCost.split(";");
		
		String[] maintenanceCost = paramMaintenanceCost.split(";");
		
		String[] orderingCost = paramOrderingCost.split(";");
		
		String[] lotSizingRule = paramLotSizingRule.split(";");

		for (int i = 0; i < dates.length; i++) {
			dates[i] = (i + 1 + "");
		}
		
		for(int i = 0; i < fatherId.length; i++) {
			if(fatherId[i].equals("none")) {
				fatherId[i] = null;
			}
		}

		String[] programDelivery = paramProgramDelivery.split("-");
		String[] reqBrutos = paramRqB.split(";");

		ArrayList<String> datesAr = new ArrayList<String>();

		ArrayList<ArrayList<Integer>> maestro = new ArrayList<>();

		ArrayList<Integer> programDeliveryAr = new ArrayList<Integer>();

		ArrayList<Integer> rqBAr = new ArrayList<Integer>();
		ArrayList<String> rqBDatesAr = new ArrayList<String>();

		for (int j = 0; j < dates.length; j++) {
			datesAr.add(dates[j]);
		}

		for (int i = 1; i < programDelivery.length; i++) {

			String[] tempDelivery = programDelivery[i].split(";");
			programDeliveryAr = new ArrayList<>();
			for (int j = 0; j < tempDelivery.length; j++) {
				if (!tempDelivery[i].equals("")) {
					int temp = Integer.parseInt(tempDelivery[j]);
					programDeliveryAr.add(temp);
				}
			}
			maestro.add(programDeliveryAr);
		}

		for (int j = 0; j < 10; j++) {
			rqBDatesAr.add((j + 1) + "");
		}

		for (int j = 0; j < reqBrutos.length; j++) {
			if (!reqBrutos[j].equals("")) {
				int temp = Integer.parseInt(reqBrutos[j]);
				rqBAr.add(temp);
			}
		}

		int[] leadTimeInteger = new int[fatherId.length];
		int[] amountInteger = new int[fatherId.length];
		int[] initialInvInteger = new int[fatherId.length];
		int[] securyInvInteger = new int[fatherId.length];

		for (int i = 0; i < securiInv.length; i++) {
			if (!amount[i].equals("")) {
				amountInteger[i] = Integer.parseInt(amount[i]);
			}

			initialInvInteger[i] = Integer.parseInt(initialInv[i]);
			leadTimeInteger[i] = Integer.parseInt(leadTime[i]);
			securyInvInteger[i] = Integer.parseInt(securiInv[i]);
		}

		for (int i = 0; i < fatherId.length; i++) {
			int amount_1 = !amount[i].equals("") ? Integer.parseInt(amount[i]) : 0;
			mrp.addProduct(id[i], name[i], fatherId[i], amount_1, lotSizingRule[i], Integer.parseInt(leadTime[i]), Integer.parseInt(initialInv[i]), 
					Integer.parseInt(securiInv[i]), Double.parseDouble(articleCost[i]), Double.parseDouble(orderingCost[i]), Double.parseDouble(maintenanceCost[i]), 
					paramPeriodicity, 1, rqBAr, maestro.get(i));
		}
		
		return mrp;
	}
	
}
