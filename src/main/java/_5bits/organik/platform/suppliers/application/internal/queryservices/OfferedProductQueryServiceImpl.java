package _5bits.organik.platform.suppliers.application.internal.queryservices;

import _5bits.organik.platform.suppliers.application.queryservices.OfferedProductQueryService;
import _5bits.organik.platform.suppliers.domain.model.aggregates.OfferedProduct;
import _5bits.organik.platform.suppliers.domain.model.queries.GetAllOfferedProductsBySupplierIdQuery;
import _5bits.organik.platform.suppliers.domain.model.queries.GetOfferedProductByIdQuery;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.OfferedProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class OfferedProductQueryServiceImpl implements OfferedProductQueryService {
    private final OfferedProductRepository offeredProductRepository;

    public OfferedProductQueryServiceImpl(OfferedProductRepository offeredProductRepository) {
        this.offeredProductRepository = offeredProductRepository;
    }

    @Override
    public List<OfferedProduct> handle(GetAllOfferedProductsBySupplierIdQuery query) {
        log.debug("Querying all offered products for supplierId={}", query.supplierId().value());
        var results = offeredProductRepository.findAllBySupplierId(query.supplierId());
        log.debug("Found {} offered product(s) for supplierId={}", results.size(), query.supplierId().value());
        return results;
    }

    @Override
    public Optional<OfferedProduct> handle(GetOfferedProductByIdQuery query) {
        log.debug("Querying offered product by id={}", query.id());
        var result = offeredProductRepository.findById(query.id());
        if (result.isEmpty()) log.debug("No offered product found for id={}", query.id());
        return result;
    }
}
