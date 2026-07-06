package io.commercestacksolutions.priceproviderservice.dataaccess.organization.organizationtype;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Provides the valid {@link OrganizationType} values for use with
 * {@code @MetaDynamicEnum(beanType = OrganizationTypeDefinition.class)}.
 */
@Component
public class OrganizationTypeDefinition {

    public static final OrganizationType CUSTOMER = new OrganizationType("CUSTOMER");
    public static final OrganizationType SUPPLIER = new OrganizationType("SUPPLIER");
    public static final OrganizationType PARTNER = new OrganizationType("PARTNER");
    public static final OrganizationType INTERNAL = new OrganizationType("INTERNAL");

    public List<OrganizationType> getValues() {
        return List.of(CUSTOMER, SUPPLIER, PARTNER, INTERNAL);
    }
}
