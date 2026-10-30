package _5bits.organik.platform.suppliers.application.internal.queryservices;

import _5bits.organik.platform.suppliers.application.queryservices.SupplierQueryService;
import _5bits.organik.platform.suppliers.domain.model.aggregates.Supplier;
import _5bits.organik.platform.suppliers.domain.model.queries.GetAllSuppliersQuery;
import _5bits.organik.platform.suppliers.domain.model.queries.GetSupplierByIdQuery;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.SupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class SupplierQueryServiceImpl implements SupplierQueryService {
    private final SupplierRepository supplierRepository;

    public SupplierQueryServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<Supplier> handle(GetAllSuppliersQuery query) {
        log.debug("Querying all suppliers");
        var results = supplierRepository.findAll();
        log.debug("Found {} supplier(s)", results.size());
        return results;
    }

    @Override
    public Optional<Supplier> handle(GetSupplierByIdQuery query) {
        log.debug("Querying supplier by id={}", query.id());
        var result = supplierRepository.findById(query.id());
        if (result.isEmpty()) log.debug("No supplier found for id={}", query.id());
        return result;
    }
}
