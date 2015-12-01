package dk.itu.maig.agents;

import java.util.Random;

import ch.idsia.agents.Agent;
import ch.idsia.agents.controllers.BasicMarioAIAgent;
import ch.idsia.benchmark.mario.engine.sprites.Mario;
import dk.itu.maig.NN.NN;

public class TestNNAgent extends BasicMarioAIAgent implements Agent {

	private NN nn = null;
	Random r;
	
	public TestNNAgent(int in, int[] hidden, int out, Random r) {
		super("TestNNAgent");
		this.r = r;
		nn = new NN(in, hidden, out, r);

	}
	
	public TestNNAgent(NN nn) {
		super("TestNNAgent");
		this.nn = nn;
	}
	
	public TestNNAgent(int in, int[] hidden, int out, double[] weights) {
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
		actions[0] = false; // Left
		actions[1] = false; // Right
		actions[2] = false; // Down
		actions[3] = false; // Jump
		actions[4] = false; // Speed 
		actions[5] = false; // Up
		
		for(int i = 0; i < nnResult.length; i++) {
			//System.out.print(nnResult[i] + " ");
			if(nnResult[i] > 0.60) {
				//System.out.print(nnResult[i] + " ");
				actions[i] = !actions[i];
			}
		}
//		System.out.println();
		return actions;
	}
	
	public void updateNN(NN nn) {
		this.nn = nn;
	}
	
	private double[] NormalizeInputs() {
		/*
		 * 
		 * Grid setup
		 * 
		 * */
		
//		int width = 2, height = 3; 
//		int xOffset = 0, yOffset = -1;
//		double[] inputs = new double[(width*height)+2-1]; //+2 for isOnGround and ableToJump
//		
//		for(int x = 0; x < width; x++) {
//			for(int y = 0; y < height; y++) {
//				if(x == marioEgoCol && y == marioEgoRow) continue;
//				inputs[width * x + y] = getReceptiveFieldCellValue(marioEgoRow + x + yOffset, marioEgoCol + y + xOffset) == 1 || getEnemiesCellValue(marioEgoRow + x + yOffset, marioEgoCol + y + xOffset) == 1 ? 1 : 0;
//				//System.out.println(inputs[width * x + y]);
//			}
//		}
//		
//		inputs[(width*height)-1] = isMarioAbleToJump ? 1 : 0;
//		inputs[(width*height)] = isMarioOnGround ? 1 : 0;
		
		double[] inputs = new double[4];
//		
		inputs[0] = getReceptiveFieldCellValue(marioEgoRow, marioEgoCol + 1);
//		inputs[1] = getReceptiveFieldCellValue(marioEgoRow, marioEgoCol + 2);
//		inputs[2] = getReceptiveFieldCellValue(marioEgoRow, marioEgoCol + 3);
//		inputs[3] = getReceptiveFieldCellValue(marioEgoRow, marioEgoCol + 4);
//		inputs[4] = getReceptiveFieldCellValue(marioEgoRow, marioEgoCol + 5);
		inputs[1] = getReceptiveFieldCellValue(marioEgoRow + 1, marioEgoCol + 1);
//		inputs[6] = getReceptiveFieldCellValue(marioEgoRow + 1, marioEgoCol + 2);
//		inputs[7] = getReceptiveFieldCellValue(marioEgoRow + 1, marioEgoCol + 3);
//		inputs[8] = getReceptiveFieldCellValue(marioEgoRow + 1, marioEgoCol + 4);
//		inputs[9] = getReceptiveFieldCellValue(marioEgoRow + 1, marioEgoCol + 5);
//		inputs[10] = getEnemiesCellValue(marioEgoRow, marioEgoCol + 1);
//		inputs[11] = getEnemiesCellValue(marioEgoRow, marioEgoCol + 2);
//		inputs[12] = getEnemiesCellValue(marioEgoRow, marioEgoCol + 3);
//		inputs[13] = getEnemiesCellValue(marioEgoRow, marioEgoCol + 4);
		inputs[2] = isMarioAbleToJump ? 1 : 0;
		inputs[3] = isMarioOnGround ? 1 : 0;
//		inputs[16] = getReceptiveFieldCellValue(marioEgoRow - 1, marioEgoCol + 1);
//		inputs[17] = getReceptiveFieldCellValue(marioEgoRow - 2, marioEgoCol + 1);
		
		
//		System.out.println("---------------------------");
//		for(int x = 0; x < width; x++) {
//			for(int y = 0; y < height; y++) {
//				System.out.print(inputs[width * x + y] + " ");
//			}
//			System.out.println();
//		}
//		
//		System.out.println(inputs[inputs.length-2] + " " + inputs[inputs.length-1]);
		
		
//		inputs[0] = getReceptiveFieldCellValue(marioEgoRow + 2, marioEgoCol + 1);
//		inputs[1] = getReceptiveFieldCellValue(marioEgoRow + 1, marioEgoCol + 1);
//		inputs[2] = getReceptiveFieldCellValue(marioEgoRow, marioEgoCol + 1);
//		inputs[3] = getReceptiveFieldCellValue(marioEgoRow, marioEgoCol + 2);
//		inputs[4] = getEnemiesCellValue(marioEgoRow, marioEgoCol + 1);
//		inputs[5] = getEnemiesCellValue(marioEgoRow, marioEgoCol + 2);
//		inputs[6] = isMarioAbleToJump ? 1 : 0;
//		inputs[7] = isMarioOnGround ? 1 : 0;
				
//		for(double input : inputs) {
//			System.out.print(input + " ");
//		}
//		System.out.println("");
		
		return inputs;
	}
}
