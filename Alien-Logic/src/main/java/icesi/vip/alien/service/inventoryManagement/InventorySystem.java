package icesi.vip.alien.service.inventoryManagement;

import icesi.vip.alien.service.inventoryManagement.InventoryDetDemand.TimeUnit;
import lombok.Data;

@Data
public abstract class InventorySystem {

	private float averageDemand;
	
	private float forecastDemand;

	private float orderCost;
	
	private float keepingCost;
	
	private float standardDevFrcErrorsDemand;
	
	private float leadTime;
	
	private float serviceLevel;
	
	private float standardDevLeadTime;
	
	private short businessDays;
	
	private float reviewCost;
	
	private TimeUnit unitTAvgDemand;
	
	private TimeUnit unitTVariables;
	
	public double calculateQuantity() {
		return -1;
	}
	
	public double calculateAnnualDemand () {
		double annualDemand = 0;
		switch (getUnitTAvgDemand()) {
		case Annual:
			annualDemand = getAverageDemand();
			break;
		case Biannual:
			annualDemand = getAverageDemand()*2;
			break;
		case Quarterly:
			annualDemand = getAverageDemand()*4;
			break;
		case Bimonthly:
			annualDemand = getAverageDemand()*6;
			break;
		case Monthly:
			annualDemand = getAverageDemand()*12;
			break;
		case Weekly:
			annualDemand = getAverageDemand()*52;
			break;
		case Daily:
			annualDemand = getAverageDemand()*getBusinessDays();
			break;
		}
		return annualDemand;
	}
	
	public double calculateReviewTime() {
		return InventoryDetDemand.roundDouble(calculateQuantity()/calculateAnnualDemand());
	}
	
	private double calculateSDeviationRPlusL () {
		return getStandardDevFrcErrorsDemand() *Math.sqrt(calculateReviewTime() + getLeadTime());
	}
	
	private double calculateForecastDemandRPlusL () {
		return getForecastDemand()*(calculateReviewTime() + getLeadTime());
	}
	
	public double calculateMaxInventory() {
		double result = calculateForecastDemandRPlusL()+(InventoryDetDemand.calculateSafetyFactor(getServiceLevel())*calculateSDeviationRPlusL());
		return Math.round(result);
	}
	
	public double calculateReorderPoint () {
		return Math.round((getForecastDemand()*getLeadTime()) + calculateSafetyStock());
	}
	
	public double calculateSafetyStock () {
		return Math.round(InventoryDetDemand.calculateSafetyFactor(getServiceLevel())*getStandardDevLeadTime());
	}
	
	public String convertUnitTimeToContext(double entrada) {
		String time = "";
		switch (getUnitTVariables()) {
		case Annual:
			time = "year";
			break;
		case Biannual:
			time = "semester";
			break;
		case Quarterly:
			time = "quarter";
			break;
		case Bimonthly:
			time = "bimonth";
			break;
		case Monthly:
			time = "month";
			break;
		case Weekly:
			time = "week";
			break;
		case Daily:
			time = "day";
			break;
		}
		if(entrada != 1)
			time += "s";
		return time;
	}
}
