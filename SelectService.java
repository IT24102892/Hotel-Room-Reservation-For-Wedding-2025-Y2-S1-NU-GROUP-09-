package com.example.demo.services;

import com.example.demo.model.PackageItem;
import com.example.demo.repositories.SelectRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class SelectService {

    private final SelectRepository repository;

    // NOTE: This in-memory cart is app-wide. For true per-session carts, move this
    // to a @SessionScope bean and inject it. Keeping as-is to match earlier setup.
    private final Cart cart = new Cart();

    public SelectService(SelectRepository repository) {
        this.repository = repository;
    }

    /* ------------------- Catalog ------------------- */

    public List<PackageItem> listAvailable() {
        List<PackageItem> items = repository.findByActiveTrueOrderByNameAsc();
        if (items == null || items.isEmpty()) {
            items = repository.findAll();
        }
        return items;
    }

    public PackageItem saveItem(PackageItem item) {
        // Ensure defaults as needed
        if (item.getPrice() == null) {
            item.setPrice(BigDecimal.ZERO);
        }
        if (item.getName() == null) {
            item.setName("");
        }
        return repository.save(item);
    }

    public Optional<PackageItem> findItem(Long id) {
        return repository.findById(id);
    }

    public void deleteItem(Long id) {
        repository.deleteById(id);
    }

    /* ------------------- Cart ------------------- */

    public void addToCart(Long itemId, int qty) {
        PackageItem item = repository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Item not found: " + itemId));
        cart.add(item, qty);
    }

    public void updateQty(Long itemId, int qty) {
        cart.update(itemId, qty);
    }

    public void removeFromCart(Long itemId) {
        cart.remove(itemId);
    }

    public void clearCart() {
        cart.clear();
    }

    public List<CartItem> getCartItems() {
        return cart.items();
    }

    public BigDecimal getTotal() {
        return cart.total();
    }

    /* ------------------- In-memory Cart helper ------------------- */

    static class Cart {
        private final LinkedHashMap<Long, CartItem> map = new LinkedHashMap<>();

        void add(PackageItem item, int qty) {
            if (qty < 1) qty = 1;
            final int q = qty; // effectively final for lambda
            map.compute(item.getId(), (id, existing) -> {
                if (existing == null) return new CartItem(item, q);
                existing.setQuantity(existing.getQuantity() + q);
                return existing;
            });
        }

        void update(Long itemId, int qty) {
            if (qty < 1) qty = 1;
            CartItem existing = map.get(itemId);
            if (existing != null) existing.setQuantity(qty);
        }

        void remove(Long itemId) {
            map.remove(itemId);
        }

        void clear() {
            map.clear();
        }

        List<CartItem> items() {
            return new ArrayList<>(map.values());
        }

        BigDecimal total() {
            return map.values().stream()
                    .map(CartItem::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    public static class CartItem {
        private PackageItem item;
        private int quantity;

        public CartItem(PackageItem item, int quantity) {
            this.item = item;
            this.quantity = quantity;
        }

        public PackageItem getItem() { return item; }
        public void setItem(PackageItem item) { this.item = item; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        public BigDecimal getSubtotal() {
            BigDecimal price = item.getPrice() != null ? item.getPrice() : BigDecimal.ZERO;
            return price.multiply(BigDecimal.valueOf(quantity));
        }
    }
}
