package com.example.api.domain.services;

import com.example.api.common.components.GenerateSku;
import com.example.api.common.components.PaginationType;
import com.example.api.domain.entities.Categories;
import com.example.api.domain.entities.Products;
import com.example.api.domain.exceptions.ResourceNotFoundException;
import com.example.api.domain.mappers.ProductMapper;
import com.example.api.domain.repositories.CategoryRepository;
import com.example.api.domain.repositories.ProductRepository;
import com.example.api.dto.product.ProductRequestDto;
import com.example.api.dto.product.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final PaginationType<Products, ProductResponseDto> paginationType;
    private final ProductMapper productMapper;

    public List<ProductResponseDto> findAll() {
        return productRepository.findAll().stream().map(productMapper::toDto).toList();
    }

    public Page<ProductResponseDto> findAllByPage(int page, int size) {
        Page<Products> produtoPage = productRepository.findAll(PageRequest.of(page, size));
        return paginationType.findAll(produtoPage, page, size, productMapper::toDto);
    }

    public ProductResponseDto findById(Long id) {
        return productRepository.findById(id).map(productMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public ProductResponseDto create(ProductRequestDto dto) {
        Categories category = categoryRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Products product = productMapper.toEntity(dto);

        if(product.getEstoque_atual() == 0 || product.getEstoque_minimo() == 0)
            throw new ResourceNotFoundException("Estoque atual ou estoque mínimo não podem ser zero");

        product.setCategoria(category);
        product.setSku(GenerateSku.generateSku(dto.getNome()));
        return productMapper.toDto(productRepository.save(product));
    }

    public ProductResponseDto update(Long id, ProductRequestDto dto) {
        findById(id);
        Products product = productMapper.toEntity(dto);
        product.setId(id);
        return productMapper.toDto(productRepository.save(product));
    }

    public void delete(Long id) {
        findById(id);
        productRepository.deleteById(id);
    }
}
