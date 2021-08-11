package com.henrysican.rentaspot.services;

import com.henrysican.rentaspot.dao.AddressRepo;
import com.henrysican.rentaspot.models.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AddressService {
    private final AddressRepo addressRepo;

    @Autowired
    public AddressService(AddressRepo locationRepo){
        this.addressRepo = locationRepo;
    }

    public List<Address> getAddresses(){
        return addressRepo.findAll();
    }
}