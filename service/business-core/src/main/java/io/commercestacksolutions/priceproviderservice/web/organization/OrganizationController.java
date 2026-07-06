package io.commercestacksolutions.priceproviderservice.web.organization;

import io.commercestacksolutions.priceproviderservice.dataaccess.organization.entity.OrganizationEntity;
import io.commercestacksolutions.priceproviderservice.dataaccess.organization.repository.OrganizationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    private final OrganizationRepository organizationRepository;

    public OrganizationController(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @GetMapping
    public List<OrganizationEntity> list() {
        return organizationRepository.findAll();
    }

    @GetMapping("/{path}")
    public ResponseEntity<OrganizationEntity> detail(@PathVariable String path) {
        return organizationRepository.findByPath(path)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
