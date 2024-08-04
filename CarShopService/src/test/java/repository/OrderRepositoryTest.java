package repository;

import java.util.ArrayList;
import java.util.List;
import model.Order;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class OrderRepositoryTest {
	@Test
	public void findAll() {
		OrderRepository o = new OrderRepository();
		List<Order> expected = new ArrayList<>();
		List<Order> actual = o.findAll();

		assertEquals(expected, actual);
	}
}
