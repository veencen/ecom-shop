package edu.hanu.ecomshop.validator;

import edu.hanu.ecomshop.model.Category;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CategoryValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Category.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Category category = (Category) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryName","error.not_empty");

        // categoryName must have from 2 characters to 32
        if (category.getCategoryName().length() <= 1) {
            errors.rejectValue("categoryName", "product.error.name.less_2");
        }
        if (category.getCategoryName().length() > 32) {
            errors.rejectValue("categoryName", "product.error.name.over_32");
        }
    }
}
