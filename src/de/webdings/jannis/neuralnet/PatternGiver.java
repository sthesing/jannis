/* PatternGiver.java - Copyright (c) 2005 by Stefan Thesing
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
 * PatternGiver is used to present a {@link NeuralNet} with
 * an input {@link Pattern}.
 * 
 * @author Stefan Thesing<br>
 * Website: <a href="http://www.webdings.de">http://www.webdings.de</a>
 * @version 0.1 01.08.2005
 */
public class PatternGiver {
    //attributes
    /**
     * <code>inputLayer</code> is the input layer of
     * the {@link NeuralNet} that is presented with
     * input by this PatternGiver.
     */
    Neuron[] inputLayer;
    
    /**
     * <code>pattern</code> is the the whole of input 
     * {@link Pattern}s the {@link NeuralNet} is presented
     * with.
     */
    Pattern[] pattern;
    
    /**
     * <code>counter</code> is used to keep track of the
     * number of patterns the net has already been presented
     * with.
     */
    private int counter;
    
    //constructors
    /**
     * @param inputLayer
     * @param fileName
     * @throws IOException
     * @throws PatternCreateException
     */
    public PatternGiver(Neuron[] inputLayer, String fileName) throws IOException, PatternCreateException  {
      this.inputLayer = inputLayer;
      this.pattern = PatternConverter.strToPattern(TextFiles.readFromFile(fileName), inputLayer.length);
      this.counter =0;
    }
    /**
     * @param inputLayer
     * @param pattern
     */
    public PatternGiver(Neuron[] inputLayer, Pattern[] pattern) {
      this.inputLayer = inputLayer;
      this.pattern = pattern;
      this.counter = 0;
    }
    
    //methods
    /**
     * Presents the net with the next pattern.
     * @throws PatternGiverReaderCommunicationException
     */
    public void nextPattern() throws PatternGiverReaderCommunicationException {
      if(counter >= pattern.length){
        throw new PatternGiverReaderCommunicationException("Fehler beim Senden des Musters an die Inputschicht!");
      } else {
       int i;
       for(i=0; i < inputLayer.length ;++i) {
        if(pattern[counter].entries[i]) {
          inputLayer[i].fire();
        }
       }
       ++counter;
      }
    }

    /**
     * @return {@link #counter}, i.e. the number of patterns
     * the net has already been presented with.
     */
    public int numberSent() {
      return counter;
    }
    
    /**
     * @return Returns the inputLayer.
     */
    public Neuron[] getInputLayer() {
        return inputLayer;
    }
    /**
     * @param inputLayer The inputLayer to set.
     */
    public void setInputLayer(Neuron[] inputLayer) {
        this.inputLayer = inputLayer;
    }
    /**
     * @return Returns the patterns.
     */
    public Pattern[] getPattern() {
        return pattern;
    }
    /**
     * @param pattern The pattern to set.
     */
    public void setPattern(Pattern[] pattern) {
        this.pattern = pattern;
    }
}
