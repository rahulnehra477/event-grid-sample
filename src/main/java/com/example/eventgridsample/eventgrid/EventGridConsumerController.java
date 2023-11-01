package com.example.eventgridsample.eventgrid;

import com.google.gson.Gson;
import com.microsoft.azure.eventgrid.models.SubscriptionValidationResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EventGridConsumerController {

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody List<Event> body, @RequestHeader HttpHeaders headers) {

        System.out.println("In Validate Function");

        Iterator itr = headers.keySet().iterator();

        while (itr.hasNext()) {
            String key = itr.next().toString();
            System.out.println("Key: " + key + ", Value: " + headers.get(key));
        }

        Gson g = new Gson();

        System.out.println("JSON: " + g.toJson(body));

        boolean isValidationEvent = body.get(0).getEventType().equalsIgnoreCase("Microsoft.EventGrid.SubscriptionValidationEvent");


        if (isValidationEvent) {
            String validationCode = g.toJsonTree(body).getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject("data").getAsJsonObject().get("validationCode").getAsString();
            System.out.println("Validation Event");
            SubscriptionValidationResponse subscriptionValidationResponse = new SubscriptionValidationResponse();
            subscriptionValidationResponse.withValidationResponse(validationCode);
            System.out.println("Validation Response: " + subscriptionValidationResponse.validationResponse());
            return ResponseEntity.ok(subscriptionValidationResponse);
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody List<Event> body, @RequestHeader HttpHeaders headers){

        System.out.println("In subscribe Function");

        printHeaders(headers);

        Gson g = new Gson();

        System.out.println("JSON: " + g.toJson(body));

        boolean isValidationEvent = body.get(0).getEventType().equalsIgnoreCase("Microsoft.EventGrid.SubscriptionValidationEvent");

        if (isValidationEvent) {
            String validationCode = g.toJsonTree(body).getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject("data").getAsJsonObject().get("validationCode").getAsString();
            System.out.println("Validation Event");
            SubscriptionValidationResponse subscriptionValidationResponse = new SubscriptionValidationResponse();
            subscriptionValidationResponse.withValidationResponse(validationCode);
            System.out.println("Validation Response: " + subscriptionValidationResponse.validationResponse());
            return ResponseEntity.ok(subscriptionValidationResponse);
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PostMapping("/build")
    public ResponseEntity<?> build(@RequestBody List<Event> body, @RequestHeader HttpHeaders headers){
        System.out.println("In build Function");
        printHeaders(headers);
        Gson g = new Gson();
        System.out.println("JSON: " + g.toJson(body));
        boolean isValidationEvent = body.get(0).getEventType().equalsIgnoreCase("Microsoft.Storage.BlobCreated");
        if (isValidationEvent) {
            System.out.println("blob created or deleted event");
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private void printHeaders(HttpHeaders headers){
        Iterator itr = headers.keySet().iterator();

        while (itr.hasNext()) {
            String key = itr.next().toString();
            System.out.println("Key: " + key + ", Value: " + headers.get(key));
        }
    }
}