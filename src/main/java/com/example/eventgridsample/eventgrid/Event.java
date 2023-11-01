package com.example.eventgridsample.eventgrid;

public class Event<T> {

    private String id;
    private String topic;
    private String subject;
    private T data;
    private String eventType;
    private String eventTime;
    private String metadataVersion;
    private String dataVersion;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getMetadataVersion() {
        return metadataVersion;
    }

    public void setMetadataVersion(String metadataVersion) {
        this.metadataVersion = metadataVersion;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }



//    public class Data{
//
//        public String getValidationCode() {
//            return validationCode;
//        }
//
//        public void setValidationCode(String validationCode) {
//            this.validationCode = validationCode;
//        }
//
//        public String getValidationUrl() {
//            return validationUrl;
//        }
//
//        public void setValidationUrl(String validationUrl) {
//            this.validationUrl = validationUrl;
//        }
//
//        private String validationCode;
//        private String validationUrl;
//    }
}


