/* NeuralNet.java - Copyright (c) 2005 by Stefan Thesing
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

import java.io.IOException;

import de.webdings.jannis.exceptions.BadArgumentException;
import de.webdings.jannis.exceptions.LowerLayersEmptyException;
import de.webdings.jannis.exceptions.NeuronTypeMismatchException;
import de.webdings.jannis.neuralnet.nnml.BiNetToNNML;
import de.webdings.tools.StringSearch;
import de.webdings.tools.files.TextFiles;
/**
 * NeuralNet is a wrapper for the actual neural net that is
 * represented in {@link #layers layers}.<br>
 * It provides functionality for setting up, using, training
 * and reconfiguring the net easily as well as saving it
 * to an NNML file.</p>
 * <p>The net can consist of all known neuron types that are
 * a subclass of the abstract 
 * {@link de.webdings.jannis.neuralnet.Neuron}. It accepts
 * only one such type at a time. So you can't mix different
 * neuron types in one net.</p>
 * <p>Currently only one neuron type is supported:</p>
 * <ul>
 * <li>{@link de.webdings.jannis.neuralnet.BiNeuron BiNeuron}</li>
 * </ul> 
 * 
 * @author Stefan Thesing<br>
 * Website: <a href="http://www.webdings.de">http://www.webdings.de</a>
 * @version 0.1 10.08.2005
 */
public class NeuralNet {
    //attributes
    /**
     * <code>layers</code> is an array containing layers
     * of neurons. Each layer is an array containing neurons.
     * So <code>layers</code> is the actual neural net 
     * wrapped by this class. 
     */
    protected Neuron[][] layers;
    /**
     * <code>type</code> represents the type of neurons
     * of which this net consists. For example, if a net
     * consists of 
     * {@link de.webdings.jannis.neuralnet.BiNeuron BiNeuron}s
     * <code>type</code> reads 
     * "de.webdings.jannis.neuralnet.BiNeuron"</p>
     * <p>Mixing different neuron types is not allowed.
     * 
     */
    protected String type;
    //constructors
    /**
     * Constructs an empty NeuralNet of the specified type.
     * @param type
     * @throws NeuronTypeMismatchException
     */
    public NeuralNet(String type) throws NeuronTypeMismatchException {
        if(type.equals("de.webdings.jannis.neuralnet.BiNeuron")) {
           this.layers = new BiNeuron[0][];
           this.type = type;
        } else {
            throw new NeuronTypeMismatchException("Unknown neuron type!");
        }
    }
    /**
     * Constructs an empty NeuralNet of the specified type.
     * @param type
     * @throws NeuronTypeMismatchException
     */
    public NeuralNet(Class type) throws NeuronTypeMismatchException {
        this(new String(type.getName()));
    }
    /**
     * Constructs a NeuralNet using a specified array containing layers
     * of neurons. This constructor automatically determines the type of
     * the neurons that the specified layers contain and rejects layers 
     * that contain neurons of unsupported types as well as layers that 
     * contain neurons of mixed types.
     * @param layers
     * @throws NeuronTypeMismatchException
     */
    public NeuralNet(Neuron[][] layers) throws NeuronTypeMismatchException {
       if(areNeuronTypesConsistent(layers)) {
          this.type = new String(layers[0][0].getClass().getName()); 
          this.layers = layers; 
       } else {
           throw new NeuronTypeMismatchException
                      ("Couldn't create neural net. There " +
                       "are gaps in the specified layers " +
                       "or they contain different neuron " +
                       "types!");
       }
    }
    /**
     * Constructs a NeuralNet starting with a single specified neuron and automatically
     * determines the type of this neuron. It rejects unsupported neuron types.
     * @param aFirstInputNeuron
     * @throws NeuronTypeMismatchException
     */
    public NeuralNet(Neuron aFirstInputNeuron) throws NeuronTypeMismatchException {
        if(aFirstInputNeuron.getClass().getName().equals
                ("de.webdings.jannis.neuralNet.BiNeuron")){
            this.layers = new BiNeuron[1][];
            layers[0] = new BiNeuron[1];
            layers[0][0]= aFirstInputNeuron;
            this.type = new String(aFirstInputNeuron.getClass().getName());
        } else {
            throw new NeuronTypeMismatchException("Unsupported neuron type: "
                    + aFirstInputNeuron.getClass().getName() + "!");
        }
    }
    
    /**
     * This static method is called by other methods and constructors
     * to ensure that the neuron types used in this net are consistent.
     * @param layers The layers of the net that is checked for consistency.
     * @return <code>true</code> if only one type of Neurons is used in this
     * net, <code>false</code> if there are mixed types.
     * @throws NeuronTypeMismatchException if the specified layers are empty.
     */
    public static boolean areNeuronTypesConsistent(Neuron[][] layers) 
      		throws NeuronTypeMismatchException {
        String sType;
        //Check if the first neuron exists
        if(layers.length>0 
           && layers[0].length>0 
           && layers[0][0] != null) {
            //use the type of the first neuron for comparison
            sType = new String(layers[0][0].getClass().getName());
            //Check if the other neurons are the same type
            for(int i=0;i<layers.length;++i) {
                for(int j=0;j<layers[i].length;++j) {
                    if(layers[i][j]==null
                       ||!(layers[i][j].getClass().getName().
                       	equals(sType))) {
                       return false;
                    }
                }
            }
            //If all is well we can use the specified layers
            return true;   
        } else {
            throw new NeuronTypeMismatchException("Could not determine " +
            		"neuron type! Specified layers are empty.");
        }
     
    }
    //setters and getters
    /**
     * @return Returns the layers.
     */
    public Neuron[][] getLayers() {
        return layers;
    }
    /**
     * This methods replaces the actual neural net wrapped
     * by this class with the specified array. It does
     * essentially the same as the constructor with the same
     * parameter.
     * @param layers The layers to set.
     * @throws NeuronTypeMismatchException
     */
    public void setLayers(Neuron[][] layers) throws NeuronTypeMismatchException {
        if(areNeuronTypesConsistent(layers)) {
            this.type = new String(layers[0][0].getClass().getName()); 
            this.layers = layers; 
         } else {
             throw new NeuronTypeMismatchException
                        ("Couldn't create neural net. There " +
                         "are gaps in the specified layers " +
                         "or they contain different neuron " +
                         "types!");
         }
    }
    /**
     * @return Returns the neuron type of this net.
     */
    public String getType() {
        return type;
    }
    //other
    /**
     * Adds a neuron to the specified layer
     * @param neuron
     * @param targetLayerID
     * @throws LowerLayersEmptyException
     * @throws NeuronTypeMismatchException
     * @throws BadArgumentException
     */
    public void addNeuron(Neuron neuron, int targetLayerID)
		throws LowerLayersEmptyException, 
		NeuronTypeMismatchException, BadArgumentException {
      //check if the neuron is the same type as this net
      if(!neuron.getClass().getName().equals(this.type)) {
          throw new NeuronTypeMismatchException("The " +
          		"specified neuron doesn't match the type " +
          		"of this net!");
      }
      //Does targetLayerID fit into the netsize?
      if(targetLayerID>=layers.length) {
          throw new BadArgumentException("The target layer " +
          		"you specified does not exist!");
      }
      //If the layer with the specified ID hasn't been
      //initialized yet, do it now...
      if(layers[targetLayerID]==null) {
          layers[targetLayerID] = new Neuron[0];
      }
      //check if the layers before targetLayerID are
      //empty. If yes throw an exception
      for(int i=0;i<targetLayerID;++i) {
          if(layers[i].length<1) {
              throw new LowerLayersEmptyException();
          }
      }
      //if everything's ok add the new neuron to the
      //specified layer
      Neuron[] tempLayer = new Neuron[layers[targetLayerID].length+1];
      for(int i=0; i<layers[targetLayerID].length; ++i) {
          tempLayer[i] = layers[targetLayerID][i];
      }
      tempLayer[layers[targetLayerID].length] = neuron;
      layers[targetLayerID] = tempLayer;
    }
    
    /**
     * Adds a new empty layer to the net.
     * @throws NeuronTypeMismatchException
     */
    public void addLayer() throws NeuronTypeMismatchException {
        if(this.getType().equals
                ("de.webdings.jannis.neuralnet.BiNeuron")) {
            this.addLayer(new BiNeuron[0]);
        } else {
            throw new NeuronTypeMismatchException("Unsupported neuron type: "
                    + this.getClass().getName() + "!");
            
        }
    }
    
    /**
     * Adds a specified number of empty layers to the net.
     * @param numberOfLayersToAdd
     * @throws NeuronTypeMismatchException
     */
    public void addLayers(int numberOfLayersToAdd) throws NeuronTypeMismatchException {
    	for(int i=0;i<numberOfLayersToAdd;++i) {
    		this.addLayer();
    	}
    }
    
    /**
     * Adds the specified layer to the net.
     * @param l
     * @throws NeuronTypeMismatchException
     */
    public void addLayer(Neuron[] l) throws NeuronTypeMismatchException {
        this.addLayer(l, this.layers.length);
    }
    
    
    /**
     * Adds the specified layer to the net using the
     * specified layerID. If the net already contains
     * a layer with this ID it (and the following layers)
     * will be moved one step farther.<br>
     * So the ID number of these layers will be increased
     * by one.<br>
     * If you don't want that, but want to replace the
     * existing layer use the method replaceLayer.
     * @param l
     * @param layerID
     * @throws NeuronTypeMismatchException
     */
    public void addLayer(Neuron[] l, int layerID) throws NeuronTypeMismatchException {
        if(StringSearch.stringContainsCaseSensitive
        	(l.getClass().getName(), 
         	"de.webdings.jannis.neuralnet.BiNeuron")) {
          Neuron[][] temp = new Neuron[layers.length+1][];
          for(int i=0;i<layerID;++i) {
              temp[i] = layers[i];
          }
          temp[layerID] = l;
          for(int i=layerID+1;i<temp.length;++i) {
              temp[i] = layers[i-1];
          }
          layers = temp;
        } else {
            throw new NeuronTypeMismatchException();
        }
        
    }
    
    /**
     * Replaces the layer of the specified layerID with the
     * specified layer l.
     * @param l
     * @param layerID
     * @throws NeuronTypeMismatchException
     */
    public void replaceLayer(Neuron[] l, int layerID) throws NeuronTypeMismatchException {
        if(StringSearch.stringContainsCaseSensitive
        	(l.getClass().getName(), 
         	"de.webdings.jannis.neuralnet.BiNeuron")) {
          Neuron[][] temp = new Neuron[layers.length][];
          for(int i=0;i<layerID;++i) {
              temp[i] = layers[i];
          }
          temp[layerID] = l;
          for(int i=layerID+1;i<layers.length;++i) {
              temp[i] = layers[i];
          }
          layers = temp;
        } else {
            throw new NeuronTypeMismatchException();
        }
    }
    
    /**
     * Saves a NNML representation of the net to a file
     * of the specified filename.
     * @param fileName
     * @throws NeuronTypeMismatchException if the neuron type
     * of the net is not supported.
     * @throws IOException
     */
    public void toFile(String fileName) throws NeuronTypeMismatchException, IOException {
        if(this.getType().equals
                ("de.webdings.jannis.neuralnet.BiNeuron")) {
            BiNetToNNML konverter = new BiNetToNNML();
            try {
				TextFiles.writeToFile(fileName, konverter.generateString(layers));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.print(e.getClass());
				System.out.print(e.getMessage());
				//e.printStackTrace();;
			} 
        } else {
            throw new NeuronTypeMismatchException("Saving of " 
                    + "nets of this neuron type is not yet supported.");
        }
        
    }
}