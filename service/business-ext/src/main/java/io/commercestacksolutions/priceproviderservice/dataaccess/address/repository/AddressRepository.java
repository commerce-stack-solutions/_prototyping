package io.commercestacksolutions.priceproviderservice.dataaccess.address.repository;

import io.commercestacksolutions.priceproviderservice.dataaccess.address.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<AddressEntity, String> {

    List<AddressEntity> findByCity(String city);

    List<AddressEntity> findByCountry(String country);
}
