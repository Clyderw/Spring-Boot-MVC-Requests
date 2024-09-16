package com.prodRepo.prodRepo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.prodRepo.prodRepo.model.Product;
import com.prodRepo.prodRepo.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
  @Autowired
  ProductService service;

  @GetMapping("/products")
  ResponseEntity<?> getProducts() {
    List<Product> prodList = service.getProducts();
    if (prodList.size() != 0) {
      return new ResponseEntity<>(prodList, HttpStatus.OK);
    } else {
      return new ResponseEntity<>("List is empty, please, add a product to list", HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/products/{id}")
  ResponseEntity<?> getProduct(@PathVariable int id) {
    Product product = service.getProduct(id);
    Integer productAmount = service.getProducts().size();
    if (product != null) {
      return new ResponseEntity<>(product, HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Product doesn't exist!." + " Amount of products - " + productAmount,
          HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/product/{id}/image")
  ResponseEntity<?> getProductImage(@PathVariable int id) {
    Product product = service.getProduct(id);
    if (product == null) {
      return new ResponseEntity<>("This product doesn't exist!", HttpStatus.BAD_REQUEST);
    }
    String imageType = product.getProdImageType();
    byte[] imageData = product.getProdImageData();
    return ResponseEntity.ok().contentType(MediaType.valueOf(imageType)).body(imageData);
  }

  @PostMapping("/products")
  ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile file) {
    if (!file.getContentType().startsWith("image/")) {
      return new ResponseEntity<>("Incorrect file type, please, enter image", HttpStatus.BAD_REQUEST);
    }
    try {
      Product product1 = service.addProduct(product, file);
      return new ResponseEntity<>(product1, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping("/products/{id}")
  ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestPart Product product,
      @RequestPart MultipartFile file) {
    if (!file.getContentType().startsWith("image/")) {
      return new ResponseEntity<>("Incorrect file type, please, enter image", HttpStatus.BAD_REQUEST);
    }
    Product product1 = service.getProduct(id);
    if (product1 == null) {
      return new ResponseEntity<>("This product don't exist", HttpStatus.BAD_REQUEST);
    }
    product.setProdId(id);
    try {
      Product product2 = service.updateProduct(product, file);
      return new ResponseEntity<>(product2, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/products/{id}")
  ResponseEntity<?> deleteProductById(@PathVariable int id) {
    Product product = service.getProduct(id);
    Integer prodAmount = service.getProducts().size();
    if (product != null) {
      service.deleteProduct(id);
      return new ResponseEntity<>(product, HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Unfortunately, this product doesn't exist, " + "amount of products: " + prodAmount,
          HttpStatus.BAD_REQUEST);
    }
  }
}