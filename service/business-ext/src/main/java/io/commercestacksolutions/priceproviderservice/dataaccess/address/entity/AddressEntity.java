package io.commercestacksolutions.priceproviderservice.dataaccess.address.entity;

import io.commercestacksolutions.commons.dataaccess.idgenerator.IdGeneratorProvider;
import io.commercestacksolutions.priceproviderservice.dataaccess.organization.entity.OrganizationEntity;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class AddressEntity {

    @Id
    @Column(length = 100)
    private String id;

    private String street;

    private String city;

    private String postalCode;

    private String country;

    @ManyToMany
    @JoinTable(
            name = "address_organization",
            joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id")
    )
    private Set<OrganizationEntity> organizations = new HashSet<>();

    public AddressEntity() {
    }

    public AddressEntity(String street, String city, String postalCode, String country) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    @PrePersist
    protected void prePersist() {
        if (this.id == null) {
            this.id = IdGeneratorProvider.generate(AddressEntity.class);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<OrganizationEntity> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Set<OrganizationEntity> organizations) {
        this.organizations = organizations;
    }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "id='" + id + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
