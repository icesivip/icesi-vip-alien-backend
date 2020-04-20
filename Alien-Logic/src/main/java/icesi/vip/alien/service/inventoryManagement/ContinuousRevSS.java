package icesi.vip.alien.service.inventoryManagement;

public class ContinuousRevSS implements InventorySystem{

	private int maxLevelInventory;
	private int minLevelInventory;
	
	@Override
	public double calculateQuantity() {
			return maxLevelInventory - minLevelInventory;
	}

	public int getMaxLevelInventory() {
		return maxLevelInventory;
	}

	public void setMaxLevelInventory(int maxLevelInventory) {
		this.maxLevelInventory = maxLevelInventory;
	}

	public int getMinLevelInventory() {
		return minLevelInventory;
	}

	public void setMinLevelInventory(int minLevelInventory) {
		this.minLevelInventory = minLevelInventory;
	}

}
