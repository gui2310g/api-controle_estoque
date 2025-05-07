package com.example.api;

import com.example.api.domain.entities.Categories;
import com.example.api.domain.entities.Products;
import com.example.api.domain.mappers.CategoryMapper;
import com.example.api.domain.mappers.ProductMapper;
import com.example.api.domain.repositories.CategoryRepository;
import com.example.api.domain.repositories.ProductRepository;
import com.example.api.domain.services.CategoryService;
import com.example.api.domain.services.ProductService;
import com.example.api.dto.category.CategoryResponseDto;
import com.example.api.dto.product.ProductDetailsDto;
import com.example.api.dto.product.ProductResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    private ProductRepository produtoRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService produtoService;

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private CategoryMapper categoryMapper;

    @Test
    void RetornarProdutoComId() {
        Products produtoMock = new Products(1L, "Camisa", "Camisa de algodão", 29, 11, 12, null, null);
        ProductResponseDto response = new ProductResponseDto(1L, "Camisa", "Camisa de algodão", 29, 11, 12, null, null);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoMock));
        when(productMapper.toDto(produtoMock)).thenReturn(response);

        ProductResponseDto result = productMapper.toDto(produtoMock);

        produtoService.findById(result.getId());

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Camisa", result.getNome());

        verify(produtoRepository).findById(1L);
    }

    @Test
    void ExceptionSeIdNaoEistir() {
        when(produtoRepository.findById(2L)).thenReturn(Optional.empty());

        Exception excecao = assertThrows(RuntimeException.class, () -> {
            produtoService.findById(2L);
        });

        assertEquals("Produto não encontrado da id 2", excecao.getMessage());
    }

    @Test
    void RetornarCategoria() {
        Products produtoMock = new Products(1L, "Camisa", "Camisa de algodão", 29, 11, 12, null, null);
        ProductDetailsDto details = new ProductDetailsDto(1L, "Camisa", "Camisa de algodão", 29, 11, 12, null);

        Categories categoriaMock = new Categories(1L, "Roupas", List.of(produtoMock));
        CategoryResponseDto response = new CategoryResponseDto(1L, "Roupas", List.of(details));
        when(categoryMapper.toDto(categoriaMock)).thenReturn(response);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoriaMock));

        CategoryResponseDto result = categoryService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Roupas", result.getNome());
        assertEquals(1, result.getProdutos().size());
        assertEquals(details, result.getProdutos().getFirst());

        verify(categoryRepository).findById(1L);
    }
}