package _5bits.organik.platform.procurements.application.internal.commandservices;

import _5bits.organik.platform.procurements.application.commandservices.ShipmentOrderCommandService;
import _5bits.organik.platform.procurements.application.commandservices.ShipmentOrderCommandFailure;
import _5bits.organik.platform.procurements.domain.model.aggregates.ShipmentOrder;
import _5bits.organik.platform.procurements.domain.model.commands.AcceptShipmentOrderCommand;
import _5bits.organik.platform.procurements.domain.model.commands.CreateShipmentOrderCommand;
import _5bits.organik.platform.procurements.domain.model.commands.RejectShipmentOrderCommand;
import _5bits.organik.platform.procurements.infrastructure.persistence.jpa.ShipmentOrderRepository;
import _5bits.organik.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShipmentOrderCommandServiceImpl implements ShipmentOrderCommandService {
    private final ShipmentOrderRepository shipmentOrderRepository;

    public ShipmentOrderCommandServiceImpl(ShipmentOrderRepository shipmentOrderRepository) {
        this.shipmentOrderRepository = shipmentOrderRepository;
    }

    @Override
    @Transactional
    public Result<ShipmentOrder, ShipmentOrderCommandFailure> handle(CreateShipmentOrderCommand command) {
        return Result.success(shipmentOrderRepository.save(new ShipmentOrder(command)));
    }

    @Override
    @Transactional
    public Result<ShipmentOrder, ShipmentOrderCommandFailure> handle(AcceptShipmentOrderCommand command) {
        var order = shipmentOrderRepository.findById(command.shipmentOrderId()).orElse(null);
        if (order == null) {
            return Result.failure(new ShipmentOrderCommandFailure.NotFound());
        }
        try {
            order.accept(command.decidedBy());
        } catch (IllegalStateException exception) {
            return Result.failure(new ShipmentOrderCommandFailure.InvalidState());
        }
        return Result.success(shipmentOrderRepository.save(order));
    }

    @Override
    @Transactional
    public Result<ShipmentOrder, ShipmentOrderCommandFailure> handle(RejectShipmentOrderCommand command) {
        var order = shipmentOrderRepository.findById(command.shipmentOrderId()).orElse(null);
        if (order == null) {
            return Result.failure(new ShipmentOrderCommandFailure.NotFound());
        }
        try {
            order.reject(command.decidedBy(), command.reason());
        } catch (IllegalStateException exception) {
            return Result.failure(new ShipmentOrderCommandFailure.InvalidState());
        }
        return Result.success(shipmentOrderRepository.save(order));
    }
}
