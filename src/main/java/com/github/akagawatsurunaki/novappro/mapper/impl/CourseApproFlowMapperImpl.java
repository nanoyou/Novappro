package com.github.akagawatsurunaki.novappro.mapper.impl;

import cn.hutool.db.Db;
import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.mapper.CourseApproFlowMapper;
import com.github.akagawatsurunaki.novappro.model.database.approval.CourseApproFlow;
import com.github.akagawatsurunaki.novappro.util.EntityUtil;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.SQLException;

public class CourseApproFlowMapperImpl implements CourseApproFlowMapper {

    @Getter
    private final static CourseApproFlowMapper instance = new CourseApproFlowMapperImpl();

    @Override
    public Pair<VerifyCode.Mapper, CourseApproFlow> insert(@NonNull CourseApproFlow courseApproFlow) {
        try {
            int rows = Db.use().insert(EntityUtil.getEntity(courseApproFlow));
            if (rows > 0) {
                return new ImmutablePair<>(VerifyCode.Mapper.OK, courseApproFlow);
            }
            return new ImmutablePair<>(VerifyCode.Mapper.OTHER_EXCEPTION, courseApproFlow);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VerifyCode.Mapper.SQL_EXCEPTION, courseApproFlow);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VerifyCode.Mapper.OTHER_EXCEPTION, courseApproFlow);
        }
    }

    @Override
    public Pair<VerifyCode.Mapper, CourseApproFlow> select() {
        return null;
    }

    @Override
    public Integer findMaxIdOfCourseApproFlow(@NonNull String flowNo) throws SQLException {

        var sql = "SELECT audit_flow_detail.id\n" +
                "FROM audit_flow_detail\n" +
                "WHERE audit_flow_detail.flow_no = ?\n" +
                "ORDER BY audit_flow_detail.id DESC\n" +
                "LIMIT 1;";
        var s = Db.use().query(sql, flowNo);
        if (s.isEmpty()) {
            return -1;
        }
        return s.get(0).getInt("id");

    }
}
