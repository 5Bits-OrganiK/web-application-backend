package _5bits.organik.platform.products.domain.model.commands;

import _5bits.organik.platform.products.domain.model.valueobjects.CategoryName;
import _5bits.organik.platform.products.domain.model.valueobjects.CategoryTone;

public record CreateCategoryCommand(CategoryName name, CategoryTone tone) {
    public CreateCategoryCommand {
        if (name == null) {
            throw new IllegalArgumentException("CategoryName cannot be null");
        }
        if (tone == null) {
            throw new IllegalArgumentException("CategoryTone cannot be null");
        }
    }
}
