package com.senac.projectmanagement.service;

import com.senac.projectmanagement.dto.AuditLogRequestDTO;
import com.senac.projectmanagement.dto.AuditLogResponseDTO;
import com.senac.projectmanagement.entity.AuditLog;
import com.senac.projectmanagement.entity.AuditLogAction;
import com.senac.projectmanagement.entity.User;
import com.senac.projectmanagement.repository.AuditLogRepository;
import com.senac.projectmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private UserRepository userRepository;

    public AuditLog createAuditLog(Long userId, String tableName, Integer recordId, AuditLogAction action, String ipAddress, String userAgent) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            AuditLog auditLog = new AuditLog(user.get(), tableName, recordId, action, ipAddress, userAgent);
            return auditLogRepository.save(auditLog);
        }
        throw new RuntimeException("Usuário não encontrado");
    }

    public AuditLogResponseDTO createAuditLog(AuditLogRequestDTO auditLogRequestDTO) {
        Optional<User> user = userRepository.findById(auditLogRequestDTO.getUserId());
        if (user.isPresent()) {
            AuditLog auditLog = new AuditLog(user.get(), auditLogRequestDTO.getTableName(), auditLogRequestDTO.getRecordId(), auditLogRequestDTO.getAction(), auditLogRequestDTO.getIpAddress(), auditLogRequestDTO.getUserAgent());
            AuditLog savedAuditLog = auditLogRepository.save(auditLog);
            return convertToResponseDTO(savedAuditLog);
        }
        throw new RuntimeException("Usuário não encontrado");
    }

    public List<AuditLogResponseDTO> getAllAuditLogs() {
        List<AuditLog> auditLogs = auditLogRepository.findAll();
        return auditLogs.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public Optional<AuditLogResponseDTO> getAuditLogById(Long id) {
        return auditLogRepository.findById(id).map(this::convertToResponseDTO);
    }

    public List<AuditLogResponseDTO> getAuditLogsByUser(Long userId) {
        List<AuditLog> auditLogs = auditLogRepository.findByUser_UserIdOrderByAuditCreatedAtDesc(userId);
        return auditLogs.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public List<AuditLogResponseDTO> getAuditLogsByTable(String tableName) {
        List<AuditLog> auditLogs = auditLogRepository.findByAuditTableName(tableName);
        return auditLogs.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    private AuditLogResponseDTO convertToResponseDTO(AuditLog auditLog) {
        AuditLogResponseDTO dto = new AuditLogResponseDTO();
        dto.setAuditLogId(auditLog.getAuditLogId());
        dto.setUserId(auditLog.getUser().getUserId());
        dto.setTableName(auditLog.getAuditTableName());
        dto.setRecordId(auditLog.getAuditRecordId());
        dto.setAction(auditLog.getAuditAction());
        dto.setIpAddress(auditLog.getAuditIpAddress());
        dto.setUserAgent(auditLog.getAuditUserAgent());
        dto.setCreatedAt(auditLog.getAuditCreatedAt());
        return dto;
    }
}
