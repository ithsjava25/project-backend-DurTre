package org.example.crimearchive.permissions;

import org.example.crimearchive.bevis.Cases;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends ListCrudRepository<Cases, String> {

    List<String> findAllBy(Long accoundId);

}
