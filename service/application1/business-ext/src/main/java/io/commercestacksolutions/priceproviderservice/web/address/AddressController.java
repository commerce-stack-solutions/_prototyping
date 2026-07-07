package io.commercestacksolutions.priceproviderservice.web.address;

import io.commercestacksolutions.priceproviderservice.dataaccess.address.entity.AddressEntity;
import io.commercestacksolutions.priceproviderservice.dataaccess.address.repository.AddressRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressRepository addressRepository;

    public AddressController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @GetMapping
    public List<AddressEntity> list() {
        return addressRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressEntity> detail(@PathVariable String id) {
        return addressRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
