package dk.itu.maig.simulator;

import ch.idsia.agents.Agent;
import ch.idsia.benchmark.mario.environments.Environment;
import ch.idsia.benchmark.mario.environments.MarioEnvironment;
import dk.itu.maig.NN.NN;
import dk.itu.maig.agents.TestNNAgent;

public class MAIGSimulator
{
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
	}
	
	// Runs the game with the provided NN and returns a fitness function.
	public int simulate(NN nn) {
		if(agent == null) {
			agent = new TestNNAgent(nn);
		} else {
			agent.updateNN(nn);
		}
		
		Environment environment = MarioEnvironment.getInstance();
	    environment.reset(options);
		
		// Game loop
		while (!environment.isLevelFinished())
	    {
	        environment.tick();
	        agent.integrateObservation(environment);
	        environment.performAction(agent.getAction());
	    }
	
		System.out.println("Evaluation Info:");
	    int[] ev = environment.getEvaluationInfoAsInts();
	    for (int anEv : ev)
	    {
	        System.out.print(anEv + ", ");
	    }
		int[] marioPos = environment.getMarioEgoPos(); 
	    return marioPos[0]; // X value.
	}
}