package icesi.vip.alien.service.craftMethod;

public class LabelMapNode {
	
	public int value;
	public int iIndex;
	public int jIndex;
	
	public LabelMapNode right;
	public LabelMapNode left;
	public LabelMapNode up;
	public LabelMapNode down;
	
	
	public LabelMapNode(int value,int i ,int j) {
		this.value=value;
		this.iIndex=i;
		this.jIndex=j;
	}
}
