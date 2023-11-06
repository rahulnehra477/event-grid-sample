package com.example.eventgridsample.eventgrid;

import com.google.gson.Gson;
import com.microsoft.azure.eventgrid.models.SubscriptionValidationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class EventGridConsumerController {

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody List<Event> body, @RequestHeader HttpHeaders headers) {

        log.info("In Validate Function");

        Iterator itr = headers.keySet().iterator();

        while (itr.hasNext()) {
            String key = itr.next().toString();
            log.info("Key: " + key + ", Value: " + headers.get(key));
        }

        Gson g = new Gson();

        log.info("JSON: " + g.toJson(body));

        boolean isValidationEvent = body.get(0).getEventType().equalsIgnoreCase("Microsoft.EventGrid.SubscriptionValidationEvent");


        if (isValidationEvent) {
            String validationCode = g.toJsonTree(body).getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject("data").getAsJsonObject().get("validationCode").getAsString();
            log.info("Validation Event");
            SubscriptionValidationResponse subscriptionValidationResponse = new SubscriptionValidationResponse();
            subscriptionValidationResponse.withValidationResponse(validationCode);
            log.info("Validation Response: " + subscriptionValidationResponse.validationResponse());
            return ResponseEntity.ok(subscriptionValidationResponse);
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody List<Event> body, @RequestHeader HttpHeaders headers, @RequestHeader(value = "supplierName") String supplierName){

        log.info("In subscribe Function");
        log.info("header supplierName" + supplierName);

        printHeaders(headers);

        Gson g = new Gson();

        log.info("JSON: " + g.toJson(body));

        boolean isValidationEvent = body.get(0).getEventType().equalsIgnoreCase("Microsoft.EventGrid.SubscriptionValidationEvent");
        boolean isCreateEvent = body.get(0).getEventType().equalsIgnoreCase("Microsoft.Storage.BlobCreated");
        boolean isDeleteEvent = body.get(0).getEventType().equalsIgnoreCase("Microsoft.Storage.BlobDeleted");
        if (isValidationEvent) {
            String validationCode = g.toJsonTree(body).getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject("data").getAsJsonObject().get("validationCode").getAsString();
            log.info("Validation Event");
            SubscriptionValidationResponse subscriptionValidationResponse = new SubscriptionValidationResponse();
            subscriptionValidationResponse.withValidationResponse(validationCode);
            log.info("Validation Response: " + subscriptionValidationResponse.validationResponse());
            return ResponseEntity.ok(subscriptionValidationResponse);
        } else if(isCreateEvent){
            log.info("blob created event");
        } else if (isDeleteEvent){
            log.info("blob deleted event");
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PostMapping("/build")
    public ResponseEntity<?> build(@RequestBody List<Event> body, @RequestHeader HttpHeaders headers){
        log.info("In build Function");
        printHeaders(headers);
        Gson g = new Gson();
        log.info("JSON: " + g.toJson(body));
        boolean isValidationEvent = body.get(0).getEventType().equalsIgnoreCase("Microsoft.Storage.BlobCreated");
        if (isValidationEvent) {
            log.info("blob created or deleted event");
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private void printHeaders(HttpHeaders headers){
        Iterator itr = headers.keySet().iterator();

        while (itr.hasNext()) {
            String key = itr.next().toString();
            log.info("Key: " + key + ", Value: " + headers.get(key));
        }
    }
}
