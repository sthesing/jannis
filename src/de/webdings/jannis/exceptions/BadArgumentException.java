/* BadArgumentException.java - Copyright (c) 2005 by Stefan Thesing
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
 * BadArgumentException is used to handle errors in passed 
 * arguments when starting {@link de.webdings.jannis.Jannis}.
 * 
 * @author Stefan Thesing<br>
 * Website: <a href="http://www.webdings.de">http://www.webdings.de</a>
 * @version 0.1 10.08.2005
 */
public class BadArgumentException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6194936181692176889L;

	/**
     * 
     */
    public BadArgumentException() {
        super();
    }

    /**
     * @param message
     */
    public BadArgumentException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public BadArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public BadArgumentException(Throwable cause) {
        super(cause);
    }

}
