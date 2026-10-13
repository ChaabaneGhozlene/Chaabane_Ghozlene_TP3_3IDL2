package com.example.demo.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.entites.Client;
import com.example.demo.entites.ClientProduct;
import com.example.demo.entites.Product;
import com.example.demo.repository.ClientProductRepository;
import com.example.demo.repository.ClientRepository;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientProductRepository clientProductRepository;
    private final ProductClient productClient;
     public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public ClientService(ClientRepository clientRepository,
                         ClientProductRepository clientProductRepository,
                         ProductClient productClient) {
        this.clientRepository = clientRepository;
        this.clientProductRepository = clientProductRepository;
        this.productClient = productClient;
    }

    public Client getClient(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public List<ClientProduct> getClientProducts(Long clientId) {
        return clientProductRepository.findByClientId(clientId);
    }

    public String addProductToClient(Long clientId, Long productId, Integer quantity) {
    System.out.println("=== DÉBUT addProductToClient ===");
    System.out.println("Client ID: " + clientId + ", Product ID: " + productId + ", Quantity: " + quantity);
    
    try {
        // 1. Vérifier si le client existe
        Client client = clientRepository.findById(clientId).orElse(null);
        System.out.println("Client trouvé: " + (client != null ? client.getName() : "NULL"));
        
        if (client == null) {
            return "Client introuvable avec ID: " + clientId;
        }

        // 2. Appeler ProductService via Feign
        System.out.println("Appel de ProductClient.getProductById(" + productId + ")...");
        Product product = productClient.getProductById(productId, "FR");
        System.out.println("Produit reçu: " + (product != null ? product.getName() : "NULL"));
        
        if (product == null) {
            return "Produit introuvable dans ProductService. ID: " + productId;
        }

        // 3. VÉRIFIER SI LE PRODUIT EXISTE DÉJÀ POUR CE CLIENT
        ClientProduct existingProduct = clientProductRepository
            .findByClientIdAndProductId(clientId, productId);
        
        if (existingProduct != null) {
            // ✅ PRODUIT EXISTANT : METTRE À JOUR LA QUANTITÉ
            System.out.println("Produit existe déjà. Mise à jour de la quantité...");
            int newQuantity = existingProduct.getQuantity() + quantity;
            existingProduct.setQuantity(newQuantity);
            clientProductRepository.save(existingProduct);
            
            System.out.println("=== SUCCÈS (QUANTITÉ MISE À JOUR) ===");
            return "Quantité mise à jour pour le produit '" + product.getName() + 
                   "'. Nouvelle quantité: " + newQuantity;
        } else {
            // ✅ NOUVEAU PRODUIT : CRÉER UNE NOUVELLE ENTRÉE
            System.out.println("Création d'un nouveau ClientProduct...");
            ClientProduct clientProduct = new ClientProduct();
            clientProduct.setClientId(clientId);
            clientProduct.setProductId(product.getId());
            clientProduct.setProductName(product.getName());
            clientProduct.setQuantity(quantity);

            System.out.println("Sauvegarde dans la base...");
            clientProductRepository.save(clientProduct);
            
            System.out.println("=== SUCCÈS (NOUVEAU PRODUIT) ===");
            return "Produit '" + product.getName() + "' ajouté avec succès au client " + client.getName();
        }

    } catch (Exception e) {
        System.out.println("=== ERREUR ===");
        System.out.println("Type d'erreur: " + e.getClass().getName());
        System.out.println("Message: " + e.getMessage());
        e.printStackTrace();
        return "Erreur interne: " + e.getMessage();
    }

}
 public String dinmnuerProductToClient(Long clientId, Long productId, Integer quantity) {
    System.out.println("=== DÉBUT addProductToClient ===");
    System.out.println("Client ID: " + clientId + ", Product ID: " + productId + ", Quantity: " + quantity);
    
    try {
        // 1. Vérifier si le client existe
        Client client = clientRepository.findById(clientId).orElse(null);
        System.out.println("Client trouvé: " + (client != null ? client.getName() : "NULL"));
        
        if (client == null) {
            return "Client introuvable avec ID: " + clientId;
        }

        // 2. Appeler ProductService via Feign
        System.out.println("Appel de ProductClient.getProductById(" + productId + ")...");
        Product product = productClient.getProductById(productId, "FR");
        System.out.println("Produit reçu: " + (product != null ? product.getName() : "NULL"));
        
        if (product == null) {
            return "Produit introuvable dans ProductService. ID: " + productId;
        }

        // 3. VÉRIFIER SI LE PRODUIT EXISTE DÉJÀ POUR CE CLIENT
        ClientProduct existingProduct = clientProductRepository
            .findByClientIdAndProductId(clientId, productId);
        
        if (existingProduct != null) {
            // ✅ PRODUIT EXISTANT : METTRE À JOUR LA QUANTITÉ
            System.out.println("Produit existe déjà. Mise à jour de la quantité...");
            int newQuantity = existingProduct.getQuantity() - quantity;
            existingProduct.setQuantity(newQuantity);
            clientProductRepository.save(existingProduct);
            
            System.out.println("=== SUCCÈS (QUANTITÉ MISE À JOUR) ===");
            return "Quantité mise à jour pour le produit '" + product.getName() + 
                   "'. Nouvelle quantité: " + newQuantity;
        } else {
            // ✅ NOUVEAU PRODUIT : CRÉER UNE NOUVELLE ENTRÉE
            System.out.println("Création d'un nouveau ClientProduct...");
            ClientProduct clientProduct = new ClientProduct();
            clientProduct.setClientId(clientId);
            clientProduct.setProductId(product.getId());
            clientProduct.setProductName(product.getName());
            clientProduct.setQuantity(quantity);

            System.out.println("Sauvegarde dans la base...");
            clientProductRepository.save(clientProduct);
            
            System.out.println("=== SUCCÈS (NOUVEAU PRODUIT) ===");
            return "Produit '" + product.getName() + "' ajouté avec succès au client " + client.getName();
        }

    } catch (Exception e) {
        System.out.println("=== ERREUR ===");
        System.out.println("Type d'erreur: " + e.getClass().getName());
        System.out.println("Message: " + e.getMessage());
        e.printStackTrace();
        return "Erreur interne: " + e.getMessage();
    }
}
public Client updateClient(Long id, Client updatedClient) {
    Client existingClient = clientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Client not found"));

    // Exemple : mets à jour les champs que contient ton entité
    existingClient.setName(updatedClient.getName());
    existingClient.setEmail(updatedClient.getEmail());
    existingClient.setAddress(updatedClient.getAddress());

    return clientRepository.save(existingClient);
}
public void deleteClient(Long id) {
    Client client = clientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Client introuvable"));

    // Supprimer les relations Client-Product
    List<ClientProduct> products = clientProductRepository.findByClientId(id);
    clientProductRepository.deleteAll(products);

    // Supprimer le client
    clientRepository.delete(client);
}

}