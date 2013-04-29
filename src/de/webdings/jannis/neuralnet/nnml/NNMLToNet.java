/* NNMLToNet.java - Copyright (c) 2005 by Stefan Thesing
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

import de.webdings.jannis.neuralnet.Neuron;

/**
 * NNMLToNet is used to construct a neural net from
 * a NNML representation.
 * NNMLToNet is abstract, so a concrete subclass has to
 * be used.
 * 
 * @author Stefan Thesing<br>
 * Website: <a href="http://www.webdings.de">http://www.webdings.de</a>
 * @version 0.1 11.08.2005
 */
public abstract class NNMLToNet {
    /**
     * This method is concrete, yet it doesn't do anything
     * on it's own. It simply calls the abstract method of 
     * the same name.<br>
     * The implementation is:<br>
     * <code>
     * public Neuron[][] convertToNet(char[] c) throws Exception {<br>
     *  return convertToNet(new String(c));<br>
     * }
     * </code>
     * So this method calls the method of a concrete 
     * subclass of NNMLToNet.
     * @param c NNML representation of a neural net
     * @return array containing layers of Neurons
     * @throws Exception
     */
    public Neuron[][] convertToNet(char[] c) throws Exception {
        return convertToNet(new String(c));
    }
    /**
     * This method is concrete, yet it doesn't do anything
     * on it's own. It simply calls the abstract method of 
     * the same name.<br>
     * The implementation is:<br>
     * <code>
     * public Neuron[][] convertToNet(StringBuffer s) throws Exception {<br>
     *  return convertToNet(new String(s));<br>
     * }
     * </code>
     * So this method calls the method of a concrete 
     * subclass of NNMLToNet.
     * @param s NNML representation of a neural net
     * @return array containing layers of Neurons
     * @throws Exception
     */
    public Neuron[][] convertToNet(StringBuffer s) throws Exception {
        return convertToNet(new String(s));
    }
    
    /**
     * This is an abstract method to be overwritten by
     * concrete subclasses.
     * @param s NNML representation of a neural net
     * @return array containing layers of Neurons
     * @throws Exception
     */
    public abstract Neuron[][] convertToNet(String s) throws Exception;
}
