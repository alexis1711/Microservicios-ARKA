package com.arka.products.controller;

import com.arka.products.dto.ProductDto;
import com.arka.products.dto.ProductSaveDto;
import com.arka.products.entity.Product;
import com.arka.products.mapper.ProductMapper;
import com.arka.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final ProductMapper productMapper;

    @PostMapping
    public Mono<ProductSaveDto> saveProduct(@RequestBody ProductDto productDto){
        return productService.saveProduct(productMapper.toEntity(productDto))
                .map(productMapper::toDto)
                .map(saveProductDto -> {
                    ProductSaveDto response = new ProductSaveDto();
                    response.setMensaje("Producto creado con exito");
                    response.setProductDto(saveProductDto);
                    return response;
                }).onErrorResume(error -> {
                    // Log del error
                    System.out.println("Error al guardar producto: " + error.getMessage() + error);

                    // Construimos una respuesta con mensaje de error
                    ProductSaveDto errorResponse = new ProductSaveDto();
                    errorResponse.setMensaje("Error al guardar el producto: " + error.getMessage());
                    errorResponse.setProductDto(null);

                    // Retornamos un Mono con la respuesta de error
                    return Mono.just(errorResponse);
                });
    }

    @PostMapping(value = "/products", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> getProductByIds(@RequestBody List<String> ids){
        return productService.getProductsById(Flux.fromIterable(ids));
    }

    @GetMapping(value = "/products")
    public Flux<Product> getProduct(){
        return productService.getProducts();
    }

    @PutMapping
    public Mono<Product> putProduct(@RequestBody Product product){
        return productService.putProduct(product);
    }

    @PutMapping(value = "/inactive/{id}")
    public Mono<Product> inactiveProduct(@PathVariable String id){
        return productService.inactiveProduct(id);
    }

    @GetMapping(value = "/products/name/{name}")
    public Flux<Product> getProductByName(@PathVariable String name){
        return productService.getProductByName(name);
    }

    @GetMapping(value = "/products/category/{category}")
    public Flux<Product> getProductByCategory(@PathVariable String category){
        return productService.getProductByCategory(category);
    }

    @GetMapping(value = "/products/status/{status}")
    public Flux<Product> getProductByStatus(@PathVariable boolean status){
        return productService.getProductByStatus(status);
    }


}
