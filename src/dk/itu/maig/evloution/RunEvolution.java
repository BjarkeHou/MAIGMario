package dk.itu.maig.evloution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import dk.itu.maig.NN.NN;
import dk.itu.maig.simulator.MAIGSimulator;

/**
 * @author Aaron Gornott <agor@itu.dk>
 */
public class RunEvolution {
	
	static final int POPULATION_SIZE = 200;
	static final int ELITE_SIZE = POPULATION_SIZE / 40; // elitism of best individuals
	static final int REPRODUCTION_TOP = POPULATION_SIZE / 10; // reproduction count for best individual
	
	// EVOLVE_GENETIC_CONTROLLER
	public static void main(String args[]) throws Exception {
		// generate initial population
		Random random = new Random();
		int numberOfNewtworkWeights = new NN(8,7,6, random).getNumberOfConnections();
		List<Genotype> population = new ArrayList<>();		
		for(int i=0; i < POPULATION_SIZE; i++){
			population.add( new Genotype(numberOfNewtworkWeights, random) );
		}
		
		int generationCount = 0;
		while (true) { // user decides when to stop
			
			// evaluate generation			
			int numTrials=10;
            for(int i=0; i<POPULATION_SIZE; i++){
            	MAIGSimulator sim = new MAIGSimulator(false, 0, 1); //TODO level seed is still static?
            	population.get(i).setPhenotype(new PhenotypeMario(sim));
            	double score = 0;
            	for(int n=0; n < numTrials; n++){
            		score += sim.simulate(new NN(8,7,6, population.get(i).getNewtworkWeights() ));
            	}
            	score = score / numTrials;
            	//System.out.println("phenotype["+ i +"].avgFitness: " + score);
            	population.get(i).getPhenotype().setFitness(score);
            }
            population.sort(Comparator.comparing(i -> -i.getPhenotype().getFitness()));
            
            double avgPopulationFitness = 0;
            for(Genotype g : population)
            	avgPopulationFitness += g.getPhenotype().getFitness();
            avgPopulationFitness = avgPopulationFitness / POPULATION_SIZE;
            
            
            System.out.println("generation: "+generationCount +
                    "   fitness[best: "+population.get(0).getPhenotype().getFitness()+
                    ",  median: "+population.get(POPULATION_SIZE/2).getPhenotype().getFitness()+
                    ", avg:" + avgPopulationFitness + "]");
            System.out.println("best Genotype:\n"+population.get(0));
            
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
                    Genotype newGenotype = population.get(index).reproduce(population.get(partnerID), random);
                    nextGeneration.add(newGenotype);
                }
                if(numChildren > 1) { numChildren--; }
                index++;
            }
            population = nextGeneration;
            generationCount++;
		}
	}

}
