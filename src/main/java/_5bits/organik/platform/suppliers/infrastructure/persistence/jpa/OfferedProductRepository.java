package _5bits.organik.platform.suppliers.infrastructure.persistence.jpa;

import _5bits.organik.platform.suppliers.domain.model.aggregates.OfferedProduct;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.SupplierId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferedProductRepository extends JpaRepository<OfferedProduct, Long> {
    List<OfferedProduct> findAllBySupplierId(SupplierId supplierId);
    boolean existsBySupplierIdAndLotCode(SupplierId supplierId, LotCode lotCode);
}
