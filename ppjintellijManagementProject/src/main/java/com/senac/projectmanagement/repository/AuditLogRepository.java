package com.senac.projectmanagement.repository;

import com.senac.projectmanagement.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByUser_UserIdOrderByAuditCreatedAtDesc(Long userId);

    List<AuditLog> findByAuditTableNameAndAuditRecordIdOrderByAuditCreatedAtDesc(String tableName, Integer recordId);

    List<AuditLog> findByAuditTableName(String tableName);
}
