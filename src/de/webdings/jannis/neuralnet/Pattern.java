/* Pattern.java - Copyright (c) 2005 by Stefan Thesing
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

import de.webdings.jannis.exceptions.PatternCreateException;

/**
 * Pattern is used represent an input pattern or output 
 * pattern a neural net processes or produces. 
 * 
 * @author Stefan Thesing<br>
 * Website: <a href="http://www.webdings.de">http://www.webdings.de</a>
 * @version 0.1 10.08.2005
 */
public class Pattern {
    //  Attribute
    /**
     * The entries of the pattern.
     */
    public boolean[] entries;
    //Konstruktor
    /**
     * Constructs a Pattern from an array containing <code>true</code> or 
     * <code>false</code>, where <code>true</code> means the neuron is supposed to 
     * fire (for an input pattern) or it has fired (for an output pattern) and 
     * <code>false</code> means it is not supposed to fire or hasn't fired.
     * @param entries
     */
    public Pattern(boolean[] entries) {
      this.entries = entries;
    }
    /**
     * Counstructs a Pattern from a String containing <code>0</code>s and <code>1</code>s, 
     * where <code>1</code> corresponds to <code>true</code> and <code>0</code> to 
     * <code>false</code>.
     * @param entries
     * @throws PatternCreateException if the String contains characters other than 
     * <code>0</code> and <code>1</code>
     */
    public Pattern(String entries) throws PatternCreateException {
        this(entries.toCharArray());
    }
    /**
     * Counstructs a Pattern from an array containing <code>0</code>s and <code>1</code>s, 
     * where <code>1</code> corresponds to <code>true</code> and <code>0</code> to 
     * <code>false</code>.
     * @param entries
     * @throws PatternCreateException if the array contains characters other than 
     * <code>0</code> and <code>1</code>
     */
    public Pattern(char[] entries) throws PatternCreateException {
        boolean[] temp = new boolean[entries.length];
        for(int i=0;i<entries.length;++i){
            if(entries[i]=='0') {
                temp[i] = false;
            } else if(entries[i]=='1') {
                temp[i] = true;
            } else {
                throw new PatternCreateException("Can't " +
                		"create pattern. Data contains " +
                		"characters other than 0 and 1");
            }
        }
        this.entries = temp;
    }
}
