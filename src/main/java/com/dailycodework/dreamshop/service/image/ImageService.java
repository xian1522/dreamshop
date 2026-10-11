package com.dailycodework.dreamshop.service.image;

import com.dailycodework.dreamshop.dto.ImageDto;
import com.dailycodework.dreamshop.exception.ResourceNotFoundException;
import com.dailycodework.dreamshop.model.Image;
import com.dailycodework.dreamshop.model.Product;
import com.dailycodework.dreamshop.repository.ImageRepository;
import com.dailycodework.dreamshop.repository.ProductRepository;
import com.dailycodework.dreamshop.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{
    private final ImageRepository imageRepository;
    private final ProductService productService;
    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No image found with id:" + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id)
                .ifPresentOrElse(imageRepository::delete,
                        ()-> new ResourceNotFoundException("image not found"));
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();
        for(MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);
                String downloadUrl = "/api/v1/images/download/" + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image saveImage = imageRepository.save(image);
                saveImage.setDownloadUrl("/api/v1/images/download/" + saveImage.getId());
                imageRepository.save(saveImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(saveImage.getId());
                imageDto.setImageName(saveImage.getFileName());
                imageDto.setDownloadUrl(saveImage.getDownloadUrl());
                savedImageDto.add(imageDto);

            }catch (SQLException | IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        image.setFileName(file.getOriginalFilename());
        try {
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
