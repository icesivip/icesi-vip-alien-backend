package icesi.vip.alien.dto.craftMethod;

public class DistributionResult {
	
	public int[][] distribution;

	public double cost;
	public DistributionResult(int[][] distribution, double cost) {
		super();
		this.distribution = distribution;
		this.cost=cost;
	}
	
	
	
}
