package dk.itu.maig.NN;

public class Node {
	
	protected Connection ins[];
	protected Connection outs[];
	protected double input; //already summed and multiplied by the weights of the connections
	protected double output = 0;
	protected double threshold = 0.5;

	public Node() {
		
	};
	
	public Node(Connection[] ins, Connection[] outs) {
		this.ins = ins;
		this.outs = outs;
	}

	public void activate(){
				
		//normalize to 0-1 range
		double res = input/(ins.length+1);
		
		//set output by calling the actual activation function		
		activationFunction(res);
		
		//propagate forward, unless it's an output node
		//also summing in-place, as you go
		//when done iterating all nodes from the same layer
		//the "input" value of the nodes in the next layer
		//should be already fully updated (that is, weighted and summed)
		
		if (outs != null){
			for (Connection c : outs) {
				
				double i = c.to.input;
				i = i + res*c.weight;
				
			}
		
		}

		
	}

	/**
	 * @param res
	 * 
	 * This is the actual activation function,
	 * in our case it's a ramp
	 */
	private void activationFunction(double res) {
		
		if (res > threshold){
			output = res;
		} else {
			output = 0;
		}
	}

	public double getInput() {
		return input;
	}

	public void setInput(double input) {
		this.input = input;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
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
