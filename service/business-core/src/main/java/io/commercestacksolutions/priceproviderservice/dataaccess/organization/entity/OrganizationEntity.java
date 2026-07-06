package io.commercestacksolutions.priceproviderservice.dataaccess.organization.entity;

import io.commercestacksolutions.commons.dataaccess.meta.MandatoryField;
import io.commercestacksolutions.commons.dataaccess.meta.MetaDynamicEnum;
import io.commercestacksolutions.priceproviderservice.dataaccess.group.entity.GroupEntity;
import io.commercestacksolutions.priceproviderservice.dataaccess.organization.organizationtype.OrganizationType;
import io.commercestacksolutions.priceproviderservice.dataaccess.organization.organizationtype.OrganizationTypeDefinition;
import io.commercestacksolutions.priceproviderservice.dataaccess.organization.organizationtype.converter.OrganizationTypeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;

@Entity
public class OrganizationEntity extends GroupEntity {

    @Convert(converter = OrganizationTypeConverter.class)
    @MetaDynamicEnum(beanType = OrganizationTypeDefinition.class)
    @MandatoryField
    private OrganizationType organizationType;

    public OrganizationEntity() {
        super();
    }

    public OrganizationEntity(String path) {
        super(path);
    }

    public OrganizationType getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(OrganizationType organizationType) {
        this.organizationType = organizationType;
    }

    @Override
    public String toString() {
        return "OrganizationEntity{" +
                "id=" + getId() +
                ", path='" + getPath() + '\'' +
                ", name='" + getName() + '\'' +
                ", organizationType=" + organizationType +
                ", createdAt=" + getCreatedAt() +
                ", lastModifiedAt=" + getLastModifiedAt() +
                '}';
    }
}
