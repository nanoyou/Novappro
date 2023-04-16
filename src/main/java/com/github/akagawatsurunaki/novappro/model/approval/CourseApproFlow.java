package com.github.akagawatsurunaki.novappro.model.approval;


import com.github.akagawatsurunaki.novappro.annotation.Field;
import com.github.akagawatsurunaki.novappro.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(table = "crs_appro_flow")

public class CourseApproFlow  {

    @Field(field = "appro_flow_nos")
    String approFlowNos;

    @Field(field = "appro_flow_detail_nos")
    String approFlowDetailIds;

    @Field(field = "cur_node_no")
    Integer currentNodeNo;

}
