package io.commercestacksolutions.commons.dataaccess.entity;

import java.time.OffsetDateTime;

public interface AuditableEntity {

    OffsetDateTime getCreatedAt();

    void setCreatedAt(OffsetDateTime createdAt);

    OffsetDateTime getLastModifiedAt();

    void setLastModifiedAt(OffsetDateTime lastModifiedAt);
}
