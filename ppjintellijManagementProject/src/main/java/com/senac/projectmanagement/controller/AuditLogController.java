package com.senac.projectmanagement.controller;

import com.senac.projectmanagement.dto.AuditLogRequestDTO;
import com.senac.projectmanagement.dto.AuditLogResponseDTO;
import com.senac.projectmanagement.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @PostMapping
    public ResponseEntity<AuditLogResponseDTO> createAuditLog(@Valid @RequestBody AuditLogRequestDTO auditLogRequestDTO) {
        AuditLogResponseDTO createdAuditLog = auditLogService.createAuditLog(auditLogRequestDTO);
        return ResponseEntity.ok(createdAuditLog);
    }

    @GetMapping
    public ResponseEntity<List<AuditLogResponseDTO>> getAllAuditLogs() {
        List<AuditLogResponseDTO> auditLogs = auditLogService.getAllAuditLogs();
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditLogResponseDTO> getAuditLogById(@PathVariable Long id) {
        return auditLogService.getAuditLogById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditLogResponseDTO>> getAuditLogsByUser(@PathVariable Long userId) {
        List<AuditLogResponseDTO> auditLogs = auditLogService.getAuditLogsByUser(userId);
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/table/{tableName}")
    public ResponseEntity<List<AuditLogResponseDTO>> getAuditLogsByTable(@PathVariable String tableName) {
        List<AuditLogResponseDTO> auditLogs = auditLogService.getAuditLogsByTable(tableName);
        return ResponseEntity.ok(auditLogs);
    }
}
