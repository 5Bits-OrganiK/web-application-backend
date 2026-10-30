package _5bits.organik.platform.inventory.infrastructure.persistence.jpa;

import _5bits.organik.platform.inventory.domain.model.aggregates.Offer;
import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findAllByStockLotIdOrderByStartsOnDesc(StockLotId stockLotId);
}
