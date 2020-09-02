package com.project.covid19.repository;

import com.project.covid19.entity.AreaCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaCountRepository extends JpaRepository<AreaCount, Long> {
    public AreaCount findByAreaName(String areaName);
    void deleteByAreaName(String areaName);
}
