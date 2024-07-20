package com.example.demo.Service;

import com.example.demo.ProductRepo.ProductRepo;
import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class ProductService {
    @Autowired
    private  ProductRepo repo;
    public List<Product> getProduct() {
        return repo.findAll();
    }

    public Product getProductById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());
        return repo.save(product);
    }

    public Product updateProduct(int id, Product product, MultipartFile imagefile) throws IOException {

        product.setImageDate(imagefile.getBytes());
        product.setImageType(imagefile.getContentType());
        product.setImageName(imagefile.getOriginalFilename());
       return repo.save(product);


    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }
}
