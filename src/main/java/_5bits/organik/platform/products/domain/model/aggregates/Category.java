package _5bits.organik.platform.products.domain.model.aggregates;

import _5bits.organik.platform.products.domain.model.commands.CreateCategoryCommand;
import _5bits.organik.platform.products.domain.model.valueobjects.CategoryName;
import _5bits.organik.platform.products.domain.model.valueobjects.CategoryTone;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.converters.CategoryNameAttributeConverter;
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
        @UniqueConstraint(columnNames = { "name" }, name = Category.NAME_UNIQUE_CONSTRAINT)
})
public class Category {
    public static final String NAME_UNIQUE_CONSTRAINT = "uk_category_name";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 128)
    @Convert(converter = CategoryNameAttributeConverter.class)
    private CategoryName name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryTone tone;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    protected Category() {}

    public Category(CreateCategoryCommand command) {
        this.name = command.name();
        this.tone = command.tone();
    }
}
