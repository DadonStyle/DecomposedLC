package com.nd.CompanyController.Exeptions;

import lombok.RequiredArgsConstructor;

public class CompanyException extends Exception {
   public CompanyException(String msg){
       super(msg);
   }
}
