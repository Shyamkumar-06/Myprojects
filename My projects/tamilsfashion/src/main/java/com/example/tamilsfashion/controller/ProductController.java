package com.example.tamilsfashion.controller;

import com.example.tamilsfashion.entity.Product;
import com.example.tamilsfashion.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    
    @GetMapping
    public String viewProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("product", new Product());
        return "product-management";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }

    
    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {

        Product product = productService.getProductById(id);

        model.addAttribute("product", product);
        model.addAttribute("products", productService.getAllProducts());

        return "product-management";
    }

    
    @PostMapping("/update")
    public String updateProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }

    
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    
    @GetMapping("/search")
    public String searchProduct(@RequestParam("keyword") String keyword,
                                Model model) {

        model.addAttribute("products",
                productService.searchProducts(keyword));

        model.addAttribute("product", new Product());

        return "product-management";
    }

}