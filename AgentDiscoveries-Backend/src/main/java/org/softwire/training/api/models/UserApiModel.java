package org.softwire.training.api.models;

import lombok.Getter;
import lombok.Setter;

/**
 * The UserApiModel is a version of the domain model with a password field instead of a hashed password.
 *
 * This should as the model for requests/responses for the API though responses should not include the password.
 */
@Getter
@Setter
public class UserApiModel {

    private int userId;
    private String username;
    private String password;
    private Integer agentId; // Nullable
    private boolean admin;

}
