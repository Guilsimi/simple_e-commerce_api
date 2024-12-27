package com.example.ec_product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_product.entities.Product;
import com.example.ec_product.repositories.ProductRepository;
import com.example.ec_product.service.exceptions.ObjectNotUpdatedException;
import com.example.ec_product.service.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
    }

    public List<Product> findAll() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Ocorreu um erro ao buscar os produtos.");
        }
    }

    public void updateQuantity(Long id, Integer quantity) {
        try {
            Product product = findById(id);
            Integer updatedQuantity = product.getQuantity() - quantity;
            product.setQuantity(updatedQuantity);
            update(product);
        } catch (Exception e) {
            throw new ObjectNotUpdatedException("Erro ao atualizar o produto");
        }

    }

    public void update(Product product) {
        try {
            Product newProduct = findById(product.getId());
            updateData(newProduct, product);
            productRepository.save(newProduct);
        } catch (Exception e) {
            throw new ObjectNotUpdatedException("Erro ao atualizar o produto");
        }
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
