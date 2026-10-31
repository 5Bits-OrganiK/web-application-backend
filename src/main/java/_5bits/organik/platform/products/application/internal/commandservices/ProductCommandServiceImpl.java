package _5bits.organik.platform.products.application.internal.commandservices;

import _5bits.organik.platform.products.application.commandservices.ProductCommandFailure;
import _5bits.organik.platform.products.application.commandservices.ProductCommandService;
import _5bits.organik.platform.products.domain.model.aggregates.Product;
import _5bits.organik.platform.products.domain.model.commands.CreateProductCommand;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.CategoryRepository;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.ProductRepository;
import _5bits.organik.platform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static _5bits.organik.platform.products.domain.model.aggregates.Product.SKU_UNIQUE_CONSTRAINT;

@Slf4j
@Service
public class ProductCommandServiceImpl implements ProductCommandService {
    private static final String DUPLICATE_PRODUCT_CONSTRAINT = SKU_UNIQUE_CONSTRAINT;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductCommandServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Result<Product, ProductCommandFailure> handle(CreateProductCommand command) {
        if (!categoryRepository.existsById(command.categoryId().value())) {
            log.warn("Product rejected, category not found: sku={}, categoryId={}",
                    command.sku().value(), command.categoryId().value());
            return Result.failure(new ProductCommandFailure.CategoryNotFound());
        }
        if (productRepository.existsBySku(command.sku())) {
            log.warn("Duplicate product detected (pre-check): sku={}", command.sku().value());
            return duplicateResult();
        }
        try {
            var product = new Product(command);
            var createdProduct = productRepository.save(product);
            log.info("Product created: sku={}, categoryId={}, id={}",
                    command.sku().value(),
                    command.categoryId().value(),
                    createdProduct.getId());
            return Result.success(createdProduct);
        } catch (DataIntegrityViolationException exception) {
            if (isDuplicateProductViolation(exception)) {
                log.warn("Duplicate product detected (constraint violation): sku={}", command.sku().value());
                return duplicateResult();
            }
            log.error("Unexpected data integrity violation saving product: sku={}", command.sku().value(), exception);
            throw exception;
        }
    }

    private Result<Product, ProductCommandFailure> duplicateResult() {
        return Result.failure(new ProductCommandFailure.Duplicate());
    }

    private boolean isDuplicateProductViolation(DataIntegrityViolationException exception) {
        Throwable violationCause = exception;
        while (violationCause != null) {
            String message = violationCause.getMessage();
            if (message != null && message.contains(DUPLICATE_PRODUCT_CONSTRAINT)) return true;
            violationCause = violationCause.getCause();
        }
        return false;
    }
}
