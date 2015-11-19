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
	
	static final int POPULATION_SIZE = 200;
	static final int ELITE_SIZE = POPULATION_SIZE / 40; // elitism of best individuals
	static final int REPRODUCTION_TOP = POPULATION_SIZE / 11; // reproduction count for best individual (next best x--; until x==1)
	static final int NUM_EVAL_TRIALS=1; // TODO:  = 10; // run count for avg evaluation
	
	static final int levelSeed = 2;
	static final boolean visual = false;
	
	static final int inNodes = 18;
	static final int[] hiddenNodes = {15, 10};
	static final int outNodes = 6; // NN def
	
	static final boolean saveFitnessDataToFile = true;
	static final boolean saveBestControllerToFile = true;
	
	
	// EVOLVE_GENETIC_CONTROLLER
	public static void main(String args[]) throws Exception {
		// generate initial population
		Random random = new Random();
		int numberOfNewtworkWeights = new NN(inNodes,hiddenNodes,outNodes, random).getNumberOfConnections();
		List<Genotype> population = new ArrayList<>();		
		for(int i=0; i < POPULATION_SIZE; i++){
			population.add( new Genotype(numberOfNewtworkWeights, random) );
		}
		
		PrintWriter writeFitness = null;
		PrintWriter writeController = null;
		if(saveFitnessDataToFile || saveBestControllerToFile) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
			Date date = new Date();
			String folderName = "level" + levelSeed + "-" + dateFormat.format(date);
			File folder = new File(folderName);
			
			if(!folder.exists())
				folder.mkdirs();
			
			if(saveFitnessDataToFile)
				writeFitness = new PrintWriter(folderName + "/evolution_data.txt", "UTF-8");
			if(saveBestControllerToFile)
				writeController = new PrintWriter(folderName + "/controller_data.txt", "UTF-8");
		}
		
		int generationCount = 0;
		while (true) { // user decides when to stop
			
			// evaluate generation
            for(int i=0; i<POPULATION_SIZE; i++){
            	MAIGSimulator sim;
//            	if(i == 0 && generationCount%50 == 0) {
//            		 sim = new MAIGSimulator(true, 0, levelSeed); //TODO level seed is still static? do random later!
//            	} else {
            		sim = new MAIGSimulator(false, 0, levelSeed); //TODO level seed is still static? do random later!
//            	}
            	
            	population.get(i).setPhenotype(new PhenotypeMario(sim));
            	double score = 0;
            	for(int n=0; n < NUM_EVAL_TRIALS; n++){
            		score += sim.simulate(new NN(inNodes,hiddenNodes,outNodes, population.get(i).getNewtworkWeights() ));
            	}
            	score = score / NUM_EVAL_TRIALS;
            	//System.out.println("phenotype["+ i +"].avgFitness: " + score);
            	population.get(i).getPhenotype().setFitness(score);
            }
            population.sort(Comparator.comparing(i -> -i.getPhenotype().getFitness()));
            
            if(saveFitnessDataToFile) {
            	double avgPopulationFitness = 0;
                for(Genotype g : population)
                	avgPopulationFitness += g.getPhenotype().getFitness();
                avgPopulationFitness = avgPopulationFitness / POPULATION_SIZE;
                
            	writeFitness.println(generationCount + " " + population.get(0).getPhenotype().getFitness() + " " + avgPopulationFitness);
            	System.out.println("generation: " + generationCount + " best: " + population.get(0).getPhenotype().getFitness() + " avg: " + avgPopulationFitness);
            }
            
            if(saveBestControllerToFile && generationCount%10 == 0) {
            	writeController.println(generationCount + " " + population.get(0));
            }
            
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
	}

}
