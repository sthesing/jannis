/* NeuronIDFinder.java - Copyright (c) 2005 by Stefan Thesing
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

import de.webdings.jannis.exceptions.NeuronNotFoundException;
/**
 * NeuronIDFinder is used to find the ID of a given neuron
 * in a net. It uses the same ID system as NNML.</p>
 * <p>You should have received a DTD of NNML along with
 * Jannis. If not, or if you're looking for further info
 * on NNML, visit the Jannis project Website on Savannah:
 * <a href="http://www.nongnu.org/jannis/">http://www.nongnu.org/jannis/</a>
 * 
 * @author Stefan Thesing<br>
 * Website: <a href="http://www.webdings.de">http://www.webdings.de</a>
 * @version 0.1 10.08.2005
 */
public class NeuronIDFinder {
	//Attribute
    /**
     * The layers of the net <code>NeuronIDFinder</code> is searching.
     */
    Neuron[][] layers;
    //Konstruktor
    /**
     * Constructs a <code>NeuronIDFinder</code> that can search the specified {@link
     * NeuralNet}.
     * @param net
     */
    public NeuronIDFinder(NeuralNet net) {
    	this(net.getLayers());
    }
    
    /**
     * Constructs a <code>NeuronIDFinder</code> that can search the {@link
     * NeuralNet} specified by its layers..
     * @param layers
     */
    public NeuronIDFinder(Neuron[][] layers) {
      this.layers = layers;
    }
    //Methoden
    /**
     * @param neuron
     * @return The ID of the layer the specified neuron resides in.
     * @throws NeuronNotFoundException
     */
    public int getLayerID(Neuron neuron) throws NeuronNotFoundException {
      return getIDs(neuron)[0];
    }

    /**
     * @param neuron
     * @return The ID of the neuron within the layer it resides in.
     * @throws NeuronNotFoundException
     */
    public int getNeuronID(Neuron neuron) throws NeuronNotFoundException {
      return getIDs(neuron)[1];
    }

    /**
     * @param neuron
     * @return an array containing two <code>int</code>s. The <code>int</code> at index 0 
     * represents the layerID, the <code>int</code> at index 1 represents the neuronID.
     * @throws NeuronNotFoundException if the specified neuron does not reside in the 
     * searched net.
     */
    int[] getIDs(Neuron neuron) throws NeuronNotFoundException {
      int layerID = -1;
      int neuronID = -1;
      int i,j;
      for(i=0;i<layers.length;++i) {
        for(j=0;j<layers[i].length;++j) {
          if(neuron == layers[i][j]) {
            layerID = i;
            neuronID = j;
            break;
          }
        }
      }
      if(layerID == -1 || neuronID == -1) {
        throw new NeuronNotFoundException("The specified net doesn't contain the neuron searched for!");
      } else {
        int[] rValue = new int[2];
        rValue[0] = layerID;
        rValue[1] = neuronID;
        return rValue;
      }
    }

}
