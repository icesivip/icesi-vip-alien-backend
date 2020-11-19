package icesi.vip.alien.service.routing;

public class Point {
	
	private double[][] s;
	private int i;
	private int j;
	
	public Point(double[][] s, int i, int j) {
		this.s = s;
		this.i = i;
		this.j = j;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

	public double[][] getS() {
		return s;
	}

	public void setS(double[][] s) {
		this.s = s;
	}

	
}
