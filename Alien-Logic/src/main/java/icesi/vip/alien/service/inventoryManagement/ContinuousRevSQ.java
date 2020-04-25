package icesi.vip.alien.service.inventoryManagement;

import icesi.vip.alien.service.inventoryManagement.InventoryDetDemand.TimeUnit;

public class ContinuousRevSQ implements InventorySystem{

	private float annualDemand;
	private float orderCost;
	private float keepingCost;
	private float leadTime;
	private float serviceLevel;
	private float standardDeviationDailyDemand;
	private float standardDeviationLeadTime;
	private short businessDays;
	private TimeUnit timeUnit;
	
	public double calculateReorderPoint () {
		double d = calculateDailyDemand();
		return (d*leadTime) + calculateSafetyStock();
	}
	
	public double calculateSafetyStock () {
		double d = calculateDailyDemand();
		return InventoryDetDemand.calculateZ(serviceLevel)*Math.sqrt(Math.pow(d, 2) * Math.pow(standardDeviationLeadTime, 2) +Math.pow(standardDeviationDailyDemand, 2) * leadTime);
	}
	
	private double calculateDailyDemand () {
		return annualDemand/businessDays;
	}
	@Override
	public double calculateQuantity() {
		return Math.sqrt(2*annualDemand*orderCost/keepingCost);
	}

	public float getAnnualDemand() {
		return annualDemand;
	}

	public void setAnnualDemand(float demand) {
		switch (timeUnit) {
		case Annual:
			this.annualDemand = demand;
			break;
		case Biannual:
			this.annualDemand = demand*2;
			break;
		case Quarterly:
			this.annualDemand = demand*4;
			break;
		case Bimonthly:
			this.annualDemand = demand*6;
			break;
		case Monthly:
			this.annualDemand = demand*12;
			break;
		case Weekly:
			this.annualDemand = demand*52;
			break;
		case Daily:
			this.annualDemand = demand*businessDays;
			break;
		default:
			break;
		}
	}

	public float getOrderCost() {
		return orderCost;
	}

	public void setOrderCost(float orderCost) {
		this.orderCost = orderCost;
	}

	public float getKeepingCost() {
		return keepingCost;
	}

	public void setKeepingCost(float keepingCost) {
		this.keepingCost = keepingCost;
	}

	public float getLeadTime() {
		return leadTime;
	}

	public void setLeadTime(float leadTime) {
		this.leadTime = leadTime;
	}

	public float getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(float serviceLevel) {
		this.serviceLevel = serviceLevel/100;
	}

	public float getStandardDeviationDailyDemand() {
		return standardDeviationDailyDemand;
	}

	public void setStandardDeviationDailyDemand(float standardDeviationDemand) {
		switch (timeUnit) {
		case Annual:
			this.standardDeviationDailyDemand = standardDeviationDemand/businessDays;
			break;
		case Biannual:
			this.standardDeviationDailyDemand = standardDeviationDemand/182;
			break;
		case Quarterly:
			this.standardDeviationDailyDemand = standardDeviationDemand/91;
			break;
		case Bimonthly:
			this.standardDeviationDailyDemand = standardDeviationDemand/61;
			break;
		case Monthly:
			this.standardDeviationDailyDemand = standardDeviationDemand/30;
			break;
		case Weekly:
			this.standardDeviationDailyDemand = standardDeviationDemand/7;
			break;
		case Daily:
			this.standardDeviationDailyDemand = standardDeviationDemand;
			break;
		default:
			break;
		}
	}

	public float getStandardDeviationLeadTime() {
		return standardDeviationLeadTime;
	}

	public void setStandardDeviationLeadTime(float standardDeviationLeadTime) {
		this.standardDeviationLeadTime = standardDeviationLeadTime;
	}

	public short getBusinessDays() {
		return businessDays;
	}

	public void setBusinessDays(short businessDays) {
		this.businessDays = businessDays;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

}
