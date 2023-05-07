package com.github.akagawatsurunaki.novappro.model.database.file;

import com.github.akagawatsurunaki.novappro.annotation.Field;
import com.github.akagawatsurunaki.novappro.annotation.Table;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@Builder
@FieldNameConstants
@Table(value = "upload_file")
public class UploadFile {

    @Field(value = "flow_no")
    String flowNo;

    @Field(value = "user_id")
    Integer userId;

    @Field(value = "file_name")
    String fileName;

}
