package com.project.covid19.repository;

import com.project.covid19.entity.Marker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional()
public interface MarkerRepository extends JpaRepository<Marker, Long> {
    long countByAddrDepTwo(String value);
    void deleteByAddrDepTwo(String value);
    List<Marker> findByAddrDepTwo(String value);
}
