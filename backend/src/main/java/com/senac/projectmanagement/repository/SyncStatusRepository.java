package com.senac.projectmanagement.repository;

import com.senac.projectmanagement.entity.SyncStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SyncStatusRepository extends JpaRepository<SyncStatus, Long> {

    Optional<SyncStatus> findByUser_UserIdAndSyncStatusTableName(Long userId, String tableName);

    List<SyncStatus> findByUser_UserId(Long userId);
}
