package org.example.repository;

import org.example.entity.AuditoriaAcceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriaAccesoRepository extends JpaRepository<AuditoriaAcceso, Long> {
}