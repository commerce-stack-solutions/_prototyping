package io.commercestacksolutions.priceproviderservice;

import io.commercestacksolutions.priceproviderservice.dataaccess.address.entity.AddressEntity;
import io.commercestacksolutions.priceproviderservice.dataaccess.address.repository.AddressRepository;
import io.commercestacksolutions.priceproviderservice.dataaccess.organization.entity.OrganizationEntity;
import io.commercestacksolutions.priceproviderservice.dataaccess.organization.repository.OrganizationRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Order(2)
public class ExtSampleDataLoader implements ApplicationRunner {

    private final AddressRepository addressRepository;
    private final OrganizationRepository organizationRepository;

    public ExtSampleDataLoader(AddressRepository addressRepository,
                               OrganizationRepository organizationRepository) {
        this.addressRepository = addressRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (addressRepository.count() > 0) {
            return;
        }

        Optional<OrganizationEntity> acme = organizationRepository.findByPath("ORG-ACME");
        Optional<OrganizationEntity> globex = organizationRepository.findByPath("ORG-GLOBEX");
        Optional<OrganizationEntity> initech = organizationRepository.findByPath("ORG-INITECH");

        AddressEntity hq = new AddressEntity("123 Main Street", "Springfield", "12345", "US");
        acme.ifPresent(org -> hq.getOrganizations().add(org));
        globex.ifPresent(org -> hq.getOrganizations().add(org));
        addressRepository.save(hq);

        AddressEntity branch = new AddressEntity("456 Industrial Ave", "Shelbyville", "67890", "US");
        acme.ifPresent(org -> branch.getOrganizations().add(org));
        addressRepository.save(branch);

        AddressEntity overseas = new AddressEntity("1 Tech Park", "Singapore", "018989", "SG");
        initech.ifPresent(org -> overseas.getOrganizations().add(org));
        addressRepository.save(overseas);
    }
}
