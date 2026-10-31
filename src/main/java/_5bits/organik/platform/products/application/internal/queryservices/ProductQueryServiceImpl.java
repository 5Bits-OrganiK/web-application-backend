package _5bits.organik.platform.products.application.internal.queryservices;

import _5bits.organik.platform.products.application.queryservices.ProductQueryService;
import _5bits.organik.platform.products.domain.model.aggregates.Product;
import _5bits.organik.platform.products.domain.model.queries.GetAllProductsByCategoryIdQuery;
import _5bits.organik.platform.products.domain.model.queries.GetAllProductsQuery;
import _5bits.organik.platform.products.domain.model.queries.GetProductByIdQuery;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ProductQueryServiceImpl implements ProductQueryService {
    private final ProductRepository productRepository;

    public ProductQueryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> handle(GetAllProductsQuery query) {
        log.debug("Querying all products");
        var results = productRepository.findAll();
        log.debug("Found {} product(s)", results.size());
        return results;
    }

    @Override
    public List<Product> handle(GetAllProductsByCategoryIdQuery query) {
        log.debug("Querying all products for categoryId={}", query.categoryId().value());
        var results = productRepository.findAllByCategoryId(query.categoryId());
        log.debug("Found {} product(s) for categoryId={}", results.size(), query.categoryId().value());
        return results;
    }

    @Override
    public Optional<Product> handle(GetProductByIdQuery query) {
        log.debug("Querying product by id={}", query.id());
        var result = productRepository.findById(query.id());
        if (result.isEmpty()) log.debug("No product found for id={}", query.id());
        return result;
    }
}
