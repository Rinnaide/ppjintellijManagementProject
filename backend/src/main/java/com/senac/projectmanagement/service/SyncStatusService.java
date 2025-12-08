package com.senac.projectmanagement.service;

import com.senac.projectmanagement.dto.SyncStatusRequestDTO;
import com.senac.projectmanagement.dto.SyncStatusResponseDTO;
import com.senac.projectmanagement.entity.SyncStatus;
import com.senac.projectmanagement.entity.User;
import com.senac.projectmanagement.repository.SyncStatusRepository;
import com.senac.projectmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SyncStatusService {

    @Autowired
    private SyncStatusRepository syncStatusRepository;

    @Autowired
    private UserRepository userRepository;

    public SyncStatus createSyncStatus(Long userId, String tableName) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            SyncStatus syncStatus = new SyncStatus(user.get(), tableName);
            return syncStatusRepository.save(syncStatus);
        }
        throw new RuntimeException("Usuário não encontrado");
    }

    public Optional<SyncStatus> getSyncStatusByUserAndTable(Long userId, String tableName) {
        return syncStatusRepository.findByUser_UserIdAndSyncStatusTableName(userId, tableName);
    }

    public SyncStatus updateLastSync(Long userId, String tableName) {
        Optional<SyncStatus> optionalSyncStatus = syncStatusRepository.findByUser_UserIdAndSyncStatusTableName(userId, tableName);
        if (optionalSyncStatus.isPresent()) {
            SyncStatus syncStatus = optionalSyncStatus.get();
            syncStatus.setSyncStatusLastSyncAt(LocalDateTime.now());
            return syncStatusRepository.save(syncStatus);
        }
        throw new RuntimeException("SyncStatus não encontrado");
    }

    public void deleteSyncStatus(Long userId, String tableName) {
        Optional<SyncStatus> optionalSyncStatus = syncStatusRepository.findByUser_UserIdAndSyncStatusTableName(userId, tableName);
        if (optionalSyncStatus.isPresent()) {
            syncStatusRepository.delete(optionalSyncStatus.get());
        }
    }

    public SyncStatusResponseDTO createSyncStatus(SyncStatusRequestDTO requestDTO) {
        Optional<User> user = userRepository.findById(requestDTO.getUserId());
        if (user.isPresent()) {
            SyncStatus syncStatus = new SyncStatus(user.get(), requestDTO.getTableName());
            if (requestDTO.getLastSyncAt() != null) {
                syncStatus.setSyncStatusLastSyncAt(requestDTO.getLastSyncAt());
            }
            if (requestDTO.getStatus() != null) {
                syncStatus.setSyncStatusStatus(requestDTO.getStatus());
            }
            if (requestDTO.getErrorMessage() != null) {
                syncStatus.setSyncStatusErrorMessage(requestDTO.getErrorMessage());
            }
            SyncStatus savedSyncStatus = syncStatusRepository.save(syncStatus);
            return convertToResponseDTO(savedSyncStatus);
        }
        throw new RuntimeException("Usuário não encontrado");
    }

    public List<SyncStatusResponseDTO> getAllSyncStatuses() {
        List<SyncStatus> syncStatuses = syncStatusRepository.findAll();
        return syncStatuses.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public Optional<SyncStatusResponseDTO> getSyncStatusById(Long id) {
        return syncStatusRepository.findById(id).map(this::convertToResponseDTO);
    }

    public List<SyncStatusResponseDTO> getSyncStatusesByUser(Long userId) {
        List<SyncStatus> syncStatuses = syncStatusRepository.findByUser_UserId(userId);
        return syncStatuses.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public SyncStatusResponseDTO updateSyncStatus(Long id, SyncStatusRequestDTO requestDTO) {
        Optional<SyncStatus> optionalSyncStatus = syncStatusRepository.findById(id);
        if (optionalSyncStatus.isPresent()) {
            SyncStatus syncStatus = optionalSyncStatus.get();
            if (requestDTO.getLastSyncAt() != null) {
                syncStatus.setSyncStatusLastSyncAt(requestDTO.getLastSyncAt());
            }
            if (requestDTO.getStatus() != null) {
                syncStatus.setSyncStatusStatus(requestDTO.getStatus());
            }
            if (requestDTO.getErrorMessage() != null) {
                syncStatus.setSyncStatusErrorMessage(requestDTO.getErrorMessage());
            }
            SyncStatus savedSyncStatus = syncStatusRepository.save(syncStatus);
            return convertToResponseDTO(savedSyncStatus);
        }
        throw new RuntimeException("SyncStatus não encontrado");
    }

    public void deleteSyncStatus(Long id) {
        syncStatusRepository.deleteById(id);
    }

    private SyncStatusResponseDTO convertToResponseDTO(SyncStatus syncStatus) {
        SyncStatusResponseDTO dto = new SyncStatusResponseDTO();
        dto.setSyncStatusId(syncStatus.getSyncStatusId());
        dto.setUserId(syncStatus.getUser().getUserId());
        dto.setTableName(syncStatus.getSyncStatusTableName());
        dto.setLastSyncAt(syncStatus.getSyncStatusLastSyncAt());
        dto.setStatus(syncStatus.getSyncStatusStatus());
        dto.setErrorMessage(syncStatus.getSyncStatusErrorMessage());
        dto.setCreatedAt(syncStatus.getSyncStatusCreatedAt());
        dto.setUpdatedAt(syncStatus.getSyncStatusUpdatedAt());
        return dto;
    }
}
