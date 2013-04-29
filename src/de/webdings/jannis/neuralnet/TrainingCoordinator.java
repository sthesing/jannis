/* TrainingCoordinator.java - Copyright (c) 2005 by Stefan Thesing
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
import de.webdings.jannis.exceptions.PatternLayerMismatchException;

/**
 * TrainingCoordinator is a subclass of {@link Coordinator}.
 * It does the same as its superclass yet it also features
 * a {@link Teacher} and calls its method 
 * {@link Teacher#teach()} at the appropriate time.
 * 
 * @author Copyright 2005 by Stefan Thesing
 * Website: <a href="http://www.webdings.de">http://www.webdings.de</a>
 * @version 0.1 11.08.2005
 */
public class TrainingCoordinator extends Coordinator {
    //ATTRIBUTES
    /**
     * See {@link Teacher}
     */
    public Teacher teacher;
    
    //CONSTRUCTORS
    /**
     * @param net The neural net to be coordinated
     * @param fileNameInputPattern Name of the file containing the input pattern used for
     * the training
     * @param teacher The teacher used for this training
     * @throws IOException if an error occurs while reading a file
     * @throws PatternCreateException See {@link PatternCreateException}
     * @throws PatternLayerMismatchException
     */
    public TrainingCoordinator(NeuralNet net, String fileNameInputPattern,
			  Teacher teacher) throws IOException, PatternCreateException, 
			  PatternLayerMismatchException{
    	this(net.getLayers(), fileNameInputPattern, teacher);
    }
  
    /**
     * @param layers The layers of the neural net to be coordinated
     * @param fileNameInputPattern Name of the file containing the input pattern used for
     * the training
     * @param teacher The teacher used for this training
     * @throws IOException if an error occurs while reading a file
     * @throws PatternCreateException {@link PatternCreateException}
     * @throws PatternLayerMismatchException
     */
    public TrainingCoordinator(Neuron[][] layers, String fileNameInputPattern,
			  Teacher teacher) throws IOException, PatternCreateException,
			  PatternLayerMismatchException{
      super(layers, fileNameInputPattern);
      this.teacher = teacher;
      if(layers[layers.length-1].length != 
          teacher.getDesiredOutput()[0].entries.length) {
          throw new PatternLayerMismatchException
          ("The size of the desired output pattern " +
          "doesn't match the size of the output layer!");
      }
    }
  
    /**
     * @param net The neural net to be coordinated
     * @param fileNameInputPattern Name of the file containing the input pattern used for
     * the training
     * @param fileNameDesiredOutputPattern
     * @throws IOException if an error occurs while reading a file
     * @throws PatternCreateException {@link PatternCreateException}
     * @throws PatternLayerMismatchException
     */
    public TrainingCoordinator(NeuralNet net, String fileNameInputPattern,
          String fileNameDesiredOutputPattern) throws IOException, PatternCreateException,
          PatternLayerMismatchException{
    	this(net.getLayers(), fileNameInputPattern, fileNameDesiredOutputPattern);
    }
  
    /**
     * @param layers The layers of the neural net to be coordinated
     * @param fileNameInputPattern Name of the file containing the input pattern used for
     * the training
     * @param fileNameDesiredOutputPattern
     * @throws IOException if an error occurs while reading a file
     * @throws PatternCreateException {@link PatternCreateException}
     * @throws PatternLayerMismatchException
     */
    public TrainingCoordinator(Neuron[][] layers, String fileNameInputPattern,
          String fileNameDesiredOutputPattern) throws IOException, PatternCreateException,
          PatternLayerMismatchException{
      super(layers, fileNameInputPattern);
      this.teacher = new Teacher(fileNameDesiredOutputPattern, layers);
      if(layers[layers.length-1].length != teacher.getDesiredOutput()[0].entries.length) {
          throw new PatternLayerMismatchException("The size of the desired output pattern " +
          "doesn't match the size of the output layer!");
      }
    }

    /**
     * @param net The neural net to be coordinated
     * @param fileNameInputPattern Name of the file containing the input pattern used for
     * the training
     * @param desiredOutputPattern
     * @throws IOException if an error occurs while reading a file
     * @throws PatternCreateException {@link PatternCreateException}
     * @throws PatternLayerMismatchException
     */
    public TrainingCoordinator(NeuralNet net, String fileNameInputPattern,
          Pattern[] desiredOutputPattern) throws IOException, PatternCreateException, 
          PatternLayerMismatchException  {
    	this(net.getLayers(), fileNameInputPattern, desiredOutputPattern);
    }
  
    /**
     * @param layers The layers of the neural net to be coordinated
     * @param fileNameInputPattern Name of the file containing the input pattern used for
     * the training
     * @param desiredOutputPattern
     * @throws IOException if an error occurs while reading a file
     * @throws PatternCreateException
     * @throws PatternLayerMismatchException
     */
    public TrainingCoordinator(Neuron[][] layers, String fileNameInputPattern,
          Pattern[] desiredOutputPattern) throws IOException, PatternCreateException, 
          PatternLayerMismatchException {
      super(layers, fileNameInputPattern);
      this.teacher = new Teacher(desiredOutputPattern, layers);
      if(layers[layers.length-1].length != desiredOutputPattern[0].entries.length) {
          throw new PatternLayerMismatchException("The size of the desired output pattern " +
          "doesn't match the size of the output layer!");
      }
    }
    
    /**
     * @param net The neural net to be coordinated
     * @param inputPattern the input pattern used for
     * the training
     * @param desiredOutputPattern
     * @throws PatternLayerMismatchException
     */
    public TrainingCoordinator(NeuralNet net, Pattern[] inputPattern, 
    		Pattern[] desiredOutputPattern) throws PatternLayerMismatchException {
    	this(net.getLayers(), inputPattern, desiredOutputPattern);
    }
    
    /**
     * @param layers The layers of the neural net to be coordinated
     * @param inputPattern the input pattern used for
     * the training
     * @param desiredOutputPattern
     * @throws PatternLayerMismatchException
     */
    public TrainingCoordinator(Neuron[][] layers, Pattern[] inputPattern, 
    		Pattern[] desiredOutputPattern) throws PatternLayerMismatchException {
    	super(layers, inputPattern);
    	this.teacher = new Teacher(desiredOutputPattern, layers);
    	if(layers[layers.length-1].length != desiredOutputPattern[0].entries.length) {
            throw new PatternLayerMismatchException("The size of the desired output pattern " +
            "doesn't match the size of the output layer!");
        }
    }
    
    /**
     * @param net The neural net to be coordinated
     * @param giver
     * @param reader
     * @param desiredOutputPattern
     * @throws PatternLayerMismatchException
     */
    public TrainingCoordinator(NeuralNet net, PatternGiver giver, PatternReader reader, 
          Pattern[] desiredOutputPattern) throws PatternLayerMismatchException  {
    	this(net.getLayers(), giver, reader, desiredOutputPattern);
    }
  
    /**
     * @param layers The layers of the neural net to be coordinated
     * @param giver
     * @param reader
     * @param desiredOutputPattern
     * @throws PatternLayerMismatchException
     */
    public TrainingCoordinator(Neuron[][] layers, PatternGiver giver, PatternReader reader, 
          Pattern[] desiredOutputPattern) throws PatternLayerMismatchException  {
      super(layers, giver, reader);
      this.teacher = new Teacher(desiredOutputPattern, layers);
      if(layers[layers.length-1].length != desiredOutputPattern[0].entries.length) {
          throw new PatternLayerMismatchException("The size of the desired output pattern " +
          "doesn't match the size of the output layer!");
      }
    }
        
    //METHODS
    
    /**
     * This method overwrites the method of the superclass. It does the
     * same as {@link de.webdings.jannis.neuralnet.Coordinator#start()}, but
     * additionally calls the method {@link Teacher#teach()} at the appropriate
     * time.
     * 
     * @see de.webdings.jannis.neuralnet.Coordinator#start()
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
      //Lehrer starten
      teacher.teach();
      //Weitermachen
      if(giver.numberSent() == reader.numberOfPatternsRead()) {
        clearAll();
        if(reader.numberOfPatternsRead() < reader.getNumberOfPatternsToRead()){
          start();
        }
      } else {
        throw new PatternGiverReaderCommunicationException("There was a problem in communication " +
        		"between PatternGiver and PatternReader!");
      }
    }
}
