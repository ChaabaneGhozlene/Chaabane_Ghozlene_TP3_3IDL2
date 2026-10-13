package com.example.demo.Controleurs;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entites.Client;
import com.example.demo.entites.ClientProduct;
import com.example.demo.services.ClientService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/clients")

public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable Long id) {
        return clientService.getClient(id);
    }

    @GetMapping("/{id}/products")
    public List<ClientProduct> getClientProducts(@PathVariable Long id) {
        return clientService.getClientProducts(id);
    }

    @PostMapping("/{clientId}/addProduct/{productId}")
    public String addProductToClient(@PathVariable Long clientId,
                                     @PathVariable Long productId,
                                     @RequestParam Integer quantity) {
        return clientService.addProductToClient(clientId, productId, quantity);
    }
    @PostMapping("/{clientId}/dinmuerProduct/{productId}")
    public String dinmnuerProductToClient(@PathVariable Long clientId,
                                     @PathVariable Long productId,
                                     @RequestParam Integer quantity) {
        return clientService.dinmnuerProductToClient(clientId, productId, quantity);
    }
    @PostMapping
public ResponseEntity<Client> createClient(@RequestBody Client client) {
    Client createdClient = clientService.createClient(client);
    return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
}
@PutMapping("/{id}")
public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
    Client client = clientService.updateClient(id, updatedClient);
    return new ResponseEntity<>(client, HttpStatus.OK);
}
@DeleteMapping("/{id}")
public ResponseEntity<String> deleteClient(@PathVariable Long id) {
    clientService.deleteClient(id);
    return new ResponseEntity<>("Client supprimé avec succès", HttpStatus.OK);
}

    
}
