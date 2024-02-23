package com.thatsgoodmoney.selectelhackbe.repositories;

import com.thatsgoodmoney.selectelhackbe.domain.entities.DonationEntity;
import com.thatsgoodmoney.selectelhackbe.domain.entities.DonationPlanEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationPlanRepository extends CrudRepository<DonationPlanEntity, Long>,
        PagingAndSortingRepository<DonationPlanEntity, Long> {
}
