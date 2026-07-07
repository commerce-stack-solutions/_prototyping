package io.commercestacksolutions.priceproviderservice.dataaccess.group.repository;

import io.commercestacksolutions.priceproviderservice.dataaccess.group.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupEntity, String> {

    Optional<GroupEntity> findByPath(String path);
}
