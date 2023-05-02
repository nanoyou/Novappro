package com.github.akagawatsurunaki.novappro.model.database.file;

import com.github.akagawatsurunaki.novappro.annotation.Field;
import com.github.akagawatsurunaki.novappro.annotation.Table;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@Builder
@FieldNameConstants
@Table(table = "upload_file")
public class UploadFile {

    @Field(field = "flow_no")
    String flowNo;

    @Field(field = "user_id")
    Integer userId;

    @Field(field = "file_name")
    String fileName;

}
