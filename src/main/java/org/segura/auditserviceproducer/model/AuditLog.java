package org.segura.auditserviceproducer.model;
/*
 * Created by Daniel - 18/11/2024 (19:31)
 */


import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AuditLog {

    private String userEmail;
    private String userId;
    private String apiEndpointCalled;
    private String crudOperation;
    private String responseStatus;
    private String dataBefore;
    private String dataAfter;
    private LocalDateTime actionTimeStamp;

    public AuditLog() {
    }


    public String getEmail() {
        return userEmail;
    }

    public void setEmail(String email) {
        this.userEmail = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApiEndpoint() {
        return apiEndpointCalled;
    }

    public void setApiEndpoint(String apiEndpoint) {
        this.apiEndpointCalled = apiEndpoint;
    }

    public String getCrudOperation() {
        return crudOperation;
    }

    public void setCrudOperation(String crudOperation) {
        this.crudOperation = crudOperation;
    }

    public String getResponse() {
        return responseStatus;
    }

    public void setResponse(String response) {
        this.responseStatus = response;
    }

    public String getDataBefore() {
        return dataBefore;
    }

    public void setDataBefore(String dataBefore) {
        this.dataBefore = dataBefore;
    }

    public String getDataAfter() {
        return dataAfter;
    }

    public void setDataAfter(String dataAfter) {
        this.dataAfter = dataAfter;
    }

    public LocalDateTime getTimestamp() {
        return actionTimeStamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.actionTimeStamp = timestamp;
    }
}
