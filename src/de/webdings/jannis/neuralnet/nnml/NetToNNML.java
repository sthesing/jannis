/* NetToNNML.java - Copyright (c) 2005 by Stefan Thesing
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

/**
 * NetToNNML is used to convert a neural net of to a String 
 * containing a NNML representation
 * of that net. The purpose of this String is to be written
 * to a file.
 * NetToNNML is abstract, so a concrete subclass of 
 * NetToNNML must be used.
 * 
 * @author Stefan Thesing<br>
 * Website: <a href="http://www.webdings.de">http://www.webdings.de</a>
 * @version 0.1 11.08.2005
 */
public abstract class NetToNNML {
    /**
     * the string to be returned by {@link #generateString(NeuralNet)} and
     * {@link #generateString(Neuron[][])}
     */
    String s;
    /**
     * a {@link NeuronIDFinder} to identify the IDs of the contained neurons. The IDs are
     * needed pieces of information for the NNML representation of the net.
     */
    NeuronIDFinder finder;
    /**
     * @param layers the layers of the net to be converted to NNML
     * @return a String containing a NNML representation
     * of the net
     * @throws NeuronTypeMismatchException if the layers contain neurons of different types
     * @throws NeuronNotFoundException 
     */
    public abstract String generateString(Neuron[][] layers) 
    	throws NeuronTypeMismatchException, NeuronNotFoundException;
    /**
     * @param net the net to be converted to NNML
     * @return a String containing a NNML representation
     * of the net
     * @throws NeuronTypeMismatchException if the layers contain neurons of different types
     * @throws NeuronNotFoundException 
     */
    public abstract String generateString(NeuralNet net)
    	throws NeuronTypeMismatchException, NeuronNotFoundException;
}
