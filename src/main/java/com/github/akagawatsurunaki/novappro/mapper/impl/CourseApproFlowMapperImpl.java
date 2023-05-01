package com.github.akagawatsurunaki.novappro.mapper.impl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.mapper.CourseApproFlowMapper;
import com.github.akagawatsurunaki.novappro.model.database.approval.CourseApproFlow;
import com.github.akagawatsurunaki.novappro.util.EntityUtil;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.SQLException;
import java.util.List;

public class CourseApproFlowMapperImpl implements CourseApproFlowMapper {

    @Getter
    private final static CourseApproFlowMapper instance = new CourseApproFlowMapperImpl();

    @Override
    public Pair<VC.Mapper, CourseApproFlow> insert(@NonNull CourseApproFlow courseApproFlow) {
        try {
            int rows = Db.use().insert(EntityUtil.getEntity(courseApproFlow));
            if (rows > 0) {
                return new ImmutablePair<>(VC.Mapper.OK, courseApproFlow);
            }
            return new ImmutablePair<>(VC.Mapper.OTHER_EXCEPTION, courseApproFlow);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VC.Mapper.SQL_EXCEPTION, courseApproFlow);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VC.Mapper.OTHER_EXCEPTION, courseApproFlow);
        }
    }

    @Override
    public CourseApproFlow select(@NonNull String flowNo) throws SQLException {

        String sql = "SELECT * FROM crs_appro_flow WHERE crs_appro_flow.appro_flow_nos = ?";
        List<Entity> entities = Db.use().query(sql, flowNo);
        if (entities.size() == 1) {
            return EntityUtil.parseEntity(CourseApproFlow.class, entities.get(0));
        }
        return null;
    }

    @Override
    public Integer findMaxIdOfCourseApproFlow(@NonNull String flowNo) throws SQLException {

        var sql = """
                SELECT audit_flow_detail.id
                FROM audit_flow_detail
                WHERE audit_flow_detail.flow_no = ?
                ORDER BY audit_flow_detail.id DESC
                LIMIT 1;""";
        var s = Db.use().query(sql, flowNo);
        if (s.isEmpty()) {
            return -1;
        }
        return s.get(0).getInt("id");

    }
}
