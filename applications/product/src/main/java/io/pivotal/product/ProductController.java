package io.pivotal.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

  private ProductRepository productRepository;

  public ProductController(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @PostMapping
  public ResponseEntity<Product> create(
      @RequestParam("name") String name,
      @RequestParam("description") String description,
      @RequestParam("imgUrl") String imgUrl,
      @RequestParam("price") double price) {

    Product resp = productRepository.save(new Product(name, description, imgUrl, price));
    if(resp != null){
      return new ResponseEntity<>(resp, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping
  public ResponseEntity<Product> update(@RequestParam("id") Long id,
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "description", required = false) String description,
      @RequestParam(value = "imgUrl",required = false) String imgUrl,
      @RequestParam(value = "price", required = false) double price) {

    Product resp = productRepository.findOne(id);
    if(name != null){
      resp.setName(name);
    }
    if(description != null){
      resp.setDescription(description);
    }
    if(imgUrl != null){
      resp.setImageUrl(imgUrl);
    }
    if(price > 0){
      resp.setPrice(price);
    }
    return new ResponseEntity<>(productRepository.save(resp), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getById(@PathVariable("id") Long id) {
    Product resp = productRepository.findOne(id);
    if(resp != null){
      return new ResponseEntity<>(resp, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/{id}/name")
  String getNameByProductId(@PathVariable("id")Long id){
    Product resp = productRepository.findOne(id);
    if(resp != null){
      return resp.getName();
    } else {
      return null;
    }
  }

  @GetMapping("/{id}/price")
  Double getPriceByProductId(@PathVariable("id")Long id){
    Product resp = productRepository.findOne(id);
    if(resp != null){
      return resp.getPrice();
    } else {
      return -1d;
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable("id") Long id) {
    try{
      productRepository.delete(id);
    } catch (Exception e){
      return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }
    return new ResponseEntity(HttpStatus.OK);
  }
}
