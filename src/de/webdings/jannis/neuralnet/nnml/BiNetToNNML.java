/* BiNetToNNML.java - Copyright (c) 2005 by Stefan Thesing
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
package de.webdings.jannis.neuralnet.nnml;

import de.webdings.jannis.exceptions.NeuronNotFoundException;
import de.webdings.jannis.exceptions.NeuronTypeMismatchException;
import de.webdings.jannis.neuralnet.NeuralNet;
import de.webdings.jannis.neuralnet.Neuron;
import de.webdings.jannis.neuralnet.NeuronIDFinder;
import de.webdings.jannis.neuralnet.Synapse;

/**
 * BiNetToNNML is used to convert a neural net of
 * BiNeurons to a String containing a NNML representation
 * of that net. The purpose of this String is to be written
 * to a file. The output XML is written according to NNML 0.3
 * 
 * @author Stefan Thesing<br>
 * Website: <a href="http://www.webdings.de">http://www.webdings.de</a>
 * @version 0.1 11.08.2005
 */
public class BiNetToNNML extends NetToNNML {

    /* (non-Javadoc)
     * @see de.webdings.jannis.neuralnet.nnml.NetToNNML#generateString(de.webdings.jannis.neuralnet.NeuralNet)
     */
    public String generateString(NeuralNet net) throws NeuronTypeMismatchException, NeuronNotFoundException {
        if(net.getType().equals("de.webdings.jannis.neuralnet.BiNeuron")) {
            return this.generateString(net.getLayers());
        } else {
            throw new NeuronTypeMismatchException();
        }
    }
    /* (non-Javadoc)
     * @see de.webdings.jannis.neuralnet.nnml.NetToNNML#generateString(de.webdings.jannis.neuralnet.Neuron[][])
     */
    public String generateString(Neuron[][] layers) throws NeuronTypeMismatchException, NeuronNotFoundException {
    	NeuralNet net = new NeuralNet(layers);
    	String type = null;
    	if(net.getType().equals("de.webdings.jannis.neuralnet.BiNeuron")) {
    		type = "bineuron";
    	} else {
    		throw new NeuronTypeMismatchException("Unknown neuron type");
    	}
    	this.finder = new NeuronIDFinder(layers);
        s = new String("<?xml version=\"1.0\"?>\n<!DOCTYPE neural_net SYSTEM \"nnml.dtd\">\n");
        s = s+ "<neural_net type=\"" + type + "\">\n";
        int i;
        for (i = 0; i < layers.length; ++i) {
          nextLayer(layers[i]);
        }
        for (i = 0; i < layers.length; ++i) {
          for(int j=0;j<layers[i].length;++j) {
            for(int k=0;k<layers[i][j].getConnections().length;++k) {
              this.nextSynapse(layers[i][j].getConnections()[k]);
            }
          }
        }
        s = s+ "</neural_net>";
        return s;
      
    }
    
    private void nextSynapse(Synapse synapse) throws NeuronNotFoundException {
        s = s+ "<synapse weight=\"" + synapse.getWeight() + "\">\n";
        s = s+ "<source layerID=\"" + finder.getLayerID(synapse.getSource())
            + "\" neuronID=\"" + finder.getNeuronID(synapse.getSource()) + "\"/>\n";
        s = s+ "<target layerID=\"" + finder.getLayerID(synapse.getTarget())
            + "\" neuronID=\"" + finder.getNeuronID(synapse.getTarget()) + "\"/>\n";
        s = s+ "</synapse>\n";
      }

      private void nextNeuron(Neuron neuron)  {
        s = s+ "<neuron sigma=\"" + neuron.getActivationFunction() + "\"/>\n";
      }

      private void nextLayer(Neuron[] layer)  {
        int j;
        s = s+ "<layer>\n";
        for(j=0;j<layer.length;++j) {
          nextNeuron(layer[j]);
        }
        s = s+ "</layer>\n";
      }
     
      
}
