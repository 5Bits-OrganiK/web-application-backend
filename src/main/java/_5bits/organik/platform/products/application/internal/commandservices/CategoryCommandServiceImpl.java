package _5bits.organik.platform.products.application.internal.commandservices;

import _5bits.organik.platform.products.application.commandservices.CategoryCommandFailure;
import _5bits.organik.platform.products.application.commandservices.CategoryCommandService;
import _5bits.organik.platform.products.domain.model.aggregates.Category;
import _5bits.organik.platform.products.domain.model.commands.CreateCategoryCommand;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.CategoryRepository;
import _5bits.organik.platform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static _5bits.organik.platform.products.domain.model.aggregates.Category.NAME_UNIQUE_CONSTRAINT;

@Slf4j
@Service
public class CategoryCommandServiceImpl implements CategoryCommandService {
    private static final String DUPLICATE_CATEGORY_CONSTRAINT = NAME_UNIQUE_CONSTRAINT;
    private final CategoryRepository categoryRepository;

    public CategoryCommandServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Result<Category, CategoryCommandFailure> handle(CreateCategoryCommand command) {
        if (categoryRepository.existsByName(command.name())) {
            log.warn("Duplicate category detected (pre-check): name={}", command.name().value());
            return duplicateResult();
        }
        try {
            var category = new Category(command);
            var createdCategory = categoryRepository.save(category);
            log.info("Category created: name={}, tone={}, id={}",
                    command.name().value(),
                    command.tone(),
                    createdCategory.getId());
            return Result.success(createdCategory);
        } catch (DataIntegrityViolationException exception) {
            if (isDuplicateCategoryViolation(exception)) {
                log.warn("Duplicate category detected (constraint violation): name={}", command.name().value());
                return duplicateResult();
            }
            log.error("Unexpected data integrity violation saving category: name={}", command.name().value(), exception);
            throw exception;
        }
    }

    private Result<Category, CategoryCommandFailure> duplicateResult() {
        return Result.failure(new CategoryCommandFailure.Duplicate());
    }

    private boolean isDuplicateCategoryViolation(DataIntegrityViolationException exception) {
        Throwable violationCause = exception;
        while (violationCause != null) {
            String message = violationCause.getMessage();
            if (message != null && message.contains(DUPLICATE_CATEGORY_CONSTRAINT)) return true;
            violationCause = violationCause.getCause();
        }
        return false;
    }
}
