package com.senac.projectmanagement.controller;

import com.senac.projectmanagement.dto.SyncStatusRequestDTO;
import com.senac.projectmanagement.dto.SyncStatusResponseDTO;
import com.senac.projectmanagement.service.SyncStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sync-statuses")
@Tag(name = "Sync Status Management", description = "APIs for managing sync statuses")
public class SyncStatusController {

    @Autowired
    private SyncStatusService syncStatusService;

    @PostMapping
    @Operation(summary = "Create a new sync status")
    public ResponseEntity<SyncStatusResponseDTO> createSyncStatus(@Valid @RequestBody SyncStatusRequestDTO requestDTO) {
        SyncStatusResponseDTO responseDTO = syncStatusService.createSyncStatus(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all sync statuses")
    public ResponseEntity<List<SyncStatusResponseDTO>> getAllSyncStatuses() {
        List<SyncStatusResponseDTO> syncStatuses = syncStatusService.getAllSyncStatuses();
        return new ResponseEntity<>(syncStatuses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get sync status by ID")
    public ResponseEntity<SyncStatusResponseDTO> getSyncStatusById(@PathVariable Long id) {
        return syncStatusService.getSyncStatusById(id)
                .map(syncStatus -> new ResponseEntity<>(syncStatus, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get sync statuses by user ID")
    public ResponseEntity<List<SyncStatusResponseDTO>> getSyncStatusesByUser(@PathVariable Long userId) {
        List<SyncStatusResponseDTO> syncStatuses = syncStatusService.getSyncStatusesByUser(userId);
        return new ResponseEntity<>(syncStatuses, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update sync status")
    public ResponseEntity<SyncStatusResponseDTO> updateSyncStatus(@PathVariable Long id, @Valid @RequestBody SyncStatusRequestDTO requestDTO) {
        try {
            SyncStatusResponseDTO responseDTO = syncStatusService.updateSyncStatus(id, requestDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete sync status by ID")
    public ResponseEntity<Void> deleteSyncStatus(@PathVariable Long id) {
        syncStatusService.deleteSyncStatus(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
