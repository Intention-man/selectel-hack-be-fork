package com.thatsgoodmoney.selectelhackbe.repositories;

import com.thatsgoodmoney.selectelhackbe.domain.entities.PointEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PointRepository extends CrudRepository<PointEntity, Long>,
        PagingAndSortingRepository<PointEntity, Long> {

    @Query("SELECT p from PointEntity p where p.inside = true")
    Iterable<PointEntity> findAllInside();

}