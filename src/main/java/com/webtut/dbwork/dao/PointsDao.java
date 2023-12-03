package com.webtut.dbwork.dao;

import com.webtut.dbwork.domain.Point;
import com.webtut.dbwork.domain.User;

import java.util.List;
import java.util.Optional;

public interface PointsDao {

    void create(Point point);

    Optional<Point> findOne(long l);

    List<Point> find();

    void update(Long id, Point point);

    void delete(Long id);
}
