package com.blogging.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogging.app.entities.Category;
import com.blogging.app.exceptions.ResourceNotFoundException;
import com.blogging.app.payloads.CategoryDTO;
import com.blogging.app.repositories.CategoryRepo;
import com.blogging.app.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryrepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDTO createCategory(CategoryDTO ctdo) {
		Category ctg = this.modelMapper.map(ctdo, Category.class);
		Category addCategory = this.categoryrepo.save(ctg);
		return this.modelMapper.map(addCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO ctdo, Integer cid) {
		Category ctg = this.categoryrepo.findById(cid)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", cid));

		ctg.setTitle(ctdo.getTitle());
		ctg.setCategoryDescription(ctdo.getCategoryDescription());

		Category updatedctg = this.categoryrepo.save(ctg);

		return this.modelMapper.map(updatedctg, CategoryDTO.class);
	}

	@Override
	public void deleteCategory(Integer cid) {
		Category ctg = this.categoryrepo.findById(cid)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", cid));
		
		this.categoryrepo.delete(ctg);

	}

	@Override
	public CategoryDTO getCategory(Integer cid) {
		Category ctg = this.categoryrepo.findById(cid)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", cid));
		return this.modelMapper.map(ctg, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getAllCategory() {
	  List<Category> allCategory = this.categoryrepo.findAll();
	  List<CategoryDTO> allcategorydto = allCategory.stream().map((cat) -> this.modelMapper.map(cat, CategoryDTO.class)).collect(Collectors.toList());
	  return allcategorydto;
	}

}
