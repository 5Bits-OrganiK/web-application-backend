package _5bits.organik.platform.products.application.internal.queryservices;

import _5bits.organik.platform.products.application.queryservices.CategoryQueryService;
import _5bits.organik.platform.products.domain.model.aggregates.Category;
import _5bits.organik.platform.products.domain.model.queries.GetAllCategoriesQuery;
import _5bits.organik.platform.products.domain.model.queries.GetCategoryByIdQuery;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class CategoryQueryServiceImpl implements CategoryQueryService {
    private final CategoryRepository categoryRepository;

    public CategoryQueryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> handle(GetAllCategoriesQuery query) {
        log.debug("Querying all categories");
        var results = categoryRepository.findAll();
        log.debug("Found {} category(ies)", results.size());
        return results;
    }

    @Override
    public Optional<Category> handle(GetCategoryByIdQuery query) {
        log.debug("Querying category by id={}", query.id());
        var result = categoryRepository.findById(query.id());
        if (result.isEmpty()) log.debug("No category found for id={}", query.id());
        return result;
    }
}
