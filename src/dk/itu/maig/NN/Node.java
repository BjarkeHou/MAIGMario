package dk.itu.maig.NN;

public abstract class Node {
	
	protected Connection ins[];
	protected Connection outs[];
	protected double output = 0;
	protected double threshold = 0.5;

	public Node(Connection[] ins, Connection[] outs) {
		this.ins = ins;
		this.outs = outs;
		// TODO Auto-generated constructor stub
	}

	public void activate(){
		
		double res = 0;
		
		for (Connection c : ins) {
			
			res+=c.from.output*c.weight;
			
		}
		
		res = res/(ins.length+1);
		
		if (res > threshold){
			output = res;
		} else {
			output = 0;
		}
		
	}

	public Connection[] getIns() {
		return ins;
	}

	public void setIns(Connection[] ins) {
		this.ins = ins;
	}

	public Connection[] getOuts() {
		return outs;
	}

	public void setOuts(Connection[] outs) {
		this.outs = outs;
	}

	public double getOutput() {
		return output;
	}

	public void setOutput(double output) {
		this.output = output;
	}
	
}
