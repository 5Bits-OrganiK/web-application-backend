package _5bits.organik.platform.inventory.infrastructure.persistence.jpa;

import _5bits.organik.platform.inventory.domain.model.aggregates.StockLot;
import _5bits.organik.platform.inventory.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.inventory.domain.model.valueobjects.MinimarketId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockLotRepository extends JpaRepository<StockLot, Long> {
    List<StockLot> findAllByMinimarketId(MinimarketId minimarketId);
    boolean existsByMinimarketIdAndLotCode(MinimarketId minimarketId, LotCode lotCode);
}
