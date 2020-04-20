package icesi.vip.alien.dto.masterPlanSchedule;

import java.util.List;

public class ProductMrpDTO {
	
	private String itemName;
	private String itemCode;
	private int leadTime;
	private int securityStock;
	private int initialInventory;
	private double costOfTheArticle;
	private double maintenanceCost;
	private double orderingCost;
	private String lotSizingRule;
	private String parentProductId;
	private int amountOfParent;
	
	private List<Integer> grossRequirements;
	private List<Integer> scheduledRequirements;
	
	private List<Integer> availInventProgrammed;
	private List<Integer> netRequirements;
	private List<Integer> receptionPlannedOrders;
	private List<Integer> releasePlannedOrders;
	
	public ProductMrpDTO() {
		
	}
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public int getLeadTime() {
		return leadTime;
	}
	public void setLeadTime(int leadTime) {
		this.leadTime = leadTime;
	}
	public int getSecurityStock() {
		return securityStock;
	}
	public void setSecurityStock(int securityStock) {
		this.securityStock = securityStock;
	}
	public int getInitialInventory() {
		return initialInventory;
	}
	public void setInitialInventory(int initialInventory) {
		this.initialInventory = initialInventory;
	}
	public double getCostOfTheArticle() {
		return costOfTheArticle;
	}
	public void setCostOfTheArticle(double costOfTheArticle) {
		this.costOfTheArticle = costOfTheArticle;
	}
	public double getMaintenanceCost() {
		return maintenanceCost;
	}
	public void setMaintenanceCost(double maintenanceCost) {
		this.maintenanceCost = maintenanceCost;
	}
	public double getOrderingCost() {
		return orderingCost;
	}
	public void setOrderingCost(double orderingCost) {
		this.orderingCost = orderingCost;
	}
	public String getLotSizingRule() {
		return lotSizingRule;
	}
	public void setLotSizingRule(String lotSizingRule) {
		this.lotSizingRule = lotSizingRule;
	}
	public String getParentProductId() {
		return parentProductId;
	}

	public void setParentProductId(String parentProductId) {
		this.parentProductId = parentProductId;
	}

	public int getAmountOfParent() {
		return amountOfParent;
	}

	public void setAmountOfParent(int amountOfParent) {
		this.amountOfParent = amountOfParent;
	}

	public List<Integer> getGrossRequirements() {
		return grossRequirements;
	}
	public void setGrossRequirements(List<Integer> grossRequirements) {
		this.grossRequirements = grossRequirements;
	}
	public List<Integer> getScheduledRequirements() {
		return scheduledRequirements;
	}
	public void setScheduledRequirements(List<Integer> scheduledRequirements) {
		this.scheduledRequirements = scheduledRequirements;
	}
	public List<Integer> getAvailInventProgrammed() {
		return availInventProgrammed;
	}
	public void setAvailInventProgrammed(List<Integer> availInventProgrammed) {
		this.availInventProgrammed = availInventProgrammed;
	}
	public List<Integer> getNetRequirements() {
		return netRequirements;
	}
	public void setNetRequirements(List<Integer> netRequirements) {
		this.netRequirements = netRequirements;
	}
	public List<Integer> getReceptionPlannedOrders() {
		return receptionPlannedOrders;
	}
	public void setReceptionPlannedOrders(List<Integer> receptionPlannedOrders) {
		this.receptionPlannedOrders = receptionPlannedOrders;
	}
	public List<Integer> getReleasePlannedOrders() {
		return releasePlannedOrders;
	}
	public void setReleasePlannedOrders(List<Integer> releasePlannedOrders) {
		this.releasePlannedOrders = releasePlannedOrders;
	}
}
