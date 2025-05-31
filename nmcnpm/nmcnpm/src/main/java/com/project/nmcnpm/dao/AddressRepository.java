package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByUserUserId(Integer userId);
    Address findByUserUserIdAndIsDefaultTrue(Integer userId);
}