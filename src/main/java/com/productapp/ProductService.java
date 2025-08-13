// ...existing code...
package com.productapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private CartRepository cartRepository;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, Product product) {
        product.setId(id);
        return productRepository.save(product);
    }

    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public boolean buyProduct(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            
            // Check if buying from cart
            Optional<Cart> cartItem = cartRepository.findByUsernameAndProductId(username, id);
            int buyQuantity = cartItem.map(Cart::getQuantity).orElse(1);
            
            if (product.getQuantity() >= buyQuantity) {
                product.setQuantity(product.getQuantity() - buyQuantity);
                productRepository.save(product);
                
                // Track purchase with quantity
                Purchase purchase = new Purchase(product.getId(), product.getName(), username, product.getPrice(), buyQuantity);
                purchaseRepository.save(purchase);
                
                // Remove from cart if it was in cart
                cartItem.ifPresent(cart -> cartRepository.delete(cart));
                
                return true;
            }
        }
        return false;
    }
    
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }
    
    public List<Purchase> getUserPurchases() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return purchaseRepository.findByUsername(username);
    }
    
    public void addToCart(Long productId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            Optional<Cart> existingCart = cartRepository.findByUsernameAndProductId(username, productId);
            if (existingCart.isPresent()) {
                Cart cart = existingCart.get();
                cart.setQuantity(cart.getQuantity() + 1);
                cartRepository.save(cart);
            } else {
                Cart cart = new Cart(username, productId, product.getName(), product.getPrice(), 1);
                cartRepository.save(cart);
            }
        }
    }
    
    public List<Cart> getCartItems() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return cartRepository.findByUsername(username);
    }
    
    @Transactional
    public void removeFromCart(Long productId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        cartRepository.deleteByUsernameAndProductId(username, productId);
    }
    
    public void decreaseCartQuantity(Long productId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Cart> cartOpt = cartRepository.findByUsernameAndProductId(username, productId);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            if (cart.getQuantity() > 1) {
                cart.setQuantity(cart.getQuantity() - 1);
                cartRepository.save(cart);
            } else {
                cartRepository.delete(cart);
            }
        }
    }
}
