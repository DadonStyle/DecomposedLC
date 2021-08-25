package com.nd.CustomerController.Exeptions;

    /**
     * uses custom exception in case of incorrect information sent to the other classes in the program
     */
public class CustomerControllerException extends Exception{
    /**
     * Constructor for creating new exception
     *
     * @param message - the custom message for the report
     */
    public CustomerControllerException(String message){
        super(message);
    }
}
