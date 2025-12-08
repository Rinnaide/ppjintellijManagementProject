package com.senac.projectmanagement.dto;

public class RoleRequestDTO {

    private Integer roleId;
    private String roleName;

    // Constructors, getters, setters

    public RoleRequestDTO() {}

    public RoleRequestDTO(Integer roleId, String roleName) {
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
