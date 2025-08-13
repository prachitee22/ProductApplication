// ...existing code...
package com.productapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        // quantity is now used instead of description
        return productService.addProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        
        return productService.updateProduct(id, product);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return productService.searchProducts(name);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/{id}/buy")
    public ResponseEntity<String> buyProduct(@PathVariable Long id) {
        boolean success = productService.buyProduct(id);
        if (success) {
            return ResponseEntity.ok("Product purchased successfully.");
        } else {
            return ResponseEntity.badRequest().body("Product not available or out of stock.");
        }
    }
    
    @GetMapping("/purchases")
    public List<Purchase> getAllPurchases() {
        return productService.getAllPurchases();
    }
    
    @GetMapping("/purchases/user")
    public List<Purchase> getUserPurchases() {
        return productService.getUserPurchases();
    }
    
    @PostMapping("/{id}/cart")
    public ResponseEntity<String> addToCart(@PathVariable Long id) {
        productService.addToCart(id);
        return ResponseEntity.ok("Product added to cart");
    }
    
    @GetMapping("/cart")
    public List<Cart> getCartItems() {
        return productService.getCartItems();
    }
    
    @DeleteMapping("/cart/{id}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long id) {
        productService.removeFromCart(id);
        return ResponseEntity.ok("Product removed from cart");
    }
    
    @PutMapping("/cart/{id}/decrease")
    public ResponseEntity<String> decreaseCartQuantity(@PathVariable Long id) {
        productService.decreaseCartQuantity(id);
        return ResponseEntity.ok("Quantity decreased");
    }
}
