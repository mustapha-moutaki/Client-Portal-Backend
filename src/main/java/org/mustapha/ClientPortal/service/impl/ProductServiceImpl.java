package org.mustapha.ClientPortal.service.impl;

import lombok.RequiredArgsConstructor;
import org.mustapha.ClientPortal.dto.request.ProductDtoRequest;
import org.mustapha.ClientPortal.dto.response.ProductDtoResponse;
import org.mustapha.ClientPortal.exception.ResourceNotFoundException;
import org.mustapha.ClientPortal.mapper.ProductMapper;
import org.mustapha.ClientPortal.model.Product;
import org.mustapha.ClientPortal.repository.ProductRepository;
import org.mustapha.ClientPortal.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDtoResponse createProduct(ProductDtoRequest request) {
        Product product = productMapper.toEntity(request);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public ProductDtoResponse updateProduct(Long productId, ProductDtoRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setBasePrice(request.getPrice());

        productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        productRepository.delete(product);
    }

    @Override
    public ProductDtoResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        return productMapper.toDto(product);
    }

    @Override
    public Page<ProductDtoResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toDto);
    }
}
