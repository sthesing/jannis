/* PatternReader.java - Copyright (c) 2005 by Stefan Thesing
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

import de.webdings.jannis.exceptions.PatternGiverReaderCommunicationException;

/**
 * PatternReader is used to read the output 
 * produced by a {@link NeuralNet}. The output is stored in
 * an array of {@link Pattern}s. It can also export the 
 * output to a {@link java.util.String} of '0's and '1's.
 * 
 * @author Stefan Thesing<br>
 * Website: <a href="http://www.webdings.de">http://www.webdings.de</a>
 * @version 0.1 10.08.2005
 */
public class PatternReader {
    //attribute
    /**
     * The output layer the output is read from
     */
    private Neuron[] outputLayer;
    /**
     * Well, the number of patterns to read...
     */
    protected int numberOfPatternsToRead;
    /**
     * The array of {@link Pattern}s the output is stored in
     */
    private Pattern[] pattern;
    /**
     * <code>counter</code> is used to keep track of the
     * number of patterns that have already been read.
     */
    private int counter;
    
    //constructor
    /**
     * @param outputLayer
     * @param numberOfPatternsToRead
     */
    public PatternReader(Neuron[] outputLayer, int numberOfPatternsToRead) {
      this.outputLayer = outputLayer;
      this.numberOfPatternsToRead = numberOfPatternsToRead;
      this.pattern = new Pattern[numberOfPatternsToRead];
      this.counter=0;
    }
    //methods
    /**
     * @return the number of patterns that have already been
     * read
     */
    public int numberOfPatternsRead(){
      return counter;
    }

    /**
     * reads the current output of the neural net
     * @throws PatternGiverReaderCommunicationException
     */
    public void readPattern() throws PatternGiverReaderCommunicationException {
     if(counter >= numberOfPatternsToRead) {
       throw new PatternGiverReaderCommunicationException("An error occured when reading output from the" +
       		"output layer!");
     } else {
       int i;
       pattern[counter] = new Pattern(new boolean[outputLayer.length]);
       for (i = 0; i < outputLayer.length; ++i) {
         pattern[counter].entries[i] = outputLayer[i].hasFired();
       }
       ++counter;
     }
    }

    /**
     * @return a {@link java.lang.String} containing '0's
     * and '1's that represents the read output patterns.
     */
    public String exportPattern(){
      return PatternConverter.patternToStr(pattern, outputLayer.length);
    }
    
    /**
     * @return Returns the numberOfPatternsToRead.
     */
    public int getNumberOfPatternsToRead() {
        return numberOfPatternsToRead;
    }
    /**
     * @param numberOfPatternsToRead The numberOfPatternsToRead to set.
     */
    public void setNumberOfPatternsToRead(int numberOfPatternsToRead) {
        this.numberOfPatternsToRead = numberOfPatternsToRead;
    }
    /**
     * @return Returns the outputLayer.
     */
    public Neuron[] getOutputLayer() {
        return outputLayer;
    }
    /**
     * @param outputLayer The outputLayer to set.
     */
    public void setOutputLayer(Neuron[] outputLayer) {
        this.outputLayer = outputLayer;
    }
    /**
     * @return Returns the patterns read from the net.
     */
    public Pattern[] getPattern() {
        return pattern;
    }
    
}
