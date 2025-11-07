package com.senac.projectmanagement.dto;

public class RoleResponseDTO {

    private Integer roleId;
    private String roleName;

    // Constructors, getters, setters

    public RoleResponseDTO() {}

    public RoleResponseDTO(Integer roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
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
