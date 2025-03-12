package org.fsolsh.deepseek.enums;

import lombok.AllArgsConstructor;

/**
 * @author fsolsh
 * @date 2025-02-27
 */
@AllArgsConstructor
public enum ToolTypeEnums {
    FUNCTION("function", "function");
    public final String toolType;
    public final String toolDesc;

    public static ToolTypeEnums fromType(String toolType) {
        for (ToolTypeEnums toolTypeEnums : ToolTypeEnums.values()) {
            if (toolTypeEnums.toolType.equals(toolType)) {
                return toolTypeEnums;
            }
        }
        return null;
    }

}
