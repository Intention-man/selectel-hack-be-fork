package com.thatsgoodmoney.selectelhackbe.repositories;

import com.thatsgoodmoney.selectelhackbe.TestDataUtil;
import com.thatsgoodmoney.selectelhackbe.domain.entities.PointEntity;
import com.thatsgoodmoney.selectelhackbe.domain.entities.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PointEntityRepositoryIntegrationTest {

    private final PointRepository underTest;

    @Autowired
    public PointEntityRepositoryIntegrationTest(PointRepository underTest){
        this.underTest = underTest;
    }

    @Test
    void testThatPointCanBeCreatedAndRecalled(){
        UserEntity userEntity = TestDataUtil.createTestUser();
        PointEntity pointEntity = TestDataUtil.createTestPointEntity(userEntity);
        underTest.save(pointEntity);
        Optional<PointEntity> result = underTest.findById(pointEntity.getPointId());
        assertThat(result).isPresent().contains(pointEntity);
    }

    @Test
    void testThatMultiplePointsCanBeCreatedAndRecalled(){
        UserEntity userEntity = TestDataUtil.createTestUser();

        List<PointEntity> pointEntities = TestDataUtil.createTestPoints(userEntity);
        underTest.saveAll(pointEntities);

        Iterable<PointEntity> result = underTest.findAll();
        Assertions.assertThat(result).hasSize(3).containsExactlyElementsOf(pointEntities);
    }

    @Test
    void testThatPointCanBeUpdated() {
        UserEntity userEntity = TestDataUtil.createTestUser();
        PointEntity pointEntity = TestDataUtil.createTestPointEntity(userEntity);
        underTest.save(pointEntity);
        pointEntity.setX(0);
        underTest.save(pointEntity);
        Optional<PointEntity> result = underTest.findById(pointEntity.getPointId());
        assertThat(result).isPresent().contains(pointEntity);
    }

    @Test
    void testThatPointCanBeDeleted() {
        UserEntity userEntity = TestDataUtil.createTestUser();

        PointEntity pointEntity = TestDataUtil.createTestPointEntity(userEntity);
        underTest.save(pointEntity);
        underTest.deleteById(pointEntity.getPointId());

        Optional<PointEntity> result = underTest.findById(pointEntity.getPointId());
        assertThat(result).isEmpty();
    }

    @Test
    void testThatPointsCanBeCreatedAndGetOnlyInside(){
        UserEntity userEntity = TestDataUtil.createTestUser();

        List<PointEntity> pointEntities = TestDataUtil.createTestPoints(userEntity);
        underTest.saveAll(pointEntities);
        List<PointEntity> pointsInside = pointEntities.stream().filter(PointEntity::isInside).collect(Collectors.toList());

        Iterable<PointEntity> result = underTest.findAllInside();
        Assertions.assertThat(result).hasSize(pointsInside.size()).containsExactlyElementsOf(pointsInside);
    }
}
