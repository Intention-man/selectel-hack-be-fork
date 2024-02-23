package com.thatsgoodmoney.selectelhackbe.repositories;

import com.thatsgoodmoney.selectelhackbe.domain.entities.CityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends CrudRepository<CityEntity, Long>,
        PagingAndSortingRepository<CityEntity, Long> {

    Optional<CityEntity> findByLatAndLng(String lat, String lng);
}
