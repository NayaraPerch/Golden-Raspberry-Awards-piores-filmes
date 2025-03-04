package com.nayaraperch.testeOutsera.piorfilme.repository;

import com.nayaraperch.testeOutsera.piorfilme.entity.Execution;
import com.nayaraperch.testeOutsera.piorfilme.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionRepository extends JpaRepository<Execution, Long> {
    Execution findFirstByOrderByExecutionEndDesc();
}
