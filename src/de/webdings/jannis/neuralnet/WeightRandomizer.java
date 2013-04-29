/* WeightRandomizer.java - Copyright (c) 2005 by Stefan Thesing
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

import java.util.Random;

/**
 * WeightRandomizer is used to generate random synapse weights.
 * These are often needed for freshly defined neural nets 
 * that will be trained.</p>
 * <p>By standard, it generates weights ranging from -0.2 
 * and +0.2, yet different ranges can be specified.</p>
 * <p>It uses the {@link java.util.Random} class. 
 * 
 * 
 * @author Stefan Thesing<br>
 * Website: <a href="http://www.webdings.de">http://www.webdings.de</a>
 * @version 0.1, 11.08.2005
 * @see Synapse
 * @see java.util.Random
 */
public class WeightRandomizer {
    //ATTRIBUTES
    private int i=0;
    //METHODS
    /**
     * @return a random weight between -0.2 and +0.2
     */
    public float generateRandomWeight() {
        return generateRandomWeight(2);
    }
    
    /**
     * @param maxDigitAfterDot
     * @return a random weight between -0.x and +0.x, where
     * x is the specified maximum digit after the dot
     */
    public float generateRandomWeight(int maxDigitAfterDot) {
        return generateRandomWeight(0, maxDigitAfterDot);
    }
    
    /**
     * @param maxDigitBeforeDot
     * @param maxDigitAfterDot
     * @return a random weight between -x.y and +x.y, where
     * x is the specified maximum number before the dot and
     * y is the specified maximum digit after the dot
     */
    public float generateRandomWeight(int maxDigitBeforeDot, int maxDigitAfterDot) {
      Random generator = new Random(System.currentTimeMillis()+i);
      int digitBeforeDot = generator.nextInt(maxDigitBeforeDot+1);
      int digitAfterDot = generator.nextInt(maxDigitAfterDot+1);
      String s = digitBeforeDot+ "." + digitAfterDot;
      Float gewicht = new Float(s);
      if(generator.nextBoolean()) {
        gewicht = new Float(gewicht.floatValue() * -1);
      }
      ++i;
      return gewicht.floatValue();
    }
}
