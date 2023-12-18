package com.webtut.dbwork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webtut.dbwork.TestDataUtil;
import com.webtut.dbwork.domain.dto.PointDto;
import com.webtut.dbwork.domain.entities.PointEntity;
import com.webtut.dbwork.domain.entities.UserEntity;
import com.webtut.dbwork.mappers.Mapper;
import com.webtut.dbwork.services.PointService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class PointControllerIntegrationTest {

    private final Mapper<PointEntity, PointDto> pointMapper;
    private final PointService pointService;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public PointControllerIntegrationTest(PointService pointService, Mapper<PointEntity, PointDto> pointMapper, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.pointService = pointService;
        this.pointMapper = pointMapper;
    }

    @Test
    void testThatCreatePointSuccessfullyReturnsHttp201Created() throws Exception {
        UserEntity testUser = TestDataUtil.createTestUser();

        PointEntity testPoint = TestDataUtil.createTestPointEntity(testUser);
        testPoint.setPointId(null);
        String pointJson = objectMapper.writeValueAsString(testPoint);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/points")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pointJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    void testThatCreatePointSuccessfullyReturnsSavedPoint() throws Exception {
        UserEntity testUser = TestDataUtil.createTestUser();

        PointEntity testPoint = TestDataUtil.createTestPointEntity(testUser);
        testPoint.setPointId(null);
        String pointJson = objectMapper.writeValueAsString(testPoint);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/points")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pointJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.pointId").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.x").value(testPoint.getX())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.y").value(testPoint.getY())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.r").value(testPoint.getR())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.inside").value(testPoint.isInside())
        );
    }

    @Test
    void testThatListPointsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/points")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    void testThatListPointsReturnsListOfPoints() throws Exception {
        UserEntity testUser = TestDataUtil.createTestUser();
        PointEntity testPoint = TestDataUtil.createTestPointEntity(testUser);
        pointService.save(testPoint);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/points")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].pointId").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].x").value(testPoint.getX())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].y").value(testPoint.getY())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].r").value(testPoint.getR())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].inside").value(testPoint.isInside())
        );
    }

    @Test
   void testThatGetPointReturnsHttpStatus200WhenPointExists() throws Exception {
        UserEntity testUser = TestDataUtil.createTestUser();
        PointEntity testPoint = TestDataUtil.createTestPointEntity(testUser);
        pointService.save(testPoint);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/points/" + testPoint.getPointId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    void testThatGetPointReturnsPointWhenPointExists() throws Exception {
        UserEntity testUser = TestDataUtil.createTestUser();
        PointEntity testPoint = TestDataUtil.createTestPointEntity(testUser);
        pointService.save(testPoint);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/points/" + testPoint.getPointId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.pointId").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.x").value(testPoint.getX())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.y").value(testPoint.getY())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.r").value(testPoint.getR())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.inside").value(testPoint.isInside())
        );
    }

    @Test
    void testThatUpdatePointReturnsUpdatedPoint() throws Exception {
        UserEntity testUser = TestDataUtil.createTestUser();
        PointEntity testPoint = TestDataUtil.createTestPointEntity(testUser);
        PointEntity savedPointEntity = pointService.save(testPoint);

        PointDto newPointData = pointMapper.mapTo(TestDataUtil.createTestPoints(testUser).get(2));
        newPointData.setPointId(savedPointEntity.getPointId());
        String pointJson = objectMapper.writeValueAsString(newPointData);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/points/" + savedPointEntity.getPointId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pointJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.pointId").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.x").value(newPointData.getX())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.y").value(newPointData.getY())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.r").value(newPointData.getR())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.inside").value(newPointData.isInside())
        );
    }

    @Test
    void testThatDeleteExistingPointReturnsHttpStatus204NoContent() throws Exception {
        UserEntity testUser = TestDataUtil.createTestUser();
        PointEntity testPoint = TestDataUtil.createTestPointEntity(testUser);
        pointService.save(testPoint);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/points/" + testPoint.getPointId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}