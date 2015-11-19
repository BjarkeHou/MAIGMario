package dk.itu.maig.NN;

import java.util.ArrayList;
import java.util.Random;

public class NN {
	
	ArrayList<Node> inputNodes = new ArrayList<Node>();
	ArrayList<ArrayList<Node>> hiddenNodes = new ArrayList<ArrayList<Node>>();
	ArrayList<Node> outputNodes = new ArrayList<Node>();
	ArrayList<Connection> connections = new ArrayList<Connection>();
	
	
	// Creates a NN with random weights
	public NN(int in, int[] hidden, int out, Random r) {
		createNodes(in, hidden, out);		
		InitializeNetwork();
		RandomizeWeights(r);
	}
	
	// Creates a NN with random weights
	public NN(int in, int[] hidden, int out, double[] weights) throws Exception {
		createNodes(in, hidden, out);		
		InitializeNetwork();
		SetWeights(weights);
	}
	
	public double[] run(double[] inputs) throws Exception {
		if(inputs.length != inputNodes.size())
			throw new Exception("Number of inputs (" + inputs.length + ") are not equal to number of input nodes ("+inputNodes.size()+").");
		
		double[] outputs = new double[outputNodes.size()];
		
		for (int i = 0; i < inputs.length; i++) {
			inputNodes.get(i).setInput(inputs[i]);
			inputNodes.get(i).activate();
		}
		
		for (ArrayList<Node> hiddenNodeLayer : hiddenNodes) {
			for (Node hiddenNode : hiddenNodeLayer) {
				hiddenNode.activate();
			}
		}
		
		for (int i = 0; i < outputs.length; i++) {
			outputNodes.get(i).activate();
			outputs[i] = outputNodes.get(i).getOutput();
		}
		
		return outputs;
	}

	private void createNodes(int in, int[] hidden, int out) {
		for(int i = 0; i < in; i++) {
			inputNodes.add(new Node());
		}
		for(int i = 0; i < hidden.length; i++) {
			// For each layer, add an array list
			hiddenNodes.add(i, new ArrayList<Node>());
			for(int j = 0; j < hidden[i]; j++) {
				// in that layer, add the desired amount of nodes
				hiddenNodes.get(i).add(new Node());
			}
		}
		for(int i = 0; i < out; i++) {
			outputNodes.add(new Node());
		}
	}
	
	// Randomizes weights of the NN network
	public void RandomizeWeights(Random r) {
		for (Connection connection : connections) {
			double weight = r.nextDouble();
			connection.setWeight(weight);
		}
	}
	
	public void SetWeights(double[] weights) throws Exception {
		if(weights.length == connections.size()) {
			for (int i = 0; i < weights.length; i++) {
				connections.get(i).setWeight(weights[i]);
			}
		} else {
			throw new Exception("Number of weights are not equal with number of connections.");
		}
	}

	// Creates connections between every input to every hidden
	// and every hidden to every output.
	private void InitializeNetwork() {
		for (Node inputNode : inputNodes) {
			for (Node hiddenNode : hiddenNodes.get(0)) {
				Connection newConn = new Connection(inputNode, hiddenNode);
				connections.add(newConn);
				inputNode.addOut(newConn);
				hiddenNode.addIn(newConn);
			}
		}
		
		for (ArrayList<Node> hiddenNodeLayer : hiddenNodes) {
			if(hiddenNodes.indexOf(hiddenNodeLayer) == hiddenNodes.size() - 1)
				break;
			
			for (Node hiddenNodeLowerLayer : hiddenNodeLayer) {
				for (Node hiddenNodeUpperLayer : hiddenNodes.get(hiddenNodes.indexOf(hiddenNodeLayer) + 1)) {
					Connection newConn = new Connection(hiddenNodeLowerLayer, hiddenNodeUpperLayer);
					connections.add(newConn);
					hiddenNodeLowerLayer.addOut(newConn);
					hiddenNodeUpperLayer.addIn(newConn);
				}
//				if(hiddenNodes.indexOf(hiddenNodeLayer) == hiddenNodes.size()-1)
//					break;
			}
		}
		
		for (Node hiddenNode : hiddenNodes.get(hiddenNodes.size()-1)) {
			for (Node outputNode : outputNodes) {
				Connection newConn = new Connection(hiddenNode, outputNode); 
				connections.add(newConn);
				hiddenNode.addOut(newConn);
				outputNode.addIn(newConn);
			}
		}
	}

	public int getNumberOfConnections() {
		return connections.size();
	}
	
}
