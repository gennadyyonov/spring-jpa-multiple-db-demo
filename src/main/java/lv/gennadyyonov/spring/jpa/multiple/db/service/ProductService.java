package lv.gennadyyonov.spring.jpa.multiple.db.service;

import lombok.RequiredArgsConstructor;
import lv.gennadyyonov.spring.jpa.multiple.db.dao.product.ProductRepository;
import lv.gennadyyonov.spring.jpa.multiple.db.dto.product.ProductDto;
import lv.gennadyyonov.spring.jpa.multiple.db.model.product.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lv.gennadyyonov.spring.jpa.multiple.db.config.DatabaseConstants.PRODUCT_TRANSACTION_MANAGER_NAME;

@Transactional(PRODUCT_TRANSACTION_MANAGER_NAME)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    public ProductDto create(ProductDto dto) {
        Product newProduct = modelMapper.map(dto, Product.class);
        Product savedProduct = productRepository.save(newProduct);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    public ProductDto getById(Long id) {
        Product product = productRepository.getById(id);
        return modelMapper.map(product, ProductDto.class);
    }
}
