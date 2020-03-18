package com.carlins.picture.pictures.model;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ShopController {
	
	 private ShopRepository shopRepository;
	 private PictureRepository pictureRepository;
	 String ErrorMessage = "";
	 
	 public ShopController (ShopRepository shopRepository, PictureRepository pictureRepository) {
		 
		 this.shopRepository=shopRepository;
		 this.pictureRepository=pictureRepository;
	 }

	    
	    @GetMapping("/shops/{shopID}/pictures")
	    Collection<Picture> picturesByGallery (@PathVariable (value="shopID") String shopId) {
			return (Collection<Picture>) pictureRepository.findByShopId(Long.parseLong(shopId));
				
		}
	    
	    @PostMapping("/shops/{shopID}/pictures")
	    ResponseEntity<String>  addPicture (@PathVariable (value="shopID") String shopId,@Valid @RequestBody Picture raw) {
	      
	    	Picture picture = new Picture(Long.parseLong(shopId),raw.title,raw.author,raw.price);
	    	Optional<Shop> shop= shopRepository.findById(Long.parseLong(shopId));
	    	if (shop.isPresent() && shop.get().currentCapacity+1<=shop.get().maxCapacity) {
	    		shop.get().currentCapacity++;
	    		pictureRepository.save(picture);
	    	return ResponseEntity.ok().body("Picture successfully added");
	    	}
	    	else if (shop.isPresent()==false) ErrorMessage="No shop with the Id provided.";
	    	else ErrorMessage="Current shop reached Max Capacity";
	    	return ResponseEntity.badRequest().body("Error: " + ErrorMessage);
					
		}
	    
	    @DeleteMapping("/shops/{shopID}/pictures")
	    ResponseEntity<String> eraseStock(@PathVariable (value="shopID") String shopId){
	    	
	    	Optional<Shop> shop= shopRepository.findById(Long.parseLong(shopId));
	    	if (shop.isPresent()) {
	    		shop.get().currentCapacity=0;
	    		Collection<Picture> toDelete = pictureRepository.findByShopId((Long.parseLong(shopId)));
	    		pictureRepository.deleteAll(toDelete);
	    		return ResponseEntity.ok().body("All deleted for " + shop.get().name);
	    		
	    	}
	    	
		
			return ResponseEntity.badRequest().body("Did not find matching Shop by that ID");
	    	
	    	
	    }


		@GetMapping("/shops")
	    Collection<Shop> shops() {
	        return (Collection<Shop>) shopRepository.findAll();
	    }

	    @PostMapping("/shops")
	    ResponseEntity<Shop> createShop(@Valid @RequestBody Shop shop) throws URISyntaxException {
	        Shop result = shopRepository.save(shop);
	        return ResponseEntity.ok().body(result);
	    }
	    
	    
	    


}
