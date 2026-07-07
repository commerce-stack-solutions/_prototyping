package io.commercestacksolutions.priceproviderservice;

import io.commercestacksolutions.priceproviderservice.dataaccess.group.entity.GroupEntity;
import io.commercestacksolutions.priceproviderservice.dataaccess.group.repository.GroupRepository;
import io.commercestacksolutions.priceproviderservice.dataaccess.organization.entity.OrganizationEntity;
import io.commercestacksolutions.priceproviderservice.dataaccess.organization.organizationtype.OrganizationTypeDefinition;
import io.commercestacksolutions.priceproviderservice.dataaccess.organization.repository.OrganizationRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class CoreSampleDataLoader implements ApplicationRunner {

    private final GroupRepository groupRepository;
    private final OrganizationRepository organizationRepository;

    public CoreSampleDataLoader(GroupRepository groupRepository,
                                OrganizationRepository organizationRepository) {
        this.groupRepository = groupRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (groupRepository.count() > 0) {
            return;
        }

        // Root groups
        GroupEntity emea = new GroupEntity("GRP-EMEA");
        emea.setName("EMEA Region");

        GroupEntity apac = new GroupEntity("GRP-APAC");
        apac.setName("APAC Region");

        groupRepository.save(emea);
        groupRepository.save(apac);

        // Organizations
        OrganizationEntity acme = new OrganizationEntity("ORG-ACME");
        acme.setName("Acme Corporation");
        acme.setOrganizationType(OrganizationTypeDefinition.CUSTOMER);
        acme.getParentRefs().add(emea);

        OrganizationEntity globex = new OrganizationEntity("ORG-GLOBEX");
        globex.setName("Globex Corp");
        globex.setOrganizationType(OrganizationTypeDefinition.SUPPLIER);
        globex.getParentRefs().add(emea);

        OrganizationEntity initech = new OrganizationEntity("ORG-INITECH");
        initech.setName("Initech");
        initech.setOrganizationType(OrganizationTypeDefinition.PARTNER);
        initech.getParentRefs().add(apac);

        OrganizationEntity umbrella = new OrganizationEntity("ORG-UMBRELLA");
        umbrella.setName("Umbrella Inc.");
        umbrella.setOrganizationType(OrganizationTypeDefinition.INTERNAL);

        organizationRepository.save(acme);
        organizationRepository.save(globex);
        organizationRepository.save(initech);
        organizationRepository.save(umbrella);
    }
}
