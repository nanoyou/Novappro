package com.github.akagawatsurunaki.novappro.model.database.approval;


import com.github.akagawatsurunaki.novappro.annotation.Field;
import com.github.akagawatsurunaki.novappro.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Table(table = "crs_appro_flow")

public class CourseApproFlow  {

    /**
     * 审批流的唯一标志号码
     */
    @Field(field = "appro_flow_nos")
    String approFlowNos;

    /**
     * 审批流明细的唯一标志号码, 这里有多个, 可组成一列
     */
    @Field(field = "appro_flow_detail_nos")
    String approFlowDetailIds;

    /**
     * 当前的审批流结点的唯一标志号码
     */
    @Field(field = "cur_node_no")
    Integer currentNodeNo;

}
