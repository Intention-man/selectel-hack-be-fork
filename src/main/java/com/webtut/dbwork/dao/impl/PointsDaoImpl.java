package com.webtut.dbwork.dao.impl;

import com.webtut.dbwork.dao.PointsDao;
import com.webtut.dbwork.domain.Point;
import com.webtut.dbwork.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class PointsDaoImpl implements PointsDao {
    private final JdbcTemplate jdbcTemplate;

    public PointsDaoImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Point point) {
        jdbcTemplate.update("INSERT INTO points (point_id, x, y, r, user_id) values (default, ?, ?, ?, ?)",
                point.getX(), point.getY(), point.getR(), point.getUserId()
                );
    }

    @Override
    public Optional<Point> findOne(long point_id) {
        List<Point> results = jdbcTemplate.query("SELECT point_id, x, y, r, user_id FROM points WHERE point_id = ? LIMIT 1", new PointRowMapper(), point_id);
        return results.stream().findFirst();
    }
    @Override
    public List<Point> find() {
        return jdbcTemplate.query("SELECT point_id, x, y, r, user_id FROM points", new PointsDaoImpl.PointRowMapper());
    }

    @Override
    public void update(Long id, Point point) {
        jdbcTemplate.update(
                "UPDATE points SET x = ?, y = ?, r = ? WHERE point_id = ?",
                point.getX(), point.getY(), point.getR(), id
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(
                "DELETE FROM points where point_id = ?",
                id
        );
    }

    public static class PointRowMapper implements RowMapper<Point> {
        @Override
        public Point mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Point.builder()
                    .pointId(rs.getLong("point_id"))
                    .x(rs.getDouble("x"))
                    .y(rs.getDouble("y"))
                    .r(rs.getDouble("r"))
                    .userId(rs.getLong("user_id"))
                    .build();
        }
    }
}