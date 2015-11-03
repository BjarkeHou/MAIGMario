package dk.itu.maig.agents;

import java.util.Random;

import ch.idsia.agents.Agent;
import ch.idsia.agents.controllers.BasicMarioAIAgent;
import ch.idsia.benchmark.mario.engine.sprites.Mario;
import dk.itu.maig.NN.NN;

public class TestNNAgent extends BasicMarioAIAgent implements Agent {

	private NN nn = null;
	Random r;
	
	public TestNNAgent(int in, int hidden, int out, Random r) {
		super("TestNNAgent");
		this.r = r;
		nn = new NN(in, hidden, out, r);

	}
	
	public TestNNAgent(NN nn) {
		super("TestNNAgent");
		this.nn = nn;
	}
	
	public TestNNAgent(int in, int hidden, int out, double[] weights) {
		super("TestNNAgent");
		try {
			nn = new NN(in, hidden, out, weights);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean[] getAction()
	{
		// Normalize input values
		double[] normalizedInputs = NormalizeInputs();
		
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
	
	public void updateNN(NN nn) {
		this.nn = nn;
	}
	
	private double[] NormalizeInputs() {
		double[] inputs = new double[8];
		inputs[0] = getReceptiveFieldCellValue(marioEgoRow + 2, marioEgoCol + 1);
		inputs[1] = getReceptiveFieldCellValue(marioEgoRow + 1, marioEgoCol + 1);
		inputs[2] = getReceptiveFieldCellValue(marioEgoRow, marioEgoCol + 1);
		inputs[3] = getReceptiveFieldCellValue(marioEgoRow, marioEgoCol + 2);
		inputs[4] = getEnemiesCellValue(marioEgoRow, marioEgoCol + 1);
		inputs[5] = getEnemiesCellValue(marioEgoRow, marioEgoCol + 2);
		inputs[6] = isMarioAbleToJump ? 1 : 0;
		inputs[7] = isMarioOnGround ? 1 : 0;
				
//		for(double input : inputs) {
//			System.out.print(input + " ");
//		}
//		System.out.println("");
		
		return inputs;
	}
}
