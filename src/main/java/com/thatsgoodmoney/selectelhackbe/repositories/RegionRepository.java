package com.thatsgoodmoney.selectelhackbe.repositories;

import com.thatsgoodmoney.selectelhackbe.domain.entities.RegionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity, Long>,
        PagingAndSortingRepository<RegionEntity, Long> {
}
