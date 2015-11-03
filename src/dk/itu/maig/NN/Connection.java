package dk.itu.maig.NN;

public class Connection {

	protected double weight = 0.5;
	protected Node from = null;
	protected Node to = null;
	
	public Connection(Node from, Node to) {
		this.from = from;
		this.to = to;
		// TODO Auto-generated constructor stub
	}

	public Connection(Node from, Node to, double weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
		// TODO Auto-generated constructor stub
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	
	
}
