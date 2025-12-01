package org.mustapha.ClientPortal.service;

import org.mustapha.ClientPortal.dto.request.ProductDtoRequest;
import org.mustapha.ClientPortal.dto.response.ProductDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductDtoResponse createProduct(ProductDtoRequest request);
    ProductDtoResponse updateProduct(Long productId, ProductDtoRequest request);
    void deleteProduct(Long productId);
    ProductDtoResponse getProductById(Long productId);
    Page<ProductDtoResponse> getAllProducts(Pageable pageable);
}
