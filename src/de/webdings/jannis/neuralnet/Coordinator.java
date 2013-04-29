/* Coordinator.java - Copyright (c) 2005 by Stefan Thesing
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
 * <code>Coordinator</code> is used to manage the activites of a 
 * feed-forward neural net. In particular it coordinates 
 * presenting the neural net with input patterns 
 * (utilizing a {@link PatternGiver}) and registering the 
 * output produced by the net (utilizing a 
 * {@link PatternReader}.</p>
 * <p>It also manages the segmentation of time that is 
 * needed when simulating a parallel process on a serial
 * machine. This means that a neuron that receives sufficient
 * activation to cause it to fire doesn't fire immediately.
 * It waits for the coordinator to tell it that all expected
 * parts of the net activation has been received. This is 
 * needed in almost every net architecture, but especially 
 * in feed-forward nets, that feature layers that become
 * active one after the other.<br>
 * In its current state, <code>Coordinator</code> might be 
 * able to do this for other net architectures, I haven't 
 * given it much thought, yet. But it is designed for 
 * feed-forward nets. 
 * 
 * @author Stefan Thesing<br>
 * Website: <a href="http://www.webdings.de">http://www.webdings.de</a>
 * @version 0.1 10.08.2005
 * 
 */
public class Coordinator {
    /**
     * The layers of the neural net to be coordinated.
     */
    //attributes
    public Neuron[][] layers;
    /**
     * See {@link PatternGiver}
     */
    public PatternGiver giver;
    /**
     * See {@link PatternReader}
     */
    public PatternReader reader;
    //constructors
    /**
     * @param net
     * @param inputPattern
     */
    public Coordinator(NeuralNet net, Pattern[] inputPattern) {
    	this(net.getLayers(), inputPattern);
    }
    
    
    /**
     * @param layers
     * @param inputPattern
     */
    public Coordinator(Neuron[][] layers, Pattern[] inputPattern) {
    	this.layers = layers;
    	this.giver = new PatternGiver(layers[0], inputPattern);
    	this.reader = new PatternReader(layers[layers.length-1], giver.getPattern().length);
    }
    /**
     * @param net
     * @param fileNameInputPattern
     * @throws IOException
     * @throws PatternCreateException 
     */
    public Coordinator(NeuralNet net, String fileNameInputPattern) throws IOException, PatternCreateException {
        this(net.getLayers(), fileNameInputPattern);
    }
    
    /**
     * @param layers
     * @param fileNameInputPattern
     * @throws IOException
     * @throws PatternCreateException 
     */
    public Coordinator(Neuron[][] layers, String fileNameInputPattern) throws IOException, PatternCreateException  {
      this.layers = layers;
      this.giver = new PatternGiver(layers[0], fileNameInputPattern);
      this.reader = new PatternReader(layers[layers.length-1], giver.getPattern().length);
    }

    /**
     * @param net
     * @param giver
     * @param reader
     */
    public Coordinator(NeuralNet net, PatternGiver giver, PatternReader reader) {
        this(net.getLayers(), giver, reader);
    }
    
    /**
     * @param layers
     * @param giver
     * @param reader
     */
    public Coordinator(Neuron[][] layers, PatternGiver giver, PatternReader reader) {
     this.layers = layers;
     this.giver = giver;
     this.reader = reader;
    }
    //methods
    /**
     * Starts presenting the net with the input pattern
     * 
     * @throws PatternGiverReaderCommunicationException
     */
    public void start() throws PatternGiverReaderCommunicationException {
      int i,j;
      giver.nextPattern();
      for(i=1;i<layers.length;++i) {
        for (j = 0; j < layers[i].length; ++j) {
          if (layers[i][j].tresholdReached()) {
            layers[i][j].fire();
          }
        }
      }
      reader.readPattern();
      if(giver.numberSent() == reader.numberOfPatternsRead()) {
        clearAll();
        if(reader.numberOfPatternsRead() < reader.numberOfPatternsToRead){
          start();
        }
      } else {
        throw new PatternGiverReaderCommunicationException("There was an error in the communication between " +
                "PatternGiver and PatternReader!");
      }
    }

    /**
     * Clears all residual activation and memory functions
     * of the neurons in the coordinated net by calling the
     * {@link de.webdings.neuralnet.Neuron#clear()}-method
     * of the {@link de.webdings.neuralnet.Neuron}s 
     * contained in the net.
     */
    public void clearAll() {
      int i,j;
      for(i=0;i<layers.length;++i) {
       for(j=0;j<layers[i].length; ++j) {
        layers[i][j].clear();
       }
      }

    }

    /**
     * Saves the produced output pattern to a file of
     * the specified filename.
     * @param filename
     * @throws IOException
     */
    public void savePattern(String filename) throws IOException {
      TextFiles.writeToFile(filename, PatternConverter.patternToStr(reader.getPattern(), layers[layers.length-1].length));
    }
}
