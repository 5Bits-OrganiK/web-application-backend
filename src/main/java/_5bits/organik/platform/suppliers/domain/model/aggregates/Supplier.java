package _5bits.organik.platform.suppliers.domain.model.aggregates;

import _5bits.organik.platform.suppliers.domain.model.valueobjects.BusinessName;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.ContactName;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.EmailAddress;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.OrganicCertification;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.PhoneNumber;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.SupplierCategory;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters.BusinessNameAttributeConverter;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters.ContactNameAttributeConverter;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters.EmailAddressAttributeConverter;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters.OrganicCertificationAttributeConverter;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters.PhoneNumberAttributeConverter;
import _5bits.organik.platform.suppliers.domain.model.commands.CreateSupplierCommand;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "email" }, name = Supplier.EMAIL_UNIQUE_CONSTRAINT)
})
public class Supplier {
    public static final String EMAIL_UNIQUE_CONSTRAINT = "uk_supplier_email";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Convert(converter = BusinessNameAttributeConverter.class)
    private BusinessName businessName;

    @Column(nullable = false)
    @Convert(converter = ContactNameAttributeConverter.class)
    private ContactName contactName;

    @Column(nullable = false)
    @Convert(converter = PhoneNumberAttributeConverter.class)
    private PhoneNumber phoneNumber;

    @Column(nullable = false)
    @Convert(converter = EmailAddressAttributeConverter.class)
    private EmailAddress email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Set<SupplierCategory> categories = new HashSet<>();

    @Column(nullable = false)
    @Convert(converter = OrganicCertificationAttributeConverter.class)
    private OrganicCertification organicCertification;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    protected Supplier() {}

    public Supplier(CreateSupplierCommand command) {
        this.businessName = command.businessName();
        this.contactName = command.contactName();
        this.phoneNumber = command.phoneNumber();
        this.email = command.email();
        this.categories = new HashSet<>(command.categories());
        this.organicCertification = command.organicCertification();
    }
}
