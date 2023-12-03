package com.webtut.dbwork;

import com.webtut.dbwork.domain.Point;
import com.webtut.dbwork.domain.User;

import java.util.ArrayList;
import java.util.List;

public final class TestDataUtil {
    private TestDataUtil(){
    }

    public static User createTestUser() {
        return buildUser(1L, "Cool fighter", "123");
    }

    public static List<User> createTestUsers() {
        List<User> users = new ArrayList<>();

        users.add(buildUser(1L, "cool-fighter", "123"));
        users.add(buildUser(2L, "second-breath", "456"));
        users.add(buildUser(3L, "third-chance", "789"));

        return users;
    }

    public static User buildUser(Long userId, String login, String password) {
        return User.builder()
                .userId(userId)
                .login(login)
                .password(password)
                .build();
    }

    public static Point createTestPoint() {
        return buildPoint(1L, -2d, 2d, 2d, 1L);
    }

    public static List<Point> createTestPoints() {
        List<Point> points = new ArrayList<>();

        points.add(buildPoint(1L, 1d, 2.5d, 2d, 1L));
        points.add(buildPoint(2L, 0d, 0d, 1d, 1L));
        points.add(buildPoint(3L, -4d, 4.9d, 1.5d, 1L));

        return points;
    }

    public static Point buildPoint(Long pointId, Double x, Double y, Double r, Long userId) {
        return Point.builder()
                .pointId(pointId)
                .x(x)
                .y(y)
                .r(r)
                .userId(userId)
                .build();
    }
}
