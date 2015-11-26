package dk.itu.maig.NN;

import java.util.ArrayList;

public class Node {
	
	protected ArrayList<Connection> ins;
	protected ArrayList<Connection> outs;
	protected double input; //already summed and multiplied by the weights of the connections
	protected double output = 0;
	protected double threshold = 0.2;

	public Node() {
	}
	
	public Node(ArrayList<Connection> ins, ArrayList<Connection> outs) {
		this.ins = ins;
		this.outs = outs;
	}

	public void activate(){
		
		//temp var
		double res = 0.0;
		
		//normalize to 0-1 range
		if (ins !=null){
			res = input/ins.size();
			//System.out.println(res);
		}else{
			res = input;
		}
		//set output by calling the actual activation function		
		activationFunction(res);
		
		//propagate forward, unless it's an output node
		//also summing in-place, as you go
		//when done iterating all nodes from the same layer
		//the "input" value of the nodes in the next layer
		//should be already fully updated (that is, weighted and summed)
		
		if (outs != null){
			for (Connection c : outs) {
				double i = c.to.getInput();
				i = i + output*c.weight;
				c.to.setInput(i);
			}
		}
	}
	
	public void resetNode() {
		input = 0;
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

	public void addIn(Connection in){
		if(ins == null){
			ins = new ArrayList<Connection>();
		}
		ins.add(in);
	}

	public void addOut(Connection in){
		if(outs == null){
			outs = new ArrayList<Connection>();
		}
		outs.add(in);
	}
	
	public ArrayList<Connection> getIns() {
		return ins;
	}

	public void setIns(ArrayList<Connection> ins) {
		this.ins = ins;
	}

	public ArrayList<Connection> getOuts() {
		return outs;
	}

	public void setOuts(ArrayList<Connection> outs) {
		this.outs = outs;
	}

	public double getOutput() {
		return output;
	}

	public void setOutput(double output) {
		this.output = output;
	}
	
}
