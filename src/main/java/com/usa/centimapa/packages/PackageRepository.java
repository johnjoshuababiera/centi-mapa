package com.usa.centimapa.packages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PackageRepository extends CrudRepository<Package,Long> {
    Package findByName(String name);
}
