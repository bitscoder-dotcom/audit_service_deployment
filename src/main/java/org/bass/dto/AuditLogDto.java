package org.bass.dto;
/*
 * Created by Daniel - 18/11/2024 (19:29)
 */

import java.time.LocalDateTime;

public class AuditLogDto {
    private String email;
    private String userId;
    private String apiEndpoint;
    private String crudOperation;
    private String response;
    private String dataBefore;
    private String dataAfter;
    private LocalDateTime timestamp;

    public AuditLogDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public void setApiEndpoint(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    public String getCrudOperation() {
        return crudOperation;
    }

    public void setCrudOperation(String crudOperation) {
        this.crudOperation = crudOperation;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
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
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
