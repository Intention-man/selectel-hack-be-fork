package com.thatsgoodmoney.selectelhackbe.repositories;

import com.thatsgoodmoney.selectelhackbe.domain.entities.DonationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DonationRepository extends CrudRepository<DonationEntity, Long>,
        PagingAndSortingRepository<DonationEntity, Long> {
}