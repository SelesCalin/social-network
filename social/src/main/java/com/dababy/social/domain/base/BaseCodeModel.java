package com.dababy.social.domain.base;

import javax.persistence.Id;

public class BaseCodeModel extends BaseModel {
    @Id
    private String code;

    public String getCode() {
        return code;
    }

    protected void setCode(String code) {
        this.code = code;
    }

}
