package _5bits.organik.platform.suppliers.application.commandservices;

import _5bits.organik.platform.suppliers.domain.model.aggregates.OfferedProduct;
import _5bits.organik.platform.suppliers.domain.model.commands.CreateOfferedProductCommand;
import _5bits.organik.platform.shared.application.result.Result;

public interface OfferedProductCommandService {
    Result<OfferedProduct, OfferedProductCommandFailure> handle(CreateOfferedProductCommand command);
}
