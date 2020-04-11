package icesi.vip.alien.service.inventoryManagement;

public class ContinuousRevSS implements InventorySystem{

	private float maxLevelInventory;
	private float minLevelInventory;
	
	@Override
	public double calculateQuantity() {
			return maxLevelInventory - minLevelInventory;
	}

	public float getMaxLevelInventory() {
		return maxLevelInventory;
	}

	public void setMaxLevelInventory(float maxLevelInventory) {
		this.maxLevelInventory = maxLevelInventory;
	}

	public float getMinLevelInventory() {
		return minLevelInventory;
	}

	public void setMinLevelInventory(float minLevelInventory) {
		this.minLevelInventory = minLevelInventory;
	}

}
