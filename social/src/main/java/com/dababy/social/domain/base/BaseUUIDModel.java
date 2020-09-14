package com.dababy.social.domain.base;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.util.UUID;

@MappedSuperclass
public class BaseUUIDModel extends BaseModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    public UUID getUuid() {
        return id;
    }

    protected void setUuid(UUID uuid) {
        this.id = uuid;
    }

    @PrePersist
    protected void generateUuidValue() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
}
