package com.example.demo.Controller;

import com.example.demo.Service.ProductService;
import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")

public class ProductController {
    @Autowired
    private ProductService service;
    @GetMapping("/product")
   public ResponseEntity<List<Product>> getProduct(){
       return new ResponseEntity<>(service.getProduct(), OK);

   }
   @GetMapping("/product/{id}")
   public ResponseEntity<Product> getProdById(@PathVariable int id ){
       Product product=service.getProductById(id);
       if (product!=null){
           return new ResponseEntity<>(product, OK);
       }
       else
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }
   @PostMapping("/product")
   public ResponseEntity<?> addProduct(@RequestPart Product product,@RequestPart MultipartFile imageFile){
       try {
           Product prod=service.addProduct(product,imageFile);
           return new ResponseEntity<>(product,HttpStatus.CREATED);
       } catch (IOException e) {
           return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
       }


   }
    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImageByProdId(@PathVariable int id ){
        Product product=service.getProductById(id);
        byte[] data=product.getImageDate();
        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(data);
    }
    @PutMapping("/product/{id}/image")
    public ResponseEntity<String>updateProduct(@PathVariable int id,@RequestPart Product product,@RequestPart  MultipartFile imagefile){
        try {
            Product product1=service.updateProduct(id,product,imagefile);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (product!=null){
            return new ResponseEntity<>("Updated", OK);
        }
        else
            return new ResponseEntity<>("filed update",HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product=service.getProductById(id);
        if (product!=null){
            service.deleteProduct(id);
            return new ResponseEntity<>("product deleted ", OK);
        }
        else
            return new ResponseEntity<>("product not found",HttpStatus.NOT_FOUND);
    }


}
