package icesi.vip.alien.dto.inventoryManagement;

import javax.validation.constraints.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import icesi.vip.alien.service.inventoryManagement.InventoryDetDemand.TimeUnit;
import lombok.Data;
@Data
public class InventorySystemDTO {
	
	public interface sqValidator {};
	public interface ssValidator {};
	public interface rsValidator {};
	public interface commonValidator{};
	
	@Positive(groups = {commonValidator.class}, message = "The average demand must be higher than 0")
	private float averageDemand;
	
	@Positive(groups = {commonValidator.class} , message = "The forecast demand must be higher than 0")
	private float forecastDemand;
	
	@Positive(groups = {rsValidator.class, ssValidator.class}, message = "This standard deviation must be higher than 0")
	private float standardDevFrcErrorsDemand;

	@Positive(groups = {commonValidator.class} , message = "The order cost must be higher than 0")
	private float orderCost;
	
	@Positive(groups = {commonValidator.class}, message = "The keeping cost must be higher than 0")
	private float keepingCost;
	
	@Positive(groups = {commonValidator.class}, message = "The lead time must be higher than 0")
	private float leadTime;
	
	@Positive(groups = {commonValidator.class}, message = "The service level must be higher than 0")
	private float serviceLevel;
	
	@PositiveOrZero(groups = {commonValidator.class}, message = "The value must be equal or higher than 0")
	private float standardDevLeadTime;
	
	@PositiveOrZero( message = "The business days must be higher than 0")
	private short businessDays;
	
	@Positive(groups = { rsValidator.class, ssValidator.class } , message = "The review cost must be higher than 0")
	private float reviewCost;
	
	@Positive(groups = {ssValidator.class},message = "The effective inventory must be higher than 0")
	private int effectiveInventory;
	
	@NotNull(groups = {commonValidator.class}, message = "Please select an option")
	private TimeUnit unitTAvgDemand;
	
	@NotNull(groups = {commonValidator.class}, message = "Please select an option")
	private TimeUnit unitTVariables;
}
