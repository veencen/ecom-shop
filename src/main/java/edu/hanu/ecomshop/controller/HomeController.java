package edu.hanu.ecomshop.controller;

import edu.hanu.ecomshop.model.Product;
import edu.hanu.ecomshop.service.CategoryService;
import edu.hanu.ecomshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    private final ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    List<Product> products;

    @GetMapping(value = {"/", "/index", "/home"})
    public String home(Model model) {
        model.addAttribute("products", getAllProducts());
        model.addAttribute("productsCount", productsCount());
        model.addAttribute("categories", categoryService.findAll());
        return "home";
    }

    @RequestMapping("/searchByCategory")
    public String searchByCategory(@RequestParam("categoryId") long categoryId, Model model) {
        products = productService.findAllByCategoryId(categoryId);
        model.addAttribute("products", productService.findAllByCategoryId(categoryId));
        model.addAttribute("productsCount", products.size());
        model.addAttribute("categories", categoryService.findAll());
        return "home";
    }

    @RequestMapping("/sortByAscPrice")
    public String sortByAscPrice(Model model) {
        products = productService.findAllByOrderByPriceAsc();
        model.addAttribute("products", productService.findAllByOrderByPriceAsc());
        model.addAttribute("productsCount", products.size());
        model.addAttribute("categories", categoryService.findAll());
        return "home";
    }

    @RequestMapping("/sortByDescPrice")
    public String sortByDescPrice(Model model) {
        products = productService.findAllByOrderByPriceDesc();
        model.addAttribute("products", productService.findAllByOrderByPriceDesc());
        model.addAttribute("productsCount", products.size());
        model.addAttribute("categories", categoryService.findAll());
        return "home";
    }

    @GetMapping("/searchByName")
    public String searchByName(@RequestParam("search") String search, Model model) {
        products = productService.findAllByNameContainingIgnoreCase(search);
        model.addAttribute("products", productService.findAllByNameContainingIgnoreCase(search));
        model.addAttribute("productsCount", products.size());
        model.addAttribute("categories", categoryService.findAll());
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }

    private List<Product> getAllProducts() {
        return productService.findAllByOrderByIdAsc();
    }

    private long productsCount() {
        return productService.count();
    }

}
