package _5bits.organik.platform.products.application.commandservices;

import _5bits.organik.platform.products.domain.model.aggregates.Product;
import _5bits.organik.platform.products.domain.model.commands.CreateProductCommand;
import _5bits.organik.platform.shared.application.result.Result;

public interface ProductCommandService {
    Result<Product, ProductCommandFailure> handle(CreateProductCommand command);
}
