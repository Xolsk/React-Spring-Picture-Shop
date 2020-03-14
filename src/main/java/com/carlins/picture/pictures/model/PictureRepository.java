package com.carlins.picture.pictures.model;



import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PictureRepository extends CrudRepository<Picture,Long> {

	Collection<Picture> findByShopId(Long shopId);
	

}
