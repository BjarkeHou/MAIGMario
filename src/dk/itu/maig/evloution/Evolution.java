package dk.itu.maig.evloution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import dk.itu.maig.NN.NN;
import dk.itu.maig.agents.TestNNAgent;

/**
 * @author Aaron Gornott <agor@itu.dk>
 */
public class Evolution {
	
	// EVOLVE_GENETIC_CONTROLLER
	public static void main(String args[]) {
		int POPULATION_SIZE = 200;
		int ELITE_SIZE = 5; // elitism of best individuals
		
		// generate initial population
		Random random = new Random();
		int numberOfNewtworkWeights = new NN(6,6,6).getNumberOfConnections();
		List<Genotype> population = new ArrayList<>();		
		for(int i=0; i < POPULATION_SIZE; i++){
			population.add( new Genotype(numberOfNewtworkWeights, random) );
		}
		
		int generationCount = 0;
		while (true) { // user decides when to stop
			
			// evaluate generation			
			int numTrials=10;
            for(int i=0; i<POPULATION_SIZE; i++){
            	population.get(i).setPhenotype(new PhenotypeMario());
            	population.get(i).getPhenotype().setFitness( run(numTrials) );
            }
            population.sort(Comparator.comparing(i -> -i.getPhenotype().getFitness()));

            System.out.println("generation: "+generationCount +
                    "   fitness[best: "+population.get(0).getPhenotype().getFitness()+
                    ",  median: "+population.get(POPULATION_SIZE/2).getPhenotype().getFitness()+"]");
            System.out.println("best Genotype:\n"+population.get(0));
            
            // produce next generation
            List<Genotype> nextGeneration = new ArrayList<>();
            for(int i=0; i<ELITE_SIZE; i++){
                nextGeneration.add(population.get(i));
            }          
            int numChildren = 18; // == iteration over (47 - ELITE_SIZE) best genotypes
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
