package dk.itu.maig.evloution;

import dk.itu.maig.simulator.MAIGSimulator;

/**
 * @author Aaron Gornott <agor@itu.dk>
 */
public class PhenotypeMario {
	private double fitness;
	private MAIGSimulator sim;
	
	public PhenotypeMario(MAIGSimulator sim){
		this.sim = sim;
		
	}
	
	// TODO:
	// this should be the place to "Decide on inputs and normalize inputs"
	
	// TODO:
	// the actual NN (structure) should be build here ??? or not?
//	public double run(){
//		return sim.simulate(new NN(6,6,6, population.get(i).getNewtworkWeights() ));
//	}
	
	
	public double getFitness(){
		return fitness;
	}
	
	public void setFitness(double fitness){
		this.fitness = fitness;
	}

}
