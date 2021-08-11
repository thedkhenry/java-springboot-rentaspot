package com.henrysican.rentaspot.dao;

import com.henrysican.rentaspot.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepo extends JpaRepository<Address,Integer> {
//    List<Address> findTop10ByIsActiveIsTrue();
//    List<Address> findTop10ByIsActiveIsTrueOrderByCreatedAtDesc();
}