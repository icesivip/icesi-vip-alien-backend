package icesi.vip.alien.service.inventoryManagement;

public class ContinuousRevSQ implements InventorySystem{

	private int annualDemand;
	private float orderCost;
	private float keepingCost;
	private int leadTime;
	private float serviceLevel;
	private float standardDeviationDiaryDemand;
	private float standardDeviationLeadTime;
	private float businessDays;
	
	public double calculateReOrderPoint () {
		double d = calculateDiaryDemand();
		return (d*leadTime) + calculateSecurityStock();
	}
	
	public double calculateSecurityStock () {
		double d = calculateDiaryDemand();
		return serviceLevel*Math.sqrt(Math.pow(d, 2) + Math.pow(standardDeviationDiaryDemand, 2) + Math.pow(standardDeviationLeadTime, 2) + leadTime);
	}
	
	private double calculateDiaryDemand () {
		return annualDemand/businessDays;
	}
	@Override
	public double calculateQuantity() {
		return Math.sqrt(2*annualDemand*orderCost/keepingCost);
	}

	public int getAnnualDemand() {
		return annualDemand;
	}

	public void setAnnualDemand(int annualDemand) {
		this.annualDemand = annualDemand;
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

	public int getLeadTime() {
		return leadTime;
	}

	public void setLeadTime(int leadTime) {
		this.leadTime = leadTime;
	}

	public float getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(float serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public float getStandardDeviationDiaryDemand() {
		return standardDeviationDiaryDemand;
	}

	public void setStandardDeviationDiaryDemand(float standardDeviationDiaryDemand) {
		this.standardDeviationDiaryDemand = standardDeviationDiaryDemand;
	}

	public float getStandardDeviationLeadTime() {
		return standardDeviationLeadTime;
	}

	public void setStandardDeviationLeadTime(float standardDeviationLeadTime) {
		this.standardDeviationLeadTime = standardDeviationLeadTime;
	}

	public float getBusinessDays() {
		return businessDays;
	}

	public void setBusinessDays(float businessDays) {
		this.businessDays = businessDays;
	}

}
