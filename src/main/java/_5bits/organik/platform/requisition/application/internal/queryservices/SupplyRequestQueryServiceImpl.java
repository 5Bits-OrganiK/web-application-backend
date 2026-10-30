package _5bits.organik.platform.requisition.application.internal.queryservices;

import _5bits.organik.platform.requisition.application.queryservices.SupplyRequestQueryService;
import _5bits.organik.platform.requisition.domain.model.aggregates.SupplyRequest;
import _5bits.organik.platform.requisition.domain.model.queries.GetAllSupplyRequestsByMinimarketIdQuery;
import _5bits.organik.platform.requisition.domain.model.queries.GetAllSupplyRequestsBySupplierIdQuery;
import _5bits.organik.platform.requisition.domain.model.queries.GetSupplyRequestByIdQuery;
import _5bits.organik.platform.requisition.infrastructure.persistence.jpa.SupplyRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class SupplyRequestQueryServiceImpl implements SupplyRequestQueryService {
    private final SupplyRequestRepository supplyRequestRepository;

    public SupplyRequestQueryServiceImpl(SupplyRequestRepository supplyRequestRepository) {
        this.supplyRequestRepository = supplyRequestRepository;
    }

    @Override
    public Optional<SupplyRequest> handle(GetSupplyRequestByIdQuery query) {
        log.debug("Querying supply request by id={}", query.id());
        var result = supplyRequestRepository.findById(query.id());
        if (result.isEmpty()) log.debug("No supply request found for id={}", query.id());
        return result;
    }

    @Override
    public List<SupplyRequest> handle(GetAllSupplyRequestsByMinimarketIdQuery query) {
        log.debug("Querying all supply requests for minimarketId={}", query.minimarketId().value());
        var results = supplyRequestRepository.findAllByMinimarketId(query.minimarketId());
        log.debug("Found {} supply request(s) for minimarketId={}", results.size(), query.minimarketId().value());
        return results;
    }

    @Override
    public List<SupplyRequest> handle(GetAllSupplyRequestsBySupplierIdQuery query) {
        log.debug("Querying all supply requests for supplierId={}", query.supplierId().value());
        var results = supplyRequestRepository.findAllBySupplierId(query.supplierId());
        log.debug("Found {} supply request(s) for supplierId={}", results.size(), query.supplierId().value());
        return results;
    }
}
