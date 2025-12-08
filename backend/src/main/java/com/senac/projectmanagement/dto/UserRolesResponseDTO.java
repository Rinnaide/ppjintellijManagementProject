package com.senac.projectmanagement.dto;

public class UserRolesResponseDTO {

    private Long userId;
    private String userEmail;
    private Integer roleId;
    private String roleName;

    // Constructors, getters, setters

    public UserRolesResponseDTO() {}

    public UserRolesResponseDTO(Long userId, String userEmail, Integer roleId, String roleName) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
