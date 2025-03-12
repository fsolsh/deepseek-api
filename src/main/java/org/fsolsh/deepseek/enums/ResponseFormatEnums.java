package org.fsolsh.deepseek.enums;

import lombok.AllArgsConstructor;

/**
 * @author fsolsh
 * @date 2025-02-27
 */
@AllArgsConstructor
public enum ResponseFormatEnums {
    TEXT("text", "text"),
    JSON_OBJECT("json_object", "json");
    public final String respType;
    public final String respTypeDesc;

    public static ResponseFormatEnums fromType(String respType) {
        for (ResponseFormatEnums responseFormatEnums : ResponseFormatEnums.values()) {
            if (responseFormatEnums.respType.equals(respType)) {
                return responseFormatEnums;
            }
        }
        return null;
    }

}
