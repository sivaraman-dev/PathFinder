package com.pathfinder.pathfinder_backend.repository;

import com.pathfinder.pathfinder_backend.model.Career;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerRepository extends JpaRepository<Career, Long> {
}
