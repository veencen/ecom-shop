package edu.hanu.ecomshop.controller;

import edu.hanu.ecomshop.model.Category;
import edu.hanu.ecomshop.service.CategoryService;
import edu.hanu.ecomshop.model.Product;
import edu.hanu.ecomshop.service.ProductService;
import edu.hanu.ecomshop.validator.CategoryValidator;
import edu.hanu.ecomshop.validator.ProductValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    private final ProductService productService;
    private final CategoryValidator categoryValidator;
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(ProductService productService, CategoryValidator categoryValidator, CategoryService categoryService) {
        this.productService = productService;
        this.categoryValidator = categoryValidator;
        this.categoryService = categoryService;
    }

    @RequestMapping("/category")
    public String showCategory(Model model) {
        model.addAttribute("products", getAllProducts());
        model.addAttribute("productsCount", productsCount());
        model.addAttribute("categories", categoryService.findAll());
        return "category";
    }

    @GetMapping("/category/new")
    public String newCategory(Model model) {
        model.addAttribute("categoryForm", new Category());
        model.addAttribute("method", "new");
        model.addAttribute("categories", categoryService.findAll());
        return "category-add";
    }

    @PostMapping("/category/new")
    public String newCategory(@ModelAttribute("categoryForm") Category categoryForm, BindingResult bindingResult, Model model) {
        categoryValidator.validate(categoryForm, bindingResult);
        if (bindingResult.hasErrors()) {
            logger.error(String.valueOf(bindingResult.getFieldError()));
            model.addAttribute("method", "new");
            return "category-add";
        }
        categoryService.save(categoryForm);
        logger.debug(String.format("Category with id: %s successfully created.", categoryForm.getId()));

        return "redirect:/category";
    }

    private List<Product> getAllProducts() {
        return productService.findAllByOrderByIdAsc();
    }

    private long productsCount() {
        return productService.count();
    }
}
