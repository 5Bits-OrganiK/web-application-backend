package _5bits.organik.platform.inventory.infrastructure.persistence.jpa;

import _5bits.organik.platform.inventory.domain.model.aggregates.InventoryMovement;
import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Long> {
    List<InventoryMovement> findAllByStockLotIdOrderByCreatedAtDesc(StockLotId stockLotId);
}
