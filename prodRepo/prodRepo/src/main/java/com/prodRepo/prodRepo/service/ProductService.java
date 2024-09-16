package com.prodRepo.prodRepo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.prodRepo.prodRepo.repository.ProductRepo;
import com.prodRepo.prodRepo.model.Product;

@Service
public class ProductService {
  @Autowired
  ProductRepo repo;

  public List<Product> getProducts() {
    return repo.findAll();
  }

  public Product getProduct(int id) {
    return repo.findById(id).orElse(null);
  }

  public Product addProduct(Product product, MultipartFile file) throws IOException {
    product.setProdImageName(file.getOriginalFilename());
    product.setProdImageType(file.getContentType());
    product.setProdImageData(file.getBytes());
    return repo.save(product);
  }

  public Product updateProduct(Product product, MultipartFile file) throws IOException {
    product.setProdImageName(file.getOriginalFilename());
    product.setProdImageType(file.getContentType());
    product.setProdImageData(file.getBytes());
    return repo.save(product);
  }

  public void deleteProduct(int id) {
    repo.deleteById(id);
  }

}
