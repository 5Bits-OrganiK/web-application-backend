package _5bits.organik.platform.products.infrastructure.persistence.jpa;

import _5bits.organik.platform.products.domain.model.aggregates.Product;
import _5bits.organik.platform.products.domain.model.valueobjects.CategoryId;
import _5bits.organik.platform.products.domain.model.valueobjects.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsBySku(Sku sku);
    List<Product> findAllByCategoryId(CategoryId categoryId);
}
