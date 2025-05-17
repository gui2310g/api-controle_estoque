package com.example.api.domain.services;

import com.example.api.common.components.AuthHelper;
import com.example.api.common.components.GenerateSku;
import com.example.api.common.components.PaginationType;
import com.example.api.domain.entities.Categories;
import com.example.api.domain.entities.Products;
import com.example.api.domain.entities.Users;
import com.example.api.domain.exceptions.ResourceAccessDeniedException;
import com.example.api.domain.exceptions.ResourceNotFoundException;
import com.example.api.domain.mappers.ProductMapper;
import com.example.api.domain.repositories.CategoryRepository;
import com.example.api.domain.repositories.ProductRepository;
import com.example.api.domain.repositories.UserRepository;
import com.example.api.dto.product.ProductRequestDto;
import com.example.api.dto.product.ProductResponseDto;
import com.example.api.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final PaginationType<Products, ProductResponseDto> paginationType;
    private final ProductMapper productMapper;
    private final AuthHelper authHelper;

    public List<ProductResponseDto> findAll() {
        return productRepository.findAll().stream().map(productMapper::toDto).toList();
    }

    public Page<ProductResponseDto> findAllByPage(int page, int size) {
        Page<Products> produtoPage = productRepository.findAll(PageRequest.of(page, size));
        return paginationType.findAll(produtoPage, page, size, productMapper::toDto);
    }

    public ProductResponseDto findById(Long id) {
        return productRepository.findById(id).map(productMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado da id " + id));
    }

    public ProductResponseDto create(ProductRequestDto dto) {
        Categories category = categoryRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrado"));

        Users users = authHelper.getAuthenticatedUser();
        Products product = productMapper.toEntity(dto);

        if(product.getEstoque_atual() == 0 || product.getEstoque_minimo() == 0)
            throw new ResourceNotFoundException("Estoque atual ou estoque mínimo não podem ser zero");

        if(authHelper.isAdmin()) throw new ResourceAccessDeniedException("Somente o usuario pode criar o produto");

        product.setCategoria(category);
        product.setSku(GenerateSku.generateSku(dto.getNome()));
        product.setUsuario(users);
        return productMapper.toDto(productRepository.save(product));
    }

    public ProductResponseDto update(Long id, ProductRequestDto dto, Long userId) {
        findById(id);

        Products product = productMapper.toEntity(dto);

        if (!product.getUsuario().getId().equals(userId))
            throw new ResourceAccessDeniedException("Usuário pode atualizar somente o seu produto proprio");

        product.setId(id);
        return productMapper.toDto(productRepository.save(product));
    }

    public void delete(Long id) {
        findById(id);
        productRepository.deleteById(id);
    }
}
