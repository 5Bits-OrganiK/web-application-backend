package _5bits.organik.platform.iam.domain.model.aggregates;

import _5bits.organik.platform.iam.domain.model.valueobjects.EmailAddress;
import _5bits.organik.platform.iam.domain.model.valueobjects.FullName;
import _5bits.organik.platform.iam.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.iam.domain.model.valueobjects.Role;
import _5bits.organik.platform.iam.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.iam.domain.model.valueobjects.UserStatus;
import _5bits.organik.platform.iam.infrastructure.persistence.jpa.converters.EmailAddressAttributeConverter;
import _5bits.organik.platform.iam.infrastructure.persistence.jpa.converters.FullNameAttributeConverter;
import _5bits.organik.platform.iam.infrastructure.persistence.jpa.converters.MinimarketIdAttributeConverter;
import _5bits.organik.platform.iam.infrastructure.persistence.jpa.converters.SupplierIdAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "email" }, name = User.EMAIL_UNIQUE_CONSTRAINT)
})
public class User {
    public static final String EMAIL_UNIQUE_CONSTRAINT = "uk_email";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Convert(converter = FullNameAttributeConverter.class)
    private FullName fullName;

    @Column(nullable = false)
    @Convert(converter = EmailAddressAttributeConverter.class)
    private EmailAddress email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Convert(converter = MinimarketIdAttributeConverter.class)
    private MinimarketId minimarketId;

    @Convert(converter = SupplierIdAttributeConverter.class)
    private SupplierId supplierId;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;
}
