package com.rcg.repository;

import com.rcg.entity.BusinessUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessUserRepository extends JpaRepository<BusinessUser, String> {
}
