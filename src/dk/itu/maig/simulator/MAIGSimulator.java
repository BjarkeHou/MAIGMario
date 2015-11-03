package dk.itu.maig.simulator;

import ch.idsia.benchmark.mario.environments.Environment;
import ch.idsia.benchmark.mario.environments.MarioEnvironment;
import ch.idsia.tools.MarioAIOptions;
import dk.itu.maig.NN.NN;
import dk.itu.maig.agents.TestNNAgent;

public class MAIGSimulator
{
	boolean test = false;
	int marioStartState; // 0 = small, 1 = large, 2 = flower
	boolean visual;
	int levelRandomizationSeed;
	TestNNAgent agent = null;
	String options = "";
	
	public MAIGSimulator(boolean visual, int marioStartState, int levelRandomizationSeed) {
		this.marioStartState = marioStartState;
		this.visual = visual;
		this.levelRandomizationSeed = levelRandomizationSeed;
		
		options = visual ? options + "-vis on " : options + "-vis off ";
		options = options + "-ls " + levelRandomizationSeed + " ";
		options = options + "-mm " + marioStartState + " ";
		if(test) {
			options = "-mm 0 -vis on -lco off -lb on -le off -lhb off -lg off -ltb off -lhs off -lde off";
		}
	}
	
	// Runs the game with the provided NN and returns a fitness function.
	public float simulate(NN nn) {
		if(agent == null) {
			agent = new TestNNAgent(nn);
		} else {
			agent.updateNN(nn);
		}
		
		Environment environment = MarioEnvironment.getInstance();
		MarioAIOptions opt = new MarioAIOptions(options);
		opt.setAgent(agent);
	    environment.reset(opt);
		
		// Game loop
		while (!environment.isLevelFinished())
	    {
	        environment.tick();
	        agent.integrateObservation(environment);
	        environment.performAction(agent.getAction());
	    }
	

//		System.out.println("Evaluation Info:");
//	    int[] ev = environment.getEvaluationInfoAsInts();
//	    for (int anEv : ev)
//	    {
//	        System.out.print(anEv + ", ");
//	    }
		float[] marioPos = environment.getMarioFloatPos(); 
	    return marioPos[0]; // X value.
	}
}