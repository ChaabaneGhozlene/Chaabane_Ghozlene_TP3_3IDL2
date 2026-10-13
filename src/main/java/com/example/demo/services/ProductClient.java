package com.example.demo.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.demo.entites.Product;  // ✅ Cet import devrait maintenant fonctionner

@FeignClient(name = "product-service", url = "http://localhost:8080")
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    Product getProductById(@PathVariable("id") Long id,        @RequestHeader("X-Country") String country  // ✅ Ajoutez ce header
);
    
}