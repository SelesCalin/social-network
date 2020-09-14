package com.dababy.social.domain.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.sql.Timestamp;

@MappedSuperclass
public abstract class BaseModel {

    @Column(name = "created_at", nullable = false)
    private Timestamp createdTimestamp;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedTimestamp;

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    /**
     * This method allows to overwrite the creation time with a value you provided.
     * If the value is null, creation timestamp will be set to value provided by {
     * the @link #getLocalDateTimeForCreateUpdate()} method.
     *
     * @param createdTimestamp the value when this record was updated
     */
    protected void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Timestamp getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    protected void setUpdatedTimestamp(Timestamp updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }


    /**
     * Method invoked before this record is created in database.
     */
    @PrePersist
    private void onCreate() {
        if (createdTimestamp == null) {
            createdTimestamp = getTimeStampForCreateUpdate(true);
        }
        onUpdate();
    }

    /**
     * Method created before this record is updated in database.
     */
    @PreUpdate
    private void onUpdate() {
        updatedTimestamp = getTimeStampForCreateUpdate(false);
    }

    /**
     * Method called in order to retrieve the current timestamp value for create / update operations.
     * Default implementations just return the <code>Timestamp(System.currentTimeMillis())</code> value.
     * You may customize the time by overwriting this method (and set another timezone (for example).
     *
     * @param create flag indicating that create timestamp is requested
     * @return the local date time when record was allegedly be created / updated
     */
    protected Timestamp getTimeStampForCreateUpdate(boolean create) {
        return new Timestamp(System.currentTimeMillis());
    }
}
