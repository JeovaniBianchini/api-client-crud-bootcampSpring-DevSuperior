package com.bianchini.apicrudclient.services.exceptions;

public class ClientNotFoundException extends RuntimeException{

    public ClientNotFoundException(String msg){
        super(msg);
    }
}
