/* Synapse.java - Copyright (c) 2005 by Stefan Thesing
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
 * Synapse is used to represent a connection between
 * two {@link Neuron}s.
 * 
 * @author Stefan Thesing<br>
 * Website: <a href="http://www.webdings.de">http://www.webdings.de</a>
 * @version 0.1 31.07.2005
 * @see Neuron
 */
public class Synapse {
    //attribute
    /**
     * the source neuron of activation
     */
    protected Neuron source;
    /**
     * the target neuron of the activation
     */
    protected Neuron target;
    /**
     * the synapse weight
     */
    protected float weight;
    //constructor
    /**
     * @param source
     * @param target
     * @param weight
     */
    public Synapse(Neuron source, Neuron target, float weight) {
     this.source = source;
     this.target = target;
     this.weight = weight;
    }
    //methods
    /**
     * @return Returns the source neuron.
     */
    public Neuron getSource() {
        return source;
    }
    /**
     * @param source The source neuron to set.
     */
    public void setSource(Neuron source) {
        this.source = source;
    }
    /**
     * @return Returns the target neuron.
     */
    public Neuron getTarget() {
        return target;
    }
    /**
     * @param target The target neuron to set.
     */
    public void setTarget(Neuron target) {
        this.target = target;
    }
    /**
     * @return Returns the synapse weight.
     */
    public float getWeight() {
        return weight;
    }
    /**
     * @param weight The synapse weight to set.
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }
}
