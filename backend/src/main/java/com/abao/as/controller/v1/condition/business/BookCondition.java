package com.abao.as.controller.v1.condition.business;

import com.abao.as.controller.v1.condition.common.BaseCondition;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookCondition extends BaseCondition {
    private String name;

    private Long positionId;

    private boolean gender;

    private Long departmentId;

    public String getName() {
        if (this.name == null) {
            return "";
        }
        return this.name;
    }
}