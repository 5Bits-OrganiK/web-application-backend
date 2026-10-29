package _5bits.organik.platform.procurements.infrastructure.persistence.jpa;

import _5bits.organik.platform.procurements.domain.model.aggregates.ShipmentOrder;
import _5bits.organik.platform.procurements.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.procurements.domain.model.valueobjects.SupplierId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentOrderRepository extends JpaRepository<ShipmentOrder, Long> {
    List<ShipmentOrder> findAllBySupplierId(SupplierId supplierId);
    List<ShipmentOrder> findAllByMinimarketId(MinimarketId minimarketId);
}
