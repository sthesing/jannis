/* Teacher.java - Copyright (c) 2005 by Stefan Thesing
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

import de.webdings.jannis.exceptions.PatternCreateException;
import de.webdings.jannis.exceptions.PatternGiverReaderCommunicationException;
import de.webdings.tools.files.TextFiles;

/**
 * Teacher is used to train a neural net. Currently only
 * training by backpropagation of errors for 2-layer-nets
 * is supported.
 * 
 * @author Stefan Thesing<br>
 * Website: <a href="http://www.webdings.de">http://www.webdings.de</a>
 * @version 0.1 11.08.2005
 */
public class Teacher {
    //attributes
    /**
     * Teacher compares the output the net is supposed to produce to the output actually 
     * produced by the net and changes synapse weights slowly into a direction
     * that brings the net closer to producing the desired output.
     */
    protected Pattern[] desiredOutput;
    private Neuron[][] layers;
    private int counter;
    
    //constructors
    /**
     * @param fileNameDesiredOutput
     * @param net
     * @throws PatternCreateException
     * @throws IOException
     */
    public Teacher(String fileNameDesiredOutput, NeuralNet net) throws PatternCreateException, IOException {
        this(fileNameDesiredOutput, net.getLayers());
    }
    
    /**
     * @param fileNameDesiredOutput
     * @param layers
     * @throws PatternCreateException
     * @throws IOException
     */
    public Teacher(String fileNameDesiredOutput, Neuron[][] layers) throws PatternCreateException, IOException {
      this.desiredOutput = PatternConverter.strToPattern(TextFiles.readFromFile(fileNameDesiredOutput),layers[layers.length-1].length);
      this.layers = layers;
    }

    /**
     * @param desiredOutput
     * @param net
     */
    public Teacher(Pattern[] desiredOutput, NeuralNet net) {
        this(desiredOutput, net.getLayers());
    }
    
    /**
     * @param desiredOutput
     * @param layers
     */
    public Teacher(Pattern[] desiredOutput, Neuron[][] layers) {
      this.desiredOutput = desiredOutput;
      this.layers = layers;
      this.counter = 0;
    }

    //methods
    /**
     * @return the number of actual and desired output 
     * patterns that have already been compared
     */
    int amountCompared() {
      return counter;
    }

    /**
     * compares the actual output produced by the net to
     * the desired output
     */
    void compareOutputToDesiredOutput() {
      for(int i=0;i<desiredOutput[0].entries.length;++i) {
        layers[layers.length-1][i].setShouldHaveFired(desiredOutput[counter].entries[i]);
      }
    }

    /**
     * There are many possible combination of states 
     * the parameters can have. Teacher only modifies
     * synapse weights in two cases:
     * <table border=1>
     *  <tr>
     *   <th>#</th>
     *   <th>Description</th>
     *   <th>Modification</th>
     *  </tr>
     *  <tr>
     *   <td>Case 1</td>
     *   <td>the target has fired, but it wasn't supposed 
     *       to fire, the source has fired</td>
     *   <td>decrease the synapse weight by 0.1</td>
     *  </tr>
     *  <tr>
     *   <td>Case 2</td>
     *   <td>the target didn't fire, but it was supposed to
     *       fire, the source has fired</td>
     *   <td>increase the synapse weight by 0.1</td>
     *  </tr>
     * </table>  
     * @param targetFired
     * @param targetShouldHaveFired
     * @param sourceFired
     * @param synapse
     */
    void adjustWeights(boolean targetFired, boolean targetShouldHaveFired, boolean sourceFired, Synapse synapse) {
            if(targetFired && targetShouldHaveFired && sourceFired) {
              //This was used in an attempt to implement
              //a backpropagation training method for 
              //nets with more than two layers. It will
              //stay here for the time being, although it
              //doesn't do anything functional for now.
              synapse.getSource().setShouldHaveFired(true);
            }

            //Case 1:
            if(targetFired && !targetShouldHaveFired && sourceFired) {
              synapse.setWeight(synapse.getWeight()-0.1f);
            }

            //Case 2:
            if(!targetFired && targetShouldHaveFired && sourceFired) {
              synapse.setWeight(synapse.getWeight()+0.1f);
              synapse.getSource().setShouldHaveFired(true);
            }

    }

    private void checkNetBackwards() {
      Neuron currentNeuron;
      Neuron potentialSource;
      //Start with the output layer and count down to the 
      //first hidden layer
      for(int i=layers.length-1;i>0;--i) {
        //process every neuron of the current layer
        for(int j=0;j<layers[i].length;++j) {
          currentNeuron = layers[i][j];
          //process the layer before the current one
          for(int k=0;k<layers[i-1].length;++k) {
            potentialSource = layers[i-1][k];
            //check every neuron of that layer for a connection to the 
            //curront neuron
            for(int l=0;l<potentialSource.getConnections().length;++l) {
              //if the potential source targets (among others) 
              //the current neuron, the synapse weight of 
              //the connection is modified (this happens in
              //the method 'adjustWeights'
              if(potentialSource.getConnections()[l].getTarget() == currentNeuron) {
                adjustWeights(currentNeuron.hasFired(), currentNeuron.getShouldHaveFired(), potentialSource.hasFired(), potentialSource.getConnections()[l]);
              }
            }
          }

        }
      }
    }
    
    /**
     * starts comparing the actual output produced by the
     * net with desired ouput and then backpropagates the
     * error.
     * @throws PatternGiverReaderCommunicationException
     */
    public void teach() throws PatternGiverReaderCommunicationException {
        if(counter >= desiredOutput.length) {
          throw new PatternGiverReaderCommunicationException("An error occured while teaching!");
        } else {
          this.compareOutputToDesiredOutput();
          this.checkNetBackwards();
          ++counter;
        }
    }

    /**
     * @return the desired output the net is supposed to
     * produced
     */
    public Pattern[] getDesiredOutput() {
        return desiredOutput;
    }
}
