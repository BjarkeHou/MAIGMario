package dk.itu.maig.agents;

import java.util.Random;

import ch.idsia.agents.Agent;
import ch.idsia.agents.controllers.BasicMarioAIAgent;
import ch.idsia.benchmark.mario.engine.sprites.Mario;
import dk.itu.maig.NN.NN;

public class TestNNAgent extends BasicMarioAIAgent implements Agent {

	private NN nn = null;
	
	public TestNNAgent(int in, int hidden, int out, Random r) {
		super("Test NN Agent");
		nn = new NN(in, hidden, out, r);
	}
	
	public TestNNAgent(int in, int hidden, int out, double[] weights) {
		super("Test NN Agent");
		try {
			nn = new NN(in, hidden, out, weights);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean[] getAction()
	{
		// Normalize input values
		double[] normalizedInputs = new double[5];
		// Feed NN with input values and receive output values
		double[] nnResult;
		try {
			nnResult = nn.run(normalizedInputs);
			// Decode output values
			action = decode(nnResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return action array according to the decoded output values
		return action;
	}

	private boolean[] decode(double[] nnResult) {
		boolean[] actions = new boolean[nnResult.length];
		for(int i = 0; i < nnResult.length; i++) {
			if(nnResult[i] > 0.5) {
				actions[i] = true;
			} else {
				actions[i] = false;
			}
		}
		
		return actions;
	}
}
