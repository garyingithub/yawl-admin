package org.yawlfoundation.admin.util;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import javax.cache.annotation.CacheResult;

/**
 * Created by root on 17-2-10.
 */


public class BaseUtil<T> {
    
    protected CrudRepository repository;



    public Iterable getAllObjects(){
        return repository.findAll();
    }



    public void storeObject(T t){
        repository.save(t);
    }



    public T getObjectById(Long id){
        return (T) repository.findOne(id);
    }




}
