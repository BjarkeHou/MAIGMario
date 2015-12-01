package dk.itu.maig.evloution;

import java.util.Random;

/**
 * @author Aaron Gornott <agor@itu.dk>
 */
public class Genotype {	
	private final double[] newtworkWeights;
	private PhenotypeMario phenotype;
    
	public Genotype(double[] newtworkWeights){
        this.newtworkWeights = newtworkWeights;
    }
	
	public Genotype(int numberOfNewtworkWeights, Random r){
		this.newtworkWeights = new double[numberOfNewtworkWeights];
		for(int i=0; i < numberOfNewtworkWeights; i++){
			this.newtworkWeights[i] = (r.nextDouble()*4)-2; 
		}
    }
	
	public Genotype(int numberOfNewtworkWeights, Random r, double wMin){
		this.newtworkWeights = new double[numberOfNewtworkWeights];
		for(int i=0; i < numberOfNewtworkWeights; i++){
			double weight;
			do {
				weight = r.nextDouble();
			} while(weight < wMin);
			this.newtworkWeights[i] = weight; 
		}
    }
    
    public Genotype reproduce(Genotype other, double mutateChance, Random r){
    	double[] newNewtworkWeights = new double[newtworkWeights.length];
    	for(int i=0; i < newtworkWeights.length; i++){
    		newNewtworkWeights[i] = mutate( geneticOperation(this.newtworkWeights[i], other.newtworkWeights[i], r), mutateChance, r);
    	}
        return new Genotype(newNewtworkWeights);
    }
    
    // TODO: replace hard-coded numbers with beauty
    private double geneticOperation(double gene1, double gene2, Random r){
        if(r.nextDouble() < 0.6)
            return gene1;
        else
            return gene2;
    }
    
    private double mutate(double gene, double chance, Random r){
        if(r.nextDouble() < chance){ 
            double newGene = gene + ((r.nextDouble() - 0.5));
            if(newGene >= 2) newGene = 2 - Double.MIN_VALUE; // <-- almost 2
            if(newGene < -2) newGene = -2;
            return newGene;
        }else
            return gene;
    }
    
    @Override
    public String toString(){
    	String s = "double[] newtworkWeights = {" + newtworkWeights[0];
    	for(int i=1; i < newtworkWeights.length; i++){
    		s += ", " + newtworkWeights[i];    		
    	}
    	return s + "};";
    }
    
    public PhenotypeMario getPhenotype(){
    	return phenotype;
    }
    
    public void setPhenotype(PhenotypeMario phenotype){
    	this.phenotype = phenotype;
    }
    
    public double[] getNewtworkWeights(){
    	return newtworkWeights;
    }
}
