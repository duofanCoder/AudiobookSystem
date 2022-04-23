package com.abao.as.controller.v1.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookRequest {
    private Long id;
    @NotNull(message = "{constraints.NotEmpty.message}")
    private String name;
    private String mobile;
    private boolean gender;
    @NotNull(message = "{constraints.NotEmpty.message}")
    private String username;

}