package lv.gennadyyonov.spring.jpa.multiple.db.dao.product;

import lv.gennadyyonov.spring.jpa.multiple.db.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
