package com.dailycodework.dreamshop.service.product;

import com.dailycodework.dreamshop.model.Product;
import com.dailycodework.dreamshop.request.AddProductRequest;
import com.dailycodework.dreamshop.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest request);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long productId);

    Product getProductById(Long id);
    List<Product> getProductByName(String name);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByBrandAndName(String catetory, String name);

    Long countProductsByBrandAndName(String brand, String name);
}
