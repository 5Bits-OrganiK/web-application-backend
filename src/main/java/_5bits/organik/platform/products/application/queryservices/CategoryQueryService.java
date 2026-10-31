package _5bits.organik.platform.products.application.queryservices;

import _5bits.organik.platform.products.domain.model.aggregates.Category;
import _5bits.organik.platform.products.domain.model.queries.GetAllCategoriesQuery;
import _5bits.organik.platform.products.domain.model.queries.GetCategoryByIdQuery;

import java.util.List;
import java.util.Optional;

public interface CategoryQueryService {
    List<Category> handle(GetAllCategoriesQuery query);
    Optional<Category> handle(GetCategoryByIdQuery query);
}
