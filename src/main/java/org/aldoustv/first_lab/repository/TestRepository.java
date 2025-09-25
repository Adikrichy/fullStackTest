package org.aldoustv.first_lab.repository;

import org.aldoustv.first_lab.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findByName(String name);

}
