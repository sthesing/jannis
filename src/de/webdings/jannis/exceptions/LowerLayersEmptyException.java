/* LowerLayersEmptyException.java - Copyright (c) 2005 by Stefan Thesing
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
package de.webdings.jannis.exceptions;

/**
 * LowerLayersEmptyException is used prevent action in a layer of a neural net that
 * has one or more empty layer(s) between it and the input layer. A net with such
 * empty layers couldn't operate at all, so classes like 
 * {@link de.webdings.jannis.neuralnet.NeuralNet}
 * make sure that layers are filled up one by one beginning with the input layer and ending
 * with the output layer. Whenever a process tries to fill a 
 * {@link de.webdings.jannis.neuralnet.Neuron}
 * into a layer that has one or more empty layers between it and the input layer, a 
 * LowerLayersEmptyException is thrown.
 * @see de.webdings.jannis.neuralnet.NeuralNet#addNeuron(Neuron, int)
 * 
 * @author Stefan Thesing<br>
 * Website: <a href="http://www.webdings.de">http://www.webdings.de</a>
 * @version 0.1 10.08.2005
 */
public class LowerLayersEmptyException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4925368406845425489L;

	/**
     * 
     */
    public LowerLayersEmptyException() {
        super();
    }

    /**
     * @param message
     */
    public LowerLayersEmptyException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public LowerLayersEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public LowerLayersEmptyException(Throwable cause) {
        super(cause);
    }

}
