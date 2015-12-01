package dk.itu.maig.evloution;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import dk.itu.maig.NN.NN;
import dk.itu.maig.simulator.MAIGSimulator;

/**
 * @author Aaron Gornott <agor@itu.dk>
 */
public class RunEvolution {
	
	static final int POPULATION_SIZE = 400;
	static final int ELITE_SIZE = POPULATION_SIZE / 40; // elitism of best individuals
	static final int REPRODUCTION_TOP = POPULATION_SIZE / 11; // reproduction count for best individual (next best x--; until x==1)
	static final int NUM_EVAL_TRIALS=1; // TODO:  = 10; // run count for avg evaluation
	
	static final double AVG_FITNESS_CHANGE_THRESHOLD = 5;
	static final int MUTATION_CHANGE_THRESHOLD = 50; 
	static final double MUTATION_CHANGE = 0.025;
	
	static final int[] randomSeed = {1,2,3,4,5};
	static final int levelSeed = 1;
	static final boolean visual = false;
	
	static final int inNodes = 4;
	static final int[] hiddenNodes = {5};
	static final int outNodes = 6; // NN def
	
	// EVOLVE_GENETIC_CONTROLLER
	public static void main(String args[]) throws Exception {
		for(int seedCounter = 0; seedCounter < randomSeed.length; seedCounter++) {
			// generate initial population
			Random random = new Random(randomSeed[seedCounter]);
			
			int numberOfNewtworkWeights = new NN(inNodes,hiddenNodes,outNodes, random).getNumberOfConnections();
			List<Genotype> population = new ArrayList<>();		
			for(int i=0; i < POPULATION_SIZE; i++){
				population.add( new Genotype(numberOfNewtworkWeights, random) );
			}
			
			int generationCount = 0;
			double mutation_chance = 0.05; // 10 percent chance for each connection to mutate.

			MAIGSimulator sim;
			sim = new MAIGSimulator(false, 0, levelSeed);
			while (generationCount < 100) { // user decides when to stop
				
//				if(generationCount%20 == 0 && generationCount != 0)
//					sim = new MAIGSimulator(true, 0, levelSeed);
//				else
//					sim = new MAIGSimulator(false, 0, levelSeed);
				
				// evaluate generation
	            for(int i=0; i<POPULATION_SIZE; i++){
	            	double score = 0;
	            	for(int n=0; n < NUM_EVAL_TRIALS; n++){
	                	population.get(i).setPhenotype(new PhenotypeMario(sim));
	            		score += sim.simulate(new NN(inNodes,hiddenNodes,outNodes, population.get(i).getNewtworkWeights() ));
	            	}
	            	score = score / NUM_EVAL_TRIALS;
	            	population.get(i).getPhenotype().setFitness(score);
	            }
	            population.sort(Comparator.comparing(i -> -i.getPhenotype().getFitness()));
	            
	            double avgPopulationFitness = 0;
	            for(Genotype g : population)
	            	avgPopulationFitness += g.getPhenotype().getFitness();
	            avgPopulationFitness = avgPopulationFitness / POPULATION_SIZE;
	            
	            System.out.println("generation: " + generationCount + " best: " + population.get(0).getPhenotype().getFitness() + " avg: " + avgPopulationFitness);
	            
	            // produce next generation
	            List<Genotype> nextGeneration = new ArrayList<>();
	            for(int i=0; i<ELITE_SIZE; i++){
	                nextGeneration.add(population.get(i));
	            }
	            int numChildren = REPRODUCTION_TOP;
	            int index = 0;
	            random = new Random();
	            while(nextGeneration.size() < POPULATION_SIZE) {
	                for(int n=0; n < numChildren; n++){
	                    // find crossover partners, incl. self
	                    int partnerID = random.nextInt(POPULATION_SIZE);
	                    Genotype newGenotype = population.get(index).reproduce(population.get(partnerID), mutation_chance, random);
	                    if(nextGeneration.size() < POPULATION_SIZE) // safty feature
	                    	nextGeneration.add(newGenotype);
	                    else
	                    	break;
	                }
	                if(numChildren > 1) { numChildren--; }
	                index++;
	            }
	            population = nextGeneration;
	            generationCount++;
			}
			System.out.println("---------------------------------------------------------------------------");
		}	
	}
}
