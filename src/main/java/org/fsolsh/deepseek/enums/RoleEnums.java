package org.fsolsh.deepseek.enums;

import lombok.AllArgsConstructor;

/**
 * @author fsolsh
 * @date 2025-02-27
 */
@AllArgsConstructor
public enum RoleEnums {
    SYSTEM("system", "系统"),
    USER("user", "用户"),
    ASSISTANT("assistant", "助手");
    public final String roleType;
    public final String roleDesc;

    public static RoleEnums fromType(String roleType) {
        for (RoleEnums roleEnums : RoleEnums.values()) {
            if (roleEnums.roleType.equals(roleType)) {
                return roleEnums;
            }
        }
        return null;
    }

}
