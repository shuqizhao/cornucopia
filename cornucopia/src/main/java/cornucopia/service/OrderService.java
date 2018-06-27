package cornucopia.service;

import cornucopia.dao.OrderDao;
import cornucopia.entity.OrderEntity;
import cornucopia.util.MyBatisHelper;

public class OrderService {
	private static OrderService instance = new OrderService();

	private OrderService() {
	}

	public static OrderService getInstance() {
		return instance;
	}

	OrderDao orderDao = MyBatisHelper.getMapper(OrderDao.class);
	
	public String getOrderNo(String pre) {
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setOrderNamePre(pre);
		orderEntity.setNum(8);
		orderEntity.setNewOrderNo("");
		orderDao.getOrderNo(orderEntity);
		return orderEntity.getNewOrderNo();
	}
}
