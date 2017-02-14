package org.yawlfoundation.admin.Utils;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.repository.CrudRepository;
import javax.cache.annotation.CacheResult;
import java.util.List;

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

    @CacheResult
    public T getObjectById(Long id){
        return (T) repository.findOne(id);
    }




}
