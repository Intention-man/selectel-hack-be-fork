package com.webtut.dbwork.dao.impl;


import com.webtut.dbwork.TestDataUtil;
import com.webtut.dbwork.domain.Point;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class PointsDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private PointsDaoImpl underTest;

    @Test
    public void testThatCreatePointGeneratesCorrectSql(){
        Point point = TestDataUtil.createTestPoint();

        underTest.create(point);

        verify(jdbcTemplate).update(eq("INSERT INTO points (point_id, x, y, r, user_id) values (default, ?, ?, ?, ?)"),
                eq(1d), eq(2.5d), eq(2d), eq(1L));
    }

    @Test
    public void testThatFindOneBookGeneratesTheCorrectSql(){
        underTest.findOne(1L);
        verify(jdbcTemplate).query(
                eq("SELECT point_id, x, y, r, user_id FROM points WHERE point_id = ? LIMIT 1"),
                ArgumentMatchers.<PointsDaoImpl.PointRowMapper>any(),
                eq(1L)
        );
    }

    @Test
    public void testThatUpdateGeneratesCorrectSql() {
        Point point = TestDataUtil.createTestPoint();
        underTest.update(1L, point);
        verify(jdbcTemplate).update(
                "UPDATE books SET x = ?, y = ?, r = ? WHERE point_id = ?",
                -2d, 2d, 2d, 1L
        );
    }

    @Test
    public void testThatDeleteGeneratesCorrectSql() {
        underTest.delete(1L);
        verify(jdbcTemplate).update(
                "DELETE FROM points where point_id = ?",
                1L
        );
    }
}
