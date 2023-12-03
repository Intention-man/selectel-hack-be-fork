package com.webtut.dbwork.dao.impl;

import com.webtut.dbwork.TestDataUtil;
import com.webtut.dbwork.dao.UsersDao;
import com.webtut.dbwork.domain.Point;
import com.webtut.dbwork.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PointsDaoImplIntegrationTest {

    private final PointsDaoImpl underTest;
    private final UsersDao usersDao;

    @Autowired
    public PointsDaoImplIntegrationTest(PointsDaoImpl underTest, UsersDao usersDao){
        this.underTest = underTest;
        this.usersDao = usersDao;
    }

    @Test
    public void testThatPointCanBeCreatedAndRecalled(){
        User user = TestDataUtil.createTestUser();
        usersDao.create(user);
        Point point = TestDataUtil.createTestPoint();
        point.setUserId(user.getUserId());
        underTest.create(point);
        Optional<Point> result = underTest.findOne(point.getPointId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(point);
    }

    @Test
    public void testThatMultiplePointsCanBeCreatedAndRecalled(){
        User user = TestDataUtil.createTestUser();
        usersDao.create(user);


        List<Point> points = TestDataUtil.createTestPoints();
        points.forEach(underTest::create);

        List<Point> result = underTest.find();
        Assertions.assertThat(result).hasSize(3).containsExactlyElementsOf(points);
    }

    @Test
    public void testThatBookCanBeUpdated() {
        User user = TestDataUtil.createTestUser();
        usersDao.create(user);

        Point point = TestDataUtil.createTestPoint();
        point.setUserId(user.getUserId());
        underTest.create(point);

        point.setX(0);
        underTest.update(point.getPointId(), point);

        Optional<Point> result = underTest.findOne(point.getPointId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(point);
    }

    @Test
    public void testThatBookCanBeDeleted() {
        User user = TestDataUtil.createTestUser();
        usersDao.create(user);

        Point point = TestDataUtil.createTestPoint();
        point.setUserId(user.getUserId());
        underTest.create(point);

        underTest.delete(point.getPointId());

        Optional<Point> result = underTest.findOne(point.getPointId());
        assertThat(result).isEmpty();
    }
}
