package com.github.akagawatsurunaki.novappro.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.github.akagawatsurunaki.novappro.enumeration.BusType;

public class BusTypeConverter implements Converter<BusType> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return BusType.class;
    }

    @Override
    public WriteCellData<?> convertToExcelData(BusType value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData<>(value.chinese);
    }
}
