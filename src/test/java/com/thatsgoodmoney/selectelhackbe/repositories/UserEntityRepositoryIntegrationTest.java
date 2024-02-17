package com.thatsgoodmoney.selectelhackbe.repositories;

import com.thatsgoodmoney.selectelhackbe.TestDataUtil;
import com.thatsgoodmoney.selectelhackbe.domain.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserEntityRepositoryIntegrationTest {
    private final UserRepository underTest;

    @Autowired
    public UserEntityRepositoryIntegrationTest(UserRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    void testThatUserCanBeCreatedAndRecalled() {
        UserEntity userEntity = TestDataUtil.createTestUser();
        underTest.save(userEntity);
        Optional<UserEntity> result = underTest.findById(userEntity.getUserId());
        assertThat(result).isPresent().contains(userEntity);
    }

    @Test
    void testThatMultipleUsersCanBeCreatedAndRecalled() {
        List<UserEntity> userEntities = TestDataUtil.createTestUsers();
        underTest.saveAll(userEntities);

        Iterable<UserEntity> result = underTest.findAll();
        assertThat(result).hasSize(3).containsExactlyElementsOf(userEntities);
    }

    @Test
    void testThatUserCanBeUpdated() {
        UserEntity userEntity = TestDataUtil.createTestUser();
        underTest.save(userEntity);
        userEntity.setLogin("second-punch");
        underTest.save(userEntity);
        Optional<UserEntity> result = underTest.findById(userEntity.getUserId());
        assertThat(result).isPresent().contains(userEntity);
    }

    @Test
    void testThatUserCanBeDeleted() {
        UserEntity userEntity = TestDataUtil.createTestUser();
        underTest.save(userEntity);
        underTest.deleteById(userEntity.getUserId());
        Optional<UserEntity> result = underTest.findById(userEntity.getUserId());
        assertThat(result).isEmpty();
    }
}
