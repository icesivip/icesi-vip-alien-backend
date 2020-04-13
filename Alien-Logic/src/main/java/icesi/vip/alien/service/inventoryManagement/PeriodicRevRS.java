package icesi.vip.alien.service.inventoryManagement;

public class PeriodicRevRS implements InventorySystem {

	private int reviewTime;
	private int availableInventory;
	private float diaryDemand;
	private int leadTime;
	private float serviceLevel;
	private float standardDeviationDiaryDemand;
	
	public double calculateSecurityStock () {
		return serviceLevel * calculateDeviationRPluSL();
	}
	
	private double calculateDeviationRPluSL () {
		return Math.sqrt(Math.pow(standardDeviationDiaryDemand, 2)* (reviewTime + leadTime));
	}
	
	@Override
	public double calculateQuantity() {
		return diaryDemand*(reviewTime + leadTime) + serviceLevel * calculateDeviationRPluSL() - availableInventory;
	}

	public int getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(int reviewTime) {
		this.reviewTime = reviewTime;
	}

	public int getAvailableInventory() {
		return availableInventory;
	}

	public void setAvailableInventory(int availableInventory) {
		this.availableInventory = availableInventory;
	}

	public float getDiaryDemand() {
		return diaryDemand;
	}

	public void setDiaryDemand(float diaryDemand) {
		this.diaryDemand = diaryDemand;
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

}
