/* PatternConverter.java - Copyright (c) 2005 by Stefan Thesing
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
 * PatternConverter is used to convert 
 * {@link java.lang.String}s to arrays of {@link Pattern}s 
 * and vice versa. The character "0" corresponds to an
 * pattern entry of <code>false</code>, "1" to <code>true
 * </code>.
 * 
 * @author Stefan Thesing<br>
 * Website: <a href="http://www.webdings.de">http://www.webdings.de</a>
 * @version 0.1 01.08.2005
 */
public class PatternConverter {
    //methods
    /**
     * @param s The {@link java.lang.String} containing a
     * representation of the pattern
     * @param patternSize The size of the pattern (usually
     * the number of neurons contained in the input or 
     * output layer)
     * @return an array of {@link Pattern}s
     * @throws PatternCreateException if the string contains characters
     * other than <code>0</code> and <code>1</code>
     */
    public static Pattern[] strToPattern(String s, int patternSize) throws PatternCreateException {
        int numberOfPatterns = s.length()/patternSize;
        Pattern[] pattern = new Pattern[numberOfPatterns];
        char[] ablage = s.toCharArray();
        int i,j;
        int k=0;
        for(i=0;i<numberOfPatterns;++i){
          pattern[i] = new Pattern(new boolean[patternSize]);
          for(j=0;j<patternSize;++j) {
            if((ablage[k]=='1')||(ablage[k]=='0')) {
                pattern[i].entries[j] = (ablage[k]== '1');
                ++k;  
            } else {
                pattern = null;
                throw new PatternCreateException("Can't " +
                        "create pattern. Data contains " +
                        "characters other than 0 and 1");
            }
          }
        }
        return pattern;
    }
    
    /**
     * @param pattern an array of {@link Pattern}s
     * @param patternSize The size of the pattern (usually
     * the number of neurons contained in the input or 
     * output layer)
     * @return a {@link java.lang.String} representing
     * the patterns.
     */
    public static String patternToStr(Pattern[] pattern, int patternSize) {
        String s = new String();
        int i,j;
        for(i=0;i<pattern.length; ++i){
         for(j=0;j<patternSize;++j){
          if(pattern[i].entries[j]){
           s = s + "1";
          } else {
           s = s + "0";
          }
         }
        }
        return s;
    }
}
