package icesi.vip.alien.service.inventoryManagement;

public class ContinuousRevSQ implements InventorySystem{

	private float annualDemand;
	private float orderCost;
	private float keepingCost;
	private float leadTime;
	private float serviceLevel;
	private float standardDeviationDailyDemand;
	private float standardDeviationLeadTime;
	private short businessDays;
	
	public double calculateReorderPoint () {
		double d = calculateDiaryDemand();
		return (d*leadTime) + calculateSafetyStock();
	}
	
	public double calculateSafetyStock () {
		double d = calculateDiaryDemand();
		return serviceLevel*Math.sqrt(Math.pow(d, 2) + Math.pow(standardDeviationDailyDemand, 2) + Math.pow(standardDeviationLeadTime, 2) + leadTime);
	}
	
	private double calculateDiaryDemand () {
		return annualDemand/businessDays;
	}
	@Override
	public double calculateQuantity() {
		return Math.sqrt(2*annualDemand*orderCost/keepingCost);
	}

	public float getAnnualDemand() {
		return annualDemand;
	}

	public void setAnnualDemand(float annualDemand) {
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
		this.serviceLevel = serviceLevel;
	}

	public float getStandardDeviationDailyDemand() {
		return standardDeviationDailyDemand;
	}

	public void setStandardDeviationDailyDemand(float standardDeviationDiaryDemand) {
		this.standardDeviationDailyDemand = standardDeviationDiaryDemand;
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

	public void setBusinessDays(short businessDays) throws NumberException{
		if(businessDays>365)
			throw new NumberException("Business days", "can't be more than", 365);
		else if (businessDays < 1)
			throw new NumberException("Business days", "can't be less than", 1);
		this.businessDays = businessDays;
	}

}
