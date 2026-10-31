package _5bits.organik.platform.products.application.commandservices;

import _5bits.organik.platform.products.domain.model.aggregates.Category;
import _5bits.organik.platform.products.domain.model.commands.CreateCategoryCommand;
import _5bits.organik.platform.shared.application.result.Result;

public interface CategoryCommandService {
    Result<Category, CategoryCommandFailure> handle(CreateCategoryCommand command);
}
