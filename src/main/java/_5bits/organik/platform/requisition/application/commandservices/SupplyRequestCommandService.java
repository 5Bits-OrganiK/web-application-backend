package _5bits.organik.platform.requisition.application.commandservices;

import _5bits.organik.platform.requisition.domain.model.aggregates.SupplyRequest;
import _5bits.organik.platform.requisition.domain.model.commands.CreateSupplyRequestCommand;

public interface SupplyRequestCommandService {
    SupplyRequest handle(CreateSupplyRequestCommand command);
}
