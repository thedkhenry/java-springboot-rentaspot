package com.henrysican.rentaspot.services;

import com.henrysican.rentaspot.dao.ImageRepo;
import com.henrysican.rentaspot.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ImageService {
    private final ImageRepo imageRepo;

    @Autowired
    public ImageService(ImageRepo imageRepo) {
        this.imageRepo = imageRepo;
    }

    public Image saveImage(Image image){
        return imageRepo.save(image);
    }
}