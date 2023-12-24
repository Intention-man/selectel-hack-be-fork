package com.webtut.dbwork;

import com.webtut.dbwork.domain.entities.PointEntity;
import com.webtut.dbwork.domain.entities.UserEntity;

import java.util.ArrayList;
import java.util.List;

public final class TestDataUtil {
    private TestDataUtil(){
    }

    public static UserEntity createTestUser() {
        return buildUser(1L, "correct-user", "123456");
    }

    public static List<UserEntity> createTestUsers() {
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(buildUser(1L, "cool-fighter", "123"));
        userEntities.add(buildUser(2L, "second-breath", "456"));
        userEntities.add(buildUser(3L, "third-chance", "789"));
        return userEntities;
    }

    public static UserEntity buildUser(Long userId, String login, String password) {
        return UserEntity.builder()
                .userId(userId)
                .login(login)
                .password(password)
                .build();
    }

    public static PointEntity createTestPointEntity(final UserEntity userEntity) {
        return buildPointEntity(1L, -2d, 2d, 2d, userEntity);
    }


    public static List<PointEntity> createTestPoints(final UserEntity userEntity) {
        List<PointEntity> pointEntities = new ArrayList<>();
        pointEntities.add(buildPointEntity(1L, 1d, 2.5d, 2d, userEntity));
        pointEntities.add(buildPointEntity(2L, 0d, 0d, 1d, userEntity));
        pointEntities.add(buildPointEntity(3L, -4d, 4.9d, 1.5d, userEntity));
        return pointEntities;
    }

    public static PointEntity buildPointEntity(Long pointId, Double x, Double y, Double r, UserEntity userEntity) {
        return PointEntity.builder()
                .pointId(pointId)
                .x(x)
                .y(y)
                .r(r)
                .inside(isInside(x, y, r))
                .userId(userEntity.getUserId())
                .build();
    }


    public static boolean isInside(double x, double y, double r){
        boolean p1 = x >= 0 && y >= 0 && (x + y) <= r/2;
        boolean p2 = x <= 0 && y >= 0 && x >= -r && y <= r;
        boolean p3 = x <= 0 && y <= 0 && (x * x + y * y) <= (r * r / 4);
        return p1 || p2 || p3;
    }
}
