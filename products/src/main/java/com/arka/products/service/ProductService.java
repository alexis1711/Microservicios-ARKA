package com.arka.products.service;

import com.arka.products.dto.ProductDto;
import com.arka.products.entity.Product;
import com.arka.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Mono<Product> saveProduct(Product product){
        System.out.println("llego 2 y llevo" + product.getDescription());
        return productRepository.save(product);
    }

    public Flux<Product> getProductsById(Flux<String> ids){
        return productRepository.findAllById(ids);
    }

    public Flux<Product> getProducts(){
        return productRepository.findAll();
    }

    public Mono<Product> putProduct(Product product) {
        return productRepository.findById(product.getId())
                .flatMap(existing -> {
                    existing.setDescription(product.getDescription());
                    existing.setName(product.getName());
                    existing.setCategory(product.getCategory());
                    existing.setStatus(true);
                    return productRepository.save(existing); // aquí se edita
                });
    }

    public Mono<Product> inactiveProduct(String id){
        return productRepository.findById(id)
                .flatMap(existing -> {
                    existing.setStatus(false);
                    return productRepository.save(existing); // aquí se edita
                });
    }

    public Flux<Product> getProductByName(String name){
        return productRepository.findAllByName(name);
    }

    public Flux<Product> getProductByCategory(String category){
        return productRepository.findAllByCategory(category);
    }

    public Flux<Product> getProductByStatus(boolean status){
        return productRepository.findAllByStatus(status);
    }

}
