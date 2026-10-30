package _5bits.organik.platform.products.domain.model.queries;

import _5bits.organik.platform.products.domain.model.valueobjects.CategoryId;

public record GetAllProductsByCategoryIdQuery(CategoryId categoryId) {
    public GetAllProductsByCategoryIdQuery {
        if (categoryId == null) throw new IllegalArgumentException("categoryId cannot be null");
    }
}
