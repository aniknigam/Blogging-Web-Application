package com.blogging.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogging.app.payloads.ApiResponse;
import com.blogging.app.payloads.CategoryDTO;
import com.blogging.app.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
	private CategoryService categoryservice;
    
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('USER_ROLES') or hasAuthority('ADMIN_ROLES')")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryBody) {
    	CategoryDTO ctg = this.categoryservice.createCategory(categoryBody);
    	return new ResponseEntity<CategoryDTO>(ctg,HttpStatus.CREATED);
    }
    
    @PutMapping("/update/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryBody, @PathVariable("categoryId")Integer cid) {
    	CategoryDTO ctg = this.categoryservice.updateCategory(categoryBody, cid);
    	return new ResponseEntity<CategoryDTO>(ctg, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/delete/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
    	this.categoryservice.deleteCategory(categoryId);
    	return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted Successfully",true,"You can create a new catergory"), HttpStatus.OK);
    }
    
    @GetMapping("/getCategory/{categoryId}")
    @PreAuthorize("hasAuthority('USER_ROLES') or hasAuthority('ADMIN_ROLES')")
    public ResponseEntity<CategoryDTO> getSingleCategory(@PathVariable Integer categoryId){
    	CategoryDTO ctg = this.categoryservice.getCategory(categoryId);
    	return new ResponseEntity<CategoryDTO>(ctg,HttpStatus.OK);
    }
    
    @GetMapping("/getAllCategory")
    @PreAuthorize("hasAuthority('USER_ROLES') or hasAuthority('ADMIN_ROLES')")
    public ResponseEntity<List<CategoryDTO>> getAllCategory(){
    	List<CategoryDTO> ctg = this.categoryservice.getAllCategory();
    	return ResponseEntity.ok(ctg);
    }

}
