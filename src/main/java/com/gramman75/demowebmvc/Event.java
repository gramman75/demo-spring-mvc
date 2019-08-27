package com.gramman75.demowebmvc;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
public class Event {
    private Integer id;

    interface ValidateName {}
    interface ValidateLimit {}

    @NotBlank(message = "이름은 필수입니다.", groups = ValidateName.class )
    private String name;



    @Min(value = 1, groups = ValidateLimit.class, message = "최소 1명 이상신청해야 합니다.")
    private Integer limit;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
