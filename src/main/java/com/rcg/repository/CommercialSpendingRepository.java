package com.rcg.repository;

import com.rcg.domain.entity.CommercialSpending;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommercialSpendingRepository extends JpaRepository<CommercialSpending, Long> {
}