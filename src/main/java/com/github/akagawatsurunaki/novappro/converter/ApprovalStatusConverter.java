package com.github.akagawatsurunaki.novappro.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;

public class ApprovalStatusConverter implements Converter<ApprovalStatus> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return ApprovalStatus.class;
    }

    @Override
    public WriteCellData<?> convertToExcelData(ApprovalStatus value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData<>(value.chinese);
    }
}
