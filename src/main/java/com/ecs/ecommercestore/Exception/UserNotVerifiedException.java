package com.ecs.ecommercestore.Exception;

import lombok.Getter;

/**
 * Exception to highlight a user does not have a verified email address.
 * @author mohamednicer
 */

@Getter
public class UserNotVerifiedException extends Exception{

    /** Did we send a new email? */
    private boolean newEmailSent;

    /**
     * Constructor.
     * @param newEmailSent Was a new email sent?
     */
    public UserNotVerifiedException(boolean newEmailSent) {
        this.newEmailSent = newEmailSent;
    }
}
