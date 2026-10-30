package _5bits.organik.platform.suppliers.application.commandservices;

import _5bits.organik.platform.suppliers.domain.model.aggregates.Supplier;
import _5bits.organik.platform.suppliers.domain.model.commands.CreateSupplierCommand;
import _5bits.organik.platform.shared.application.result.Result;

public interface SupplierCommandService {
    Result<Supplier, SupplierCommandFailure> handle(CreateSupplierCommand command);
}
