package org.fsolsh.deepseek.enums;

import lombok.AllArgsConstructor;

/**
 * @author fsolsh
 * @date 2025-02-27
 */
@AllArgsConstructor
public enum ModelEnums {
    DEEPSEEK_CHAT("deepseek-chat", "deepseek-chat"),
    DEEPSEEK_REASONER("deepseek-reasoner", "deepseek-reasoner");
    public final String modelType;
    public final String modelDesc;

    public static ModelEnums fromType(String modelType) {
        for (ModelEnums modelEnums : ModelEnums.values()) {
            if (modelEnums.modelType.equals(modelType)) {
                return modelEnums;
            }
        }
        return null;
    }

}
