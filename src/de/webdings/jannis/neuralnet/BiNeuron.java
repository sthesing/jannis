/* BiNeuron.java - Copyright (c) 2005 by Stefan Thesing
 <p>This file is part of Jannis.</p>
 <p>Jannis is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.</p>
<p>Jannis is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.</p>
<p>You should have received a copy of the GNU General Public License
along with Jannis; if not, write to the<br>
Free Software Foundation, Inc.,<br>
51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA<br>
*/
package de.webdings.jannis.neuralnet;



/**
 * BiNeuron is a concrete subclass of {@link Neuron}.
 * The activation function of this neuron type is a
 * binary threshhold function. This means that neurons
 * of this type have a threshhold value <code>sigma</code>.
 * The neuron fires if the overall activation the neuron 
 * receives is equal or higher than <code>sigma</code>. 
 * 
 * @author Stefan Thesing<br>
 * Website: <a href="http://www.webdings.de">http://www.webdings.de</a>
 * @version 0.1 10.08.2005
 */
public class BiNeuron extends Neuron {
    //attributes
    /**
     * <code>sigma</code> is the activation treshold value
     * of the BiNeuron. If the net activation for this 
     * BiNeuron is equal or higher than sigma, 
     * {@link #tresholdReached() tresholdReached} 
     * will return <code>true</code>
     */
    protected float sigma;
    
    //constructors
    /**
     * Constructs a BiNeuron using the specified value for sigma and
     * using default values for the attributes inherited from 
     * {@link Neuron}:<br>
     * a=0<br>
     * net=0<br>
     * fired=false<br>
     * shouldHaveFired=<code>false</code><br>
     * numberOfConnections=0
     * @param sigma
     */
    public BiNeuron(float sigma) {
       this.sigma = sigma;
       a = 0;
       net = 0;
       numberOfConnections = 0;
       connections = new Synapse[numberOfConnections];
       fired = false;
    }
    
    /**
     * Constructs a BiNeuron using default values:<br>
     * a=0<br>
     * net=0<br>
     * fired=false<br>
     * shouldHaveFired=<code>false</code><br>
     * numberOfConnections=0<br>
     * sigma=0.8
     */
    public BiNeuron() {
    	this(0.8f);
    }
    //methods
    /** 
     * represents the activations function of the 
     * neuron.
     * @return <code>true</code> if the net activation this neuron 
     * receives is equal or higher than sigma. 
     */
    public boolean tresholdReached() {
        return(net>=sigma);
    }

    /**
     * @return Returns sigma.
     */
    public float getSigma() {
        return sigma;
    }
    /**
     * @param sigma The value for sigma to set.
     */
    public void setSigma(float sigma) {
        this.sigma = sigma;
    }

	/**
	 * For <code>BiNeurons</code>, this function returns a String containing
	 * sigma.
	 * @see de.webdings.jannis.neuralnet.Neuron#getActivationFunction()
	 * 
	 */
	public String getActivationFunction() {
		return Float.toString(this.getSigma());
	}
}
