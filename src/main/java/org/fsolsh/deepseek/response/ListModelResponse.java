package org.fsolsh.deepseek.response;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author fsolsh
 * @date 2025-02-27
 */
@Data
public class ListModelResponse {
    // list
    private String object;

    private List<Model> data;


    @Data
    public class Model {
        private String id;
        private String object;
        @JSONField(name = "owned_by")
        private String ownedBy;
    }

}
