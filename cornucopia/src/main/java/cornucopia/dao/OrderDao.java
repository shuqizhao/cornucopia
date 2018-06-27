package cornucopia.dao;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import cornucopia.entity.OrderEntity;

public interface OrderDao {
	@Select("call sp_orders_generate_order_no(#{order.orderNamePre},#{order.num},#{order.newOrderNo,mode=OUT,jdbcType=VARCHAR})")
	@Options(statementType = StatementType.CALLABLE)
	public String getOrderNo(@Param("order") OrderEntity orderEntity);
}
