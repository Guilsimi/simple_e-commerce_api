package com.example.ec_product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_product.entities.Product;
import com.example.ec_product.repositories.ProductRepository;
import com.example.ec_product.service.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void updateQuantity(Long id, Integer quantity) {
        Product product = findById(id);
        Integer updatedQuantity = product.getQuantity() - quantity;
        product.setQuantity(updatedQuantity);
        update(product);
    }

    public void update(Product product) {
        Product newProduct = findById(product.getId());
        updateData(newProduct, product);
        productRepository.save(newProduct);
    }

    private void updateData(Product newProduct, Product product) {
        newProduct.setName(
                product.getName() != null ? product.getName() : newProduct.getName());
        newProduct.setCategory(
                product.getCategory() != null ? product.getCategory() : newProduct.getCategory());
        newProduct.setDescription(
                product.getDescription() != null ? product.getDescription() : newProduct.getDescription());
        newProduct.setPrice(
                product.getPrice() != null ? product.getPrice() : newProduct.getPrice());
        newProduct.setQuantity(
                product.getQuantity() != null ? product.getQuantity() : newProduct.getQuantity());
        newProduct.setImageUrl(
                product.getImageUrl() != null ? product.getImageUrl() : newProduct.getImageUrl());
    }

}
