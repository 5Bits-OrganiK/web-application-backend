package _5bits.organik.platform.inventory.application.commandservices;

import _5bits.organik.platform.inventory.domain.model.aggregates.StockLot;
import _5bits.organik.platform.inventory.domain.model.commands.CreateStockLotCommand;
import _5bits.organik.platform.shared.application.result.Result;

public interface StockLotCommandService {
    Result<StockLot, StockLotCommandFailure> handle(CreateStockLotCommand command);
}
