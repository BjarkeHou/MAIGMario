/*
 * Copyright (c) 2009-2010, Sergey Karakovskiy and Julian Togelius
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Mario AI nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package dk.itu.maig.simulator;

import ch.idsia.agents.Agent;
import ch.idsia.agents.controllers.BasicMarioAIAgent;
import ch.idsia.agents.controllers.ForwardAgent;
import ch.idsia.agents.controllers.ScaredAgent;
import ch.idsia.benchmark.mario.environments.Environment;
import ch.idsia.benchmark.mario.environments.MarioEnvironment;
import ch.idsia.tools.MarioAIOptions;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey Karakovskiy, sergey at idsia dot ch
 * Date: Mar 1, 2010 Time: 1:50:37 PM
 * Package: ch.idsia.scenarios
 */

public class MAIGSimulator
{
public static void main(String[] args)
{
	
	int marioStartState = 1; // 0 = small, 1 = large, 2 = flower
	boolean visual = true;
	boolean flatLevel = false;
	int levelRandomizationSeed = 16;
	
	String options = "";
	options = visual ? options + "-vis on " : options + "-vis off ";
	options = flatLevel ? options + "-lf on " : options + "-lf off ";
	options = options + "-ls " + levelRandomizationSeed + " ";
	options = options + "-mm " + marioStartState + " ";
	
    MarioAIOptions marioAIOptions = new MarioAIOptions(args);
    marioAIOptions.setMarioInvulnerable(true);
    System.out.print(options);
    Environment environment = MarioEnvironment.getInstance();
    Agent agent = new ScaredAgent();
    environment.reset(options);
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
//        }
    System.exit(0);
}

}