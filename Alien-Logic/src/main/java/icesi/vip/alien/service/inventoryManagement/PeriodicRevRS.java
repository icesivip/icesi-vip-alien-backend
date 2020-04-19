package icesi.vip.alien.service.inventoryManagement;

public class PeriodicRevRS implements InventorySystem {

	private byte reviewTime;
	private int availableInventory;
	private float dailyDemand;
	private float leadTime;
	private float serviceLevel;
	private float standardDeviationDailyDemand;
	
	public double calculateSafetyStock () {
		return serviceLevel * calculateDeviationRPluSL();
	}
	
	private double calculateDeviationRPluSL () {
		return Math.sqrt(Math.pow(standardDeviationDailyDemand, 2)* (reviewTime + leadTime));
	}
	
	@Override
	public double calculateQuantity() {
		return dailyDemand*(reviewTime + leadTime) + serviceLevel * calculateDeviationRPluSL() - availableInventory;
	}

	public byte getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(byte reviewTime) {
		this.reviewTime = reviewTime;
	}

	public int getAvailableInventory() {
		return availableInventory;
	}

	public void setAvailableInventory(int availableInventory) {
		this.availableInventory = availableInventory;
	}

	public float getDailyDemand() {
		return dailyDemand;
	}

	public void setDailyDemand(float diaryDemand) {
		this.dailyDemand = diaryDemand;
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

}
