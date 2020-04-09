package icesi.vip.alien.dto.craftMethod;

import java.util.ArrayList;
import java.util.Map;

import icesi.vip.alien.service.craftMethod.FacilityDistribution;

public class CraftResponse {
	
	public ArrayList<DistributionResult> distributions;
	public double optimalCost;
	public int[][] optimalDistribution;
	
	public CraftResponse(Map<FacilityDistribution, Double> distributions, double optimalCost, FacilityDistribution optimalDistribution) {
		super();
		this.distributions = new ArrayList<>();
		for(FacilityDistribution dist : distributions.keySet()) {
			int[][]out=dist.outputDistribution();
			this.distributions.add(new DistributionResult(out, distributions.get(dist)));
		}
		this.optimalCost = optimalCost;
		this.optimalDistribution = optimalDistribution.outputDistribution();
	}

}
