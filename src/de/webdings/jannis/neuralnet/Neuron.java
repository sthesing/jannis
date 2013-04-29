/* Neuron.java - Copyright (c) 2005 by Stefan Thesing
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
 * Neurons are the basic units that compose neural nets. 
 * The abstract class Neuron provides functionalities that 
 * all types of
 * neurons have in common. It is an abstract class
 * for it does not provide an implementation of an
 * activation function or a treshhold value.
 * 
 * @author Stefan Thesing<br>
 * Website: <a href="http://www.webdings.de">http://www.webdings.de</a>
 * @version 0.1 10.08.2005
 */
public abstract class Neuron {
    //attributes
    /** activation a of the neuron. Can be used in 
     * calculations for the activation funtion.
     */
     protected float a;

    /** net represents the summed up input reaching 
     * the neuron.
     * */
     protected float net;

    /** connections is an array containing all outgoing
     * {@link Synapse synaptical} connections of this 
     * neuron.
     */
     protected Synapse[] connections;
     
    /**
     * <code>numberOfConnections</code> represents
     * the amount of outgoing connections of this 
     * neuron. It is identical to the size of {@link
     * #connections}
     */
     protected int numberOfConnections;

    /** fired returns true if the neuron has fired
     * during the last time the net was presented with
     * input.
     */
     protected boolean fired;

    /** shouldHaveFired can be used by learning methods.
     * {@link Teacher) uses it, for example.
     */
     protected boolean shouldHaveFired;

    //constructor
   /**
    * Constructs a Neuron with default values:<br>
    * a=0<br>
    * net=0<br>
    * fired=false<br>
    * shouldHaveFired=<code>false</code><br>
    * numberOfConnections=0
    */
    public Neuron() {
        a = 0.0f;
        net = 0.0f;
        numberOfConnections = 0;
        connections = new Synapse[numberOfConnections];
        fired = false;
        shouldHaveFired = false;
    }

   //operations
    /**
     * connects this neuron with another one (target) by constructing
     * a new {@link Synapse} with a randomly generated weight ranging
     * from -0.2 and +0.2
     * @param target the target neuron
     * @see WeightRandomizer#generateRandomWeight()
     */
    public void addConnection(Neuron target) {
    	WeightRandomizer wr = new WeightRandomizer();
    	this.addConnection(target, wr.generateRandomWeight());
    }
    
   /**
    * connects this neuron with another one (target)
    * by constructing a new {@link Synapse} with
    * the specified weight.
    * @param target the target neuron
    * @param weight weight of the synaptical connection
    */
    public void addConnection(Neuron target, float weight){
        this.addConnection(new Synapse(this, target, weight));
    }
   /**
    * Adds a existing {@link Synapse} to the
    * connections of this neuron.
    * @param synapse the {@link Synapse} to add
    */
    public void addConnection(Synapse synapse) {
        int i;
        Synapse[] newConnections = new Synapse[numberOfConnections+1];
        if(numberOfConnections>0) {
            for(i=0;i<numberOfConnections;++i) {
                newConnections[i] = connections[i];
            }
        }
        newConnections[numberOfConnections] = synapse;
        ++numberOfConnections;
        connections = newConnections;
    }
   /**
    * represents the activations function of the 
    * neuron. This is an abstract method that must
    * be replaced by any concrete subclass of Neuron.<br>
    * The simplest concrete implementation of this
    * method is a "binary treshold function" (a 
    * treshold value) found in {@link BiNeuron}.
    * @return true if the treshold is reached.
    */
    public abstract boolean tresholdReached();
   
    /**
     * @return a String representation of the activation function
     * implemented in {@link #tresholdReached()}
     */
    public abstract String getActivationFunction();
    
   /**
    * directly causes the neuron to fire.
    */
    public void fire() {
        int i;
        for(i=0;i<numberOfConnections;++i) {
            connections[i].target.gatherActivation(connections[i].weight);
        }
        net=0;
        fired = true;
    }

   /**
    * Gathers the net input.
    * @param weight 
    */
    public void gatherActivation(float weight) {
        net = net + weight;
    }

   /**
    * Sets all attributes (exclusive connections and
    * numberOfConnections) back to default:
    * a=0<br>
    * net=0<br>
    * fired=false<br>
    * shouldHaveFired=false<br>
    */
   public void clear() {
    a=0;
    net=0;
    fired=false;
    shouldHaveFired=false;
   }
   /**
    * @return Returns the connections.
    */
   public Synapse[] getConnections() {
       return connections;
   }
    /**
     * @param connections The connections to set.
     */
    public void setConnections(Synapse[] connections) {
        this.connections = connections;
    }
    /**
     * @return Returns the fired.
     */
    public boolean hasFired() {
        return fired;
    }
    /**
     * @param fired The fired to set.
     */
    public void setFired(boolean fired) {
        this.fired = fired;
    }
    /**
     * @return Returns the shouldHaveFired.
     */
    public boolean getShouldHaveFired() {
        return shouldHaveFired;
    }
    /**
     * @param shouldHaveFired The shouldHaveFired to set.
     */
    public void setShouldHaveFired(boolean shouldHaveFired) {
        this.shouldHaveFired = shouldHaveFired;
    }
    /**
     * @return Returns the a.
     */
    public float getA() {
        return a;
    }
    /**
     * @return Returns the net.
     */
    public float getNet() {
        return net;
    }
    /**
     * @return Returns the numberOfConnections.
     */
    public int getNumberOfConnections() {
        return numberOfConnections;
    }
}
