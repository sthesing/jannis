/* NNMLToBiNet.java - Copyright (c) 2005 by Stefan Thesing
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

import de.webdings.jannis.exceptions.NNMLException;
import de.webdings.jannis.exceptions.NeuronTypeMismatchException;
import de.webdings.jannis.neuralnet.BiNeuron;
import de.webdings.jannis.neuralnet.Neuron;
import de.webdings.tools.CharToFloat;
import de.webdings.tools.CharToInt;
import de.webdings.tools.StringSearch;

/**
 * NNMLToBiNet is used to construct a neural net with
 * BiNeurons from a NNML representation. 
 * 
 * @author Stefan Thesing<br>
 * Website: <a href="http://www.webdings.de">http://www.webdings.de</a>
 * @version 0.3 11.08.2005
 */
public class NNMLToBiNet extends NNMLToNet {

    /* (non-Javadoc)
     * @see de.webdings.jannis.neuralnet.nnml.NNMLToNet#convert(java.lang.String)
     */
    /**
     * This overrides the method inherited from {@link NNMLToNet} .
     * It calls {@link NNMLToBiNet#convertToBiNet(String s)}, so it
     * actually returns an array containing layers of 
     * BiNeurons. 
     * @throws NNMLException
     * @throws NeuronTypeMismatchException 
     */
    public Neuron[][] convertToNet(String s) throws NNMLException, NeuronTypeMismatchException  {
        return this.convertToBiNet(s);
    }

    private BiNeuron parseNeuron(String neuron) {
        return new BiNeuron(CharToFloat.convert(
                neuron.substring(neuron.indexOf("=")+2, 
                neuron.indexOf(".")+2)));
    }

    private BiNeuron[] parseLayer(String layerStr) {
        String[] s = new String[0];
        String[] buffer;
        while(layerStr.indexOf("<neuron") != -1) {
          buffer = new String[s.length+1];
          for(int i=0;i<s.length;++i) {
            buffer[i] = s[i];
          }
          buffer[s.length] = layerStr.substring(layerStr.indexOf("<neuron"), layerStr.indexOf("/>"));
          layerStr = layerStr.substring(layerStr.indexOf("/>")+1);
          s = buffer;
        }
        //Construct an array for the layer
        BiNeuron[] layer = new BiNeuron[s.length];
        //fill the array
        for(int i=0;i<layer.length;++i) {
          layer[i] = parseNeuron(s[i]);
        }
        return layer;
      }

      private BiNeuron[][] parseLayers(String layersStr) {
        String[] s = new String[0];
        String[] buffer;
        //fill the array with the strings representing
        //each layer
        while(layersStr.indexOf("<layer>") != -1) {
          buffer = new String[s.length+1];
          for(int i=0;i<s.length;++i) {
            buffer[i] = s[i];
          }
          buffer[s.length] = layersStr.substring(layersStr.indexOf("<layer>"), layersStr.indexOf("</layer>"));
          layersStr = layersStr.substring(layersStr.indexOf("</layer>")+1);
          s = buffer;
        }
        //construct array for the layers
        BiNeuron[][] layers = new BiNeuron[s.length][];
        //fill the array
        for(int i=0;i<layers.length;++i) {
            layers[i] = parseLayer(s[i]);
        }
        return layers;
      }

      private void parseSynapses(String synapses, BiNeuron[][] schichten) throws NNMLException  {
        try {
            while(synapses.indexOf("<synapse") != -1) {
              synapses = synapses.substring(synapses.indexOf("<synapse"));
              String weightStr = synapses.substring(synapses.indexOf("=")+2, synapses.indexOf(".")+2);
              float weight = CharToFloat.convert(weightStr);
              synapses = synapses.substring(synapses.indexOf("<source"));
              int sourceLayerID = CharToInt.convert(synapses.charAt(synapses.indexOf("=")+2));
              synapses = synapses.substring(synapses.indexOf("neuronID"));
              int sourceNeuronID = CharToInt.convert(synapses.charAt(synapses.indexOf("=")+2));
              synapses = synapses.substring(synapses.indexOf("<target"));
              int targetLayerID = CharToInt.convert(synapses.charAt(synapses.indexOf("=")+2));
              synapses = synapses.substring(synapses.indexOf("neuronID"));
              int targetNeuronID = CharToInt.convert(synapses.charAt(synapses.indexOf("=")+2));

              schichten[sourceLayerID][sourceNeuronID].addConnection(schichten[targetLayerID][targetNeuronID], weight);
            }
        } catch (NumberFormatException e) {
            throw new NNMLException("Error in parsing a neuron ID or layer ID", e.getCause());
        }
      }

    /**
     * See {@link #convertToNet(String)}
     * @param s string containing NNML representation of the net
     * @return array containing layers of BiNeurons
     * @throws NNMLException
     * @throws NeuronTypeMismatchException 
     */
    public BiNeuron[][] convertToBiNet(String s) throws NNMLException, NeuronTypeMismatchException  {
    	String layersStr;
        String synapses;
        String typeStr;
        int beginType = s.indexOf("<neural_net type=");
        int beginLayers = s.indexOf("<layer>");
        typeStr = s.substring(beginType, beginLayers-1);
        if(!StringSearch.stringContains(typeStr, "type=\"bineuron\"")){
        	throw new NeuronTypeMismatchException("Unsupported neuron type!");
        }
        int beginSynapsen = s.indexOf("<synapse");
        if(beginLayers < 0 || beginSynapsen <0) {
          throw new NNMLException("This is not valid NNML!");
        } else {
          layersStr = s.substring(beginLayers, beginSynapsen-1);
          synapses = s.substring(beginSynapsen);
          BiNeuron[][] schichten = parseLayers(layersStr);
          parseSynapses(synapses, schichten);
          return schichten;
        }
      }
    
}
