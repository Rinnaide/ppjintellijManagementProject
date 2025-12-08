package com.senac.projectmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senac.projectmanagement.entity.UserRoles;
import com.senac.projectmanagement.entity.UserRolesId;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, UserRolesId> {

    List<UserRoles> findByUser_UserId(Long userId);

    List<UserRoles> findByRole_RoleId(Integer roleId);

    Optional<UserRoles> findByUser_UserIdAndRole_RoleId(Long userId, Integer roleId);
}
