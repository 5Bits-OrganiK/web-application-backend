package _5bits.organik.platform.suppliers.application.internal.commandservices;

import _5bits.organik.platform.suppliers.application.commandservices.OfferedProductCommandFailure;
import _5bits.organik.platform.suppliers.application.commandservices.OfferedProductCommandService;
import _5bits.organik.platform.suppliers.domain.model.aggregates.OfferedProduct;
import _5bits.organik.platform.suppliers.domain.model.commands.CreateOfferedProductCommand;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.OfferedProductRepository;
import _5bits.organik.platform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static _5bits.organik.platform.suppliers.domain.model.aggregates.OfferedProduct.SUPPLIER_LOT_UNIQUE_CONSTRAINT;

@Slf4j
@Service
public class OfferedProductCommandServiceImpl implements OfferedProductCommandService {
    private static final String DUPLICATE_OFFERED_PRODUCT_CONSTRAINT = SUPPLIER_LOT_UNIQUE_CONSTRAINT;
    private final OfferedProductRepository offeredProductRepository;

    public OfferedProductCommandServiceImpl(OfferedProductRepository offeredProductRepository) {
        this.offeredProductRepository = offeredProductRepository;
    }

    @Override
    @Transactional
    public Result<OfferedProduct, OfferedProductCommandFailure> handle(CreateOfferedProductCommand command) {
        if (offeredProductRepository.existsBySupplierIdAndLotCode(command.supplierId(), command.lotCode())) {
            log.warn("Duplicate offered product detected (pre-check): supplierId={}, lotCode={}",
                    command.supplierId().value(), command.lotCode().value());
            return duplicateResult();
        }
        try {
            var offeredProduct = new OfferedProduct(command);
            var createdOfferedProduct = offeredProductRepository.save(offeredProduct);
            log.info("Offered product created: supplierId={}, lotCode={}, id={}",
                    command.supplierId().value(),
                    command.lotCode().value(),
                    createdOfferedProduct.getId());
            return Result.success(createdOfferedProduct);
        } catch (DataIntegrityViolationException exception) {
            if (isDuplicateOfferedProductViolation(exception)) {
                log.warn("Duplicate offered product detected (constraint violation): supplierId={}, lotCode={}",
                        command.supplierId().value(), command.lotCode().value());
                return duplicateResult();
            }
            log.error("Unexpected data integrity violation saving offered product: supplierId={}, lotCode={}",
                    command.supplierId().value(), command.lotCode().value(), exception);
            throw exception;
        }
    }

    private Result<OfferedProduct, OfferedProductCommandFailure> duplicateResult() {
        return Result.failure(new OfferedProductCommandFailure.Duplicate());
    }

    private boolean isDuplicateOfferedProductViolation(DataIntegrityViolationException exception) {
        Throwable violationCause = exception;
        while (violationCause != null) {
            String message = violationCause.getMessage();
            if (message != null && message.contains(DUPLICATE_OFFERED_PRODUCT_CONSTRAINT)) return true;
            violationCause = violationCause.getCause();
        }
        return false;
    }
}
