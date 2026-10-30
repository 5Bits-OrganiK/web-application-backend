package _5bits.organik.platform.suppliers.application.internal.commandservices;

import _5bits.organik.platform.suppliers.application.commandservices.SupplierCommandFailure;
import _5bits.organik.platform.suppliers.application.commandservices.SupplierCommandService;
import _5bits.organik.platform.suppliers.domain.model.aggregates.Supplier;
import _5bits.organik.platform.suppliers.domain.model.commands.CreateSupplierCommand;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.SupplierRepository;
import _5bits.organik.platform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static _5bits.organik.platform.suppliers.domain.model.aggregates.Supplier.EMAIL_UNIQUE_CONSTRAINT;

@Slf4j
@Service
public class SupplierCommandServiceImpl implements SupplierCommandService {
    private static final String DUPLICATE_SUPPLIER_CONSTRAINT = EMAIL_UNIQUE_CONSTRAINT;
    private final SupplierRepository supplierRepository;

    public SupplierCommandServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    @Transactional
    public Result<Supplier, SupplierCommandFailure> handle(CreateSupplierCommand command) {
        if (supplierRepository.existsByEmail(command.email())) {
            log.warn("Duplicate supplier detected (pre-check): email={}", command.email().value());
            return duplicateResult();
        }
        try {
            var supplier = new Supplier(command);
            var createdSupplier = supplierRepository.save(supplier);
            log.info("Supplier created: email={}, id={}, createdAt={}",
                    command.email().value(),
                    createdSupplier.getId(),
                    createdSupplier.getCreatedAt());
            return Result.success(createdSupplier);
        } catch (DataIntegrityViolationException exception) {
            if (isDuplicateSupplierViolation(exception)) {
                log.warn("Duplicate supplier detected (constraint violation): email={}", command.email().value());
                return duplicateResult();
            }
            log.error("Unexpected data integrity violation saving supplier: email={}", command.email().value(), exception);
            throw exception;
        }
    }

    private Result<Supplier, SupplierCommandFailure> duplicateResult() {
        return Result.failure(new SupplierCommandFailure.Duplicate());
    }

    private boolean isDuplicateSupplierViolation(DataIntegrityViolationException exception) {
        Throwable violationCause = exception;
        while (violationCause != null) {
            String message = violationCause.getMessage();
            if (message != null && message.contains(DUPLICATE_SUPPLIER_CONSTRAINT)) return true;
            violationCause = violationCause.getCause();
        }
        return false;
    }
}
