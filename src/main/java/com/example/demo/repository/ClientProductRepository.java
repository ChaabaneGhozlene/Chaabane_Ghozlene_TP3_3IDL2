package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entites.ClientProduct;

public interface ClientProductRepository extends JpaRepository<ClientProduct, Long> {
    List<ClientProduct> findByClientId(Long clientId);
    ClientProduct findByClientIdAndProductId(Long clientId, Long productId);
    
}