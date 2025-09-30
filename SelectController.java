package com.example.demo.controllers;

import com.example.demo.model.PackageItem;
import com.example.demo.services.SelectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SelectController {

    private final SelectService service;

    public SelectController(SelectService service) {
        this.service = service;
    }

    /* -------- Catalog / Choose page -------- */

    @GetMapping({ "/choosepackage"})
    public String choosePackage(Model model) {
        model.addAttribute("items", service.listAvailable());
        return "choosepackage";
    }

    /* -------- Cart (addpackage.html) -------- */

    @GetMapping("/addpackage")
    public String viewCart(Model model) {
        model.addAttribute("cartItems", service.getCartItems());
        model.addAttribute("total", service.getTotal());
        return "addpackage";
    }

    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable Long id, @RequestParam(defaultValue = "1") int qty) {
        service.addToCart(id, qty);
        return "redirect:/addpackage";
    }

    @PostMapping("/cart/update/{id}")
    public String updateQty(@PathVariable Long id, @RequestParam int qty) {
        service.updateQty(id, qty);
        return "redirect:/addpackage";
    }

    @PostMapping("/cart/remove/{id}")
    public String remove(@PathVariable Long id) {
        service.removeFromCart(id);
        return "redirect:/addpackage";
    }

    @PostMapping("/cart/clear")
    public String clear() {
        service.clearCart();
        return "redirect:/addpackage";
    }

    @PostMapping("/cart/checkout")
    public String checkout() {
        // TODO: Persist a composed "Package" from cart items if you need.
        service.clearCart();
        return "redirect:/choosepackage";
    }

    /* -------- Create/Edit form -------- */

    @GetMapping("/package/new")
    public String showCreateForm(Model model) {
        model.addAttribute("packageItem", new PackageItem());
        return "package-form"; // create this template
    }

    @GetMapping("/package/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        PackageItem item = service.findItem(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
        model.addAttribute("packageItem", item);
        return "package-form"; // reuse same template
    }

    @PostMapping("/package/save")
    public String savePackage(@ModelAttribute("packageItem") PackageItem packageItem) {
        service.saveItem(packageItem);
        return "redirect:/choosepackage";
    }

    @PostMapping("/package/{id}/delete")
    public String deletePackage(@PathVariable Long id) {
        service.deleteItem(id);
        return "redirect:/choosepackage";
    }
}
