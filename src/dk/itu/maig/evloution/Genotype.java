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
			this.newtworkWeights[i] = r.nextDouble(); 
		}
    }
    
    public Genotype reproduce(Genotype other, Random r){
    	double[] newNewtworkWeights = new double[newtworkWeights.length];
    	for(int i=0; i < newtworkWeights.length; i++){
    		newNewtworkWeights[i] = mutate( geneticOperation(this.newtworkWeights[i], other.newtworkWeights[i], r), r);
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
    
    private double mutate(double gene, Random r){
        if(r.nextDouble() < 0.025){ // 2,5%
            double newGene = gene + ((r.nextDouble() - 0.5) * 0.3 );
            if(newGene >= 1) newGene = 1 - Double.MIN_VALUE ; // <-- almost 1
            if(newGene < 0) newGene = 0;
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
}
