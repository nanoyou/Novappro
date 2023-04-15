import cn.hutool.core.util.EnumUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.github.akagawatsurunaki.novappro.annotation.Table;
import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import com.github.akagawatsurunaki.novappro.enumeration.BusType;
import com.github.akagawatsurunaki.novappro.mapper.ApprovalFlowMapper;
import com.github.akagawatsurunaki.novappro.mapper.impl.ApprovalFlowMapperImpl;
import com.github.akagawatsurunaki.novappro.model.approval.ApprovalFlow;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws SQLException, IllegalAccessException {
        ApprovalFlowMapper approvalFlowMapper = ApprovalFlowMapperImpl.getInstance();
        var af = new ApprovalFlow("1231234", ApprovalStatus.APPROVED, "asdf", BusType.LINEAR, 123, new Date());
        approvalFlowMapper.insert(af);
    }

}
