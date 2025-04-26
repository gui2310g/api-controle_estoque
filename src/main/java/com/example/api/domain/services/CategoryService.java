package com.example.api.domain.services;

import com.example.api.common.PaginationType;
import com.example.api.domain.entities.Categories;
import com.example.api.domain.exceptions.ResourceBadRequestException;
import com.example.api.domain.exceptions.ResourceNotFoundException;
import com.example.api.domain.mappers.CategoryMapper;
import com.example.api.domain.repositories.CategoryRepository;
import com.example.api.dto.category.CategoryRequestDto;
import com.example.api.dto.category.CategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final PaginationType<Categories, CategoryResponseDto> paginationType;

    public CategoryResponseDto create(CategoryRequestDto dto) {
        if(categoryRepository.findByNome(dto.getNome()).isPresent())
            throw new ResourceNotFoundException("Categoria ja existente");

        Categories categoria = categoryMapper.toEntity(dto);
        return categoryMapper.toDto(categoryRepository.save(categoria));
    }

    public Page<CategoryResponseDto> findAllByPage(int page, int size) {
        Page<Categories> categoriaPage = categoryRepository.findAll(PageRequest.of(page, size));
        return paginationType.findAll(categoriaPage, page, size, categoryMapper::toDto);
    }

    public List<CategoryResponseDto> findAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).toList();
    }

    public CategoryResponseDto findById(Long id) {
        return categoryRepository.findById(id).map(categoryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Nao foi encontrado categoria com id " + id));
    }

    public CategoryResponseDto update(Long id, CategoryRequestDto dto) {
        if(categoryRepository.findByNome(dto.getNome()).isPresent())
            throw new ResourceNotFoundException("Categoria ja existente");

        findById(id);
        Categories categoria = categoryMapper.toEntity(dto);
        categoria.setId(id);
        return categoryMapper.toDto(categoryRepository.save(categoria));
    }

    public void delete(Long id) {
        Categories categories = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nao foi encontrado categoria com id " + id));

        if(!categories.getProdutos().isEmpty())
            throw new ResourceBadRequestException("Nao se pode deletar uma categoria com produtos associados");

        categoryRepository.deleteById(id);
    }
}
