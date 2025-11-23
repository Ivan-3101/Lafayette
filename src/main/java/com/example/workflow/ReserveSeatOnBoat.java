package com.example.workflow;

import jakarta.inject.Named;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

//adding this named annotation so that we can call this being by name
// https://youtu.be/zAbPRhVwwZ4?list=PLJG25HlmvsOVssaiPmavxv3htN_dXS3BW&t=342
// Alternatively you can call a java class by its full class path

// Now I'll be able to call the name of the class without needing to use the entire package name
@Named
public class ReserveSeatOnBoat implements JavaDelegate {
//    After you create the class you have to add the implements JavaDelegate line
//    click the red bulb also to auto import the things
//    This has to be added so we can call it from the engine, so we can get the context of the engine from the class
//    As I said this is a really important method because this allows us to get context from the engine in runtime and
//    we can decide what to do accordingly,
//    so we can get variables and set variables
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String money = "0.0";
        String ticketType = "Coach"; //default ticketType

        // WE ARE GOING TO ASK THE EXECUTION TO GET A VARIABLE CALLED MONEY
        // Remember, this variable was already added by the initial starting point of the process
        money = (String) delegateExecution.getVariable("money");

//        converting it to double
        double moneyDouble = Double.parseDouble(money);

        if (moneyDouble >= 10000) {
            ticketType = "First Class";
        } else if (moneyDouble >= 5000) {
            ticketType = "Business Class";
        }
         else if (moneyDouble <= 10) {
            ticketType = "Stowaway Class";
            throw new BpmnError("Fall_Overboard","A Cheap ticket has led to a small wave throwing you overboard.");
        }


//        at the end telling the execution to set the variable back to the instance
//        with the variable name ticketType,
//        Meaning this process will continue with the variable ticketType with the value that we added,
        delegateExecution.setVariable("ticketType", ticketType);
    }
}
