package org.bass.mapper;
/*
 * Created by Daniel - 18/11/2024 (20:49)
 */

import org.bass.dto.AuditLogDto;
import org.bass.model.AuditLog;
import org.springframework.stereotype.Component;

@Component
public class AuditLogMapper {

    public AuditLogDto toDto(AuditLog auditLog) {
        AuditLogDto dto = new AuditLogDto();
        dto.setEmail(auditLog.getEmail());
        dto.setUserId(auditLog.getUserId());
        dto.setApiEndpoint(auditLog.getApiEndpoint());
        dto.setCrudOperation(auditLog.getCrudOperation());
        dto.setResponse(auditLog.getResponse());
        dto.setDataBefore(auditLog.getDataBefore());
        dto.setDataAfter(auditLog.getDataAfter());
        dto.setTimestamp(auditLog.getTimestamp());
        return dto;
    }
/*
    public AuditLog toEntity(AuditLogDto dto) {
        AuditLog auditLog = new AuditLog();
        auditLog.setEmail(dto.getEmail());
        auditLog.setUserId(dto.getUserId());
        auditLog.setApiEndpoint(dto.getApiEndpoint());
        auditLog.setCrudOperation(dto.getCrudOperation());
        auditLog.setResponse(dto.getResponse());
        auditLog.setDataBefore(dto.getDataBefore());
        auditLog.setDataAfter(dto.getDataAfter());
        auditLog.setTimestamp(dto.getTimestamp());
        return auditLog;
    }

 */
}
