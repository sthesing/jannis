/* PatternLayerMismatchException.java - Copyright (c) 2005 by Stefan Thesing
 <p>This file is part of de.webdings.jannis.</p>
 <p>de.webdings.jannis is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.</p>
<p>de.webdings.jannis is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.</p>
<p>You should have received a copy of the GNU General Public License
along with de.webdings.jannis; if not, write to the<br>
Free Software Foundation, Inc.,<br>
51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA<br>
*/
package de.webdings.jannis.exceptions;

/**
 * PatternLayerMismatchException is thrown when a net is trained, but the size of an
 * output pattern doesn't match the size of the output layer.
 * @author Stefan Thesing<br>
 * Website: <a href="http://www.webdings.de">http://www.webdings.de</a>
 *
 * @version 0.1 11.08.2005
 */
public class PatternLayerMismatchException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6750284067426153503L;

	/**
	 * 
	 */
	public PatternLayerMismatchException() {
		super();
	}

	/**
	 * @param message
	 */
	public PatternLayerMismatchException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PatternLayerMismatchException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public PatternLayerMismatchException(Throwable cause) {
		super(cause);
	}

}
