package _5bits.organik.platform.requisition.application.internal.commandservices;

import _5bits.organik.platform.requisition.application.commandservices.SupplyRequestCommandService;
import _5bits.organik.platform.requisition.domain.model.aggregates.SupplyRequest;
import _5bits.organik.platform.requisition.domain.model.commands.CreateSupplyRequestCommand;
import _5bits.organik.platform.requisition.infrastructure.persistence.jpa.SupplyRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SupplyRequestCommandServiceImpl implements SupplyRequestCommandService {
    private final SupplyRequestRepository supplyRequestRepository;

    public SupplyRequestCommandServiceImpl(SupplyRequestRepository supplyRequestRepository) {
        this.supplyRequestRepository = supplyRequestRepository;
    }

    @Override
    @Transactional
    public SupplyRequest handle(CreateSupplyRequestCommand command) {
        var createdSupplyRequest = supplyRequestRepository.save(new SupplyRequest(command));
        log.info("Supply request created: minimarketId={}, supplierId={}, productId={}, id={}",
                command.minimarketId().value(),
                command.supplierId().value(),
                command.productId().value(),
                createdSupplyRequest.getId());
        return createdSupplyRequest;
    }
}
