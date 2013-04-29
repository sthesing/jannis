/* PatternGiverReaderCommunicationException.java - Copyright (c) 2005 by Stefan Thesing
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
 * PatternGiverReaderCommunicationException is thrown when there is a problem
 * in the communication between {@link de.webdings.jannis.neuralnet.PatternGiver} and
 * {@link de.webdings.jannis.neuralnet.PatternReader}.
 * @author Stefan Thesing<br>
 * Website: <a href="http://www.webdings.de">http://www.webdings.de</a>
 *
 * @version 0.1 10.08.2005
 */
public class PatternGiverReaderCommunicationException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8903122370237467163L;

	/**
     * 
     */
    public PatternGiverReaderCommunicationException() {
        super();
    }

    /**
     * @param message
     */
    public PatternGiverReaderCommunicationException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public PatternGiverReaderCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public PatternGiverReaderCommunicationException(Throwable cause) {
        super(cause);
    }

}
