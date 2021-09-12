package com.henrysican.rentaspot.services;

import com.henrysican.rentaspot.dao.ImageRepo;
import com.henrysican.rentaspot.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ImageService {
    private final ImageRepo imageRepo;

    @Autowired
    public ImageService(ImageRepo imageRepo) {
        this.imageRepo = imageRepo;
    }

    /**
     * Creates a new entry in the database table with the image provided and returns it.
     * @param image the image to be saved
     * @return      the saved image
     */
    public Image saveImage(Image image){
        return imageRepo.save(image);
    }

    /**
     * Returns a List of all Images in the database.
     * @return  the list of images
     */
    public List<Image> getAllImages(){
        return  imageRepo.findAll();
    }
}