package icesi.vip.alien.dto.inventoryManagement;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import icesi.vip.alien.service.inventoryManagement.InventoryDetDemand.TimeUnit;
import lombok.Data;
@Data
public class InventorySystemDTO {
	
	@NotNull(message = "Write some shit here")
	@Positive(message = "The fckin demand must be more than 0")
	private Float demand;
	private float orderCost;
	private float keepingCost;
	private float leadTime;
	private float serviceLevel;
	private float standardDeviationDemand;
	private float standardDeviationLeadTime;
	private short businessDays;
	private int maxLevelInventory;
	private int minLevelInventory;
	private short reviewTime;
	private int availableInventory;
	private TimeUnit demandType;
}
