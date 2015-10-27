package dk.itu.maig.NN;

import java.util.ArrayList;
import java.util.Random;

public class NN {
	
	ArrayList<InNode> inputNodes = new ArrayList<InNode>();
	ArrayList<HiddenNode> hiddenNodes = new ArrayList<HiddenNode>();
	ArrayList<OutNode> outputNodes = new ArrayList<OutNode>();
	ArrayList<Connection> connections = new ArrayList<Connection>();
	
	Random rand = new Random();
	
	// Creates a NN with random weights
	public NN(int in, int hidden, int out) {
		createNodes(in, hidden, out);		
		InitializeNetwork();
		RandomizeWeights();
	}
	
	// Creates a NN with random weights
	public NN(int in, int hidden, int out, double[] weights) throws Exception {
		createNodes(in, hidden, out);		
		InitializeNetwork();
		SetWeights(weights);
	}
	
	public double[] run(double[] inputs) throws Exception {
		if(inputs.length != inputNodes.size())
			throw new Exception("Number of inputs are not equal to number of input nodes.");
		
		double[] outputs = new double[outputNodes.size()];
		
		for (int i = 0; i < inputs.length; i++) {
			// TODO: Uncomment line
			//inputNodes.get(i).setInput(inputs[i]);
			//inputNodes.get(i).activate();
		}
		
		for (HiddenNode hiddenNode : hiddenNodes) {
			//TODO: Uncomment line
			//hiddenNode.activate();
		}
		
		for (int i = 0; i < outputs.length; i++) {
			//TODO: Uncomment line
			//outputNodes.get(i).activate();
			//outputs[i] = outputNodes.get(i).getOutput();
		}
		
		return outputs;
	}

	private void createNodes(int in, int hidden, int out) {
		for(int i = 0; i < in; i++) {
			inputNodes.add(new InNode());
		}
		for(int i = 0; i < hidden; i++) {
			hiddenNodes.add(new HiddenNode());
		}
		for(int i = 0; i < out; i++) {
			outputNodes.add(new OutNode());
		}
	}
	
	// Randomizes weights of the NN network
	public void RandomizeWeights() {
		for (Connection connection : connections) {
			double weight = rand.nextDouble();
			//TODO: Uncomment line
			//connection.setWeight(weight);
		}
	}
	
	public void SetWeights(double[] weights) throws Exception {
		if(weights.length == connections.size()) {
			for (int i = 0; i < weights.length; i++) {
				//TODO: Uncomment line
				//connections.get(i).setWeight(weights[i]);
			}
		} else {
			throw new Exception("Number of weights are not equal with number of connections.");
		}
	}

	// Creates connections between every input to every hidden
	// and every hidden to every output.
	private void InitializeNetwork() {
		for (InNode inputNode : inputNodes) {
			for (HiddenNode hiddenNode : hiddenNodes) {
				connections.add(new Connection()); //TODO: Set connection to be from input node to hidden node
			}
		}
		
		for (HiddenNode hiddenNode : hiddenNodes) {
			for (OutNode outputNode : outputNodes) {
				connections.add(new Connection()); //TODO: Set connection to be from input node to hidden node
			}
		}
	}

	public int getNumberOfConnections() {
		return connections.size();
	}
	
}