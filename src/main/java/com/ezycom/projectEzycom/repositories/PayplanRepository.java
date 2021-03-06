package com.ezycom.projectEzycom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ezycom.projectEzycom.entities.Payplan;

@Repository
public interface PayplanRepository extends JpaRepository<Payplan, Long> {
}