package dk.itu.maig.evloution;

import dk.itu.maig.NN.NN;
import dk.itu.maig.simulator.MAIGSimulator;

public class RunStrongestController {
	
	public static void main(String args[]) throws Exception{
		
		double[] newtworkWeights = {0.2973553705368434, 0.3343693307399183, 0.6571671086529972, 0.2601411635240055, 1.0, 0.2390792503707358, 0.7615279113677541, 0.7891546650251791, 1.0, 0.10802227678279874, 0.5697125401227637, 1.0, 0.1948368102560657, 0.17249060404679442, 0.47438948143861764, 0.7417573489367256, 0.2768406400740412, 0.7600534081646062, 0.7783932620504943, 0.15528619158209733, 0.4004379870521819, 0.0, 0.24551161154261858, 0.5636497989029992, 0.35887475147138237, 0.6573192517552584, 0.4198887589901215, 0.81740807737636, 0.8005548336092918, 0.32380081134214644, 0.18510454505595447, 0.4042950810160757, 0.9308904104550886, 0.13789554183998662, 0.2194203785629771, 0.43817838274035315, 0.9014879628050707, 0.667188714124398, 0.9253710446155055, 0.433347379763123, 0.4613159890065883, 0.4295814815054674, 0.1436380188715717, 0.21291793439986606, 0.6213895438093723, 0.5658562685614323, 0.9061775366174708, 0.0030902861474199073, 0.8980827031651001, 0.0052952897224392, 0.30556101692072657, 0.7296886701783968, 0.2914926503849774, 0.5507328846322935, 0.5422076925222261, 0.0, 0.1438525147362305, 0.9879769964803726, 0.7621648829210603, 0.783406558809109, 0.6489300020371256, 0.7609982111597784, 0.3673339791365482, 0.5226204870070112, 0.5005921111088082, 0.4118821688276376, 0.6845190375431303, 0.0, 0.787065944330027, 0.33648119513673613, 0.9228378863737499, 1.0, 0.5550062208272908, 0.18287178274783877, 0.6533816034784239, 0.6755407611436307, 0.8722258402265111, 0.43574713359983885, 0.3436611859385654, 0.6669681276243198, 0.910865128463749, 0.9344688543830366, 0.656318492301492, 0.7249091786701601, 0.7151402953481737, 0.5076286291136043, 0.22100051839362966, 0.987344458785669, 0.12931859597069673, 0.9470051097357178, 0.12266879967676045, 0.7594759729346958, 0.899507221334065, 0.04124510795907399, 0.0, 0.9367086222467921, 0.3945814971619641, 0.5235838204688967, 0.4522571835363719, 0.9686471898990616, 0.3764505381083808, 0.6856896550618162, 0.25991786094167113, 0.4767674358094621, 0.5211820392358048, 0.0, 0.7492506675203258, 0.2629831938648338, 0.0, 0.60013977255245, 0.18369548429160615, 0.0, 0.41864797795827735, 0.14860941203709566, 0.0, 0.0, 0.20260670653000007, 0.0, 0.5853313511464525, 0.3040992483526376};

		Genotype genotype = new Genotype(newtworkWeights);
		NN nn = new NN(10,new int[]{7,5},3, genotype.getNewtworkWeights() );
		//Random random = new Random();
		new MAIGSimulator(true, 0, 2).simulate(nn); //TODO level seed is still static?
	}
	
}
