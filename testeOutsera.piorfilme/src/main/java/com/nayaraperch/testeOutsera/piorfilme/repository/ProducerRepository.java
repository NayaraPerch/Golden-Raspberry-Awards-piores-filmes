package com.nayaraperch.testeOutsera.piorfilme.repository;

import com.nayaraperch.testeOutsera.piorfilme.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {
    Producer findByName(String name);
}
