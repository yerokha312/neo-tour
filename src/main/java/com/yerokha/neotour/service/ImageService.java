package com.yerokha.neotour.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.yerokha.neotour.entity.Image;
import com.yerokha.neotour.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final Cloudinary cloudinary;

    public ImageService(ImageRepository imageRepository, Cloudinary cloudinary) {
        this.imageRepository = imageRepository;
        this.cloudinary = cloudinary;
    }

    public Image processImage(MultipartFile image) {
        return saveImage(image);
    }

    private Image saveImage(MultipartFile file) {
        String imageUrl = uploadImage(file);
        Image image = new Image();
        image.setImageUrl(imageUrl);
        image.setImageName(file.getOriginalFilename());
        return imageRepository.save(image);
    }

    private String uploadImage(MultipartFile image) {
        try {
            return cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

