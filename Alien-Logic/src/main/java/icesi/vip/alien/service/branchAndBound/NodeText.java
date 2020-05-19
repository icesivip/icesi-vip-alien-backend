package icesi.vip.alien.service.branchAndBound;

public class NodeText {

	/*
	 * Will be used for Level of Node
	 */
	private String name;
	/*
	 * Will be used for model description
	 */
	private String title;
	/*
	 * Will describe solution
	 */
	private String desc;
	
	private String addedConstraint;
	

	
	
	public NodeText(String name, String title,String desc,String addedConstraint) {
		this.name=name;
		this.title=title;
		this.addedConstraint=addedConstraint;
		if(desc==null) {
			desc="UNFEASIBLE";
		}else {
			this.desc=desc;
		}
		
	}


	public String getName() {
		return name;
	}
	
	public String getAddedConstraint() {
		return this.addedConstraint;
	}


	public String getTitle() {
		return title;
	}
	
	public String getDesc() {
		return desc;
	}
	
	
	
	
	
}
