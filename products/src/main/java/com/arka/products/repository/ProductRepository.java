package com.arka.products.repository;

import com.arka.products.entity.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    Flux<Product> findAllById(Flux<String> ids);
    Flux<Product> findAllByName(String name);
    Flux<Product> findAllByCategory(String category);
    Flux<Product> findAllByStatus(boolean status);

}
