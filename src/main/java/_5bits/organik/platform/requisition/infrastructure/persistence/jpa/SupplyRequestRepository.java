package _5bits.organik.platform.requisition.infrastructure.persistence.jpa;

import _5bits.organik.platform.requisition.domain.model.aggregates.SupplyRequest;
import _5bits.organik.platform.requisition.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.requisition.domain.model.valueobjects.SupplierId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyRequestRepository extends JpaRepository<SupplyRequest, Long> {
    List<SupplyRequest> findAllByMinimarketId(MinimarketId minimarketId);
    List<SupplyRequest> findAllBySupplierId(SupplierId supplierId);
}
