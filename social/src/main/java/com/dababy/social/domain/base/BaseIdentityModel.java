package com.dababy.social.domain.base;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseIdentityModel<T extends Number> extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private T id;

    public T getId() {
        return id;
    }

    protected void setId(T id) {
        this.id = id;
    }
}
