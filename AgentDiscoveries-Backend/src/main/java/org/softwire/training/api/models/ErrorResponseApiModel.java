package org.softwire.training.api.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorResponseApiModel {

    private int errorCode;
    private String message;

    public void setMessage(String message) {this.message = message;}
}
