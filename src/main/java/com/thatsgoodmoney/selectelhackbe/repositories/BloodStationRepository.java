package com.thatsgoodmoney.selectelhackbe.repositories;

import com.thatsgoodmoney.selectelhackbe.domain.entities.BloodStationEntity;
import com.thatsgoodmoney.selectelhackbe.domain.entities.CityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodStationRepository extends CrudRepository<BloodStationEntity, Long>,
        PagingAndSortingRepository<BloodStationEntity, Long> {
}
