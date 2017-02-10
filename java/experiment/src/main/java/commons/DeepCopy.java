package commons;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class DeepCopy {
	public static void main(String[] args) {
		Order order1 = new Order();
		order1.setId(11L);
		order1.setName("11");

		Order order2 = new Order();
		order2.setId(22L);
		order2.setName("22");

		List<Order> orders = new ArrayList<Order>();
		orders.add(order1);
		orders.add(order2);

		Cust cust = new Cust();
		cust.setId(33L);
		cust.setName("33");
		cust.setList(orders);

		Cust cust1 = null;
		try {
			cust1 = (Cust) cust.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		cust.setName("333");
		cust.getList().get(0).setName("111");

		// source
		System.out.println(cust.getName());
		System.out.println(cust.getList().get(0).getName());

		// dest
		System.out.println(cust1.getName());
		System.out.println(cust1.getList().get(0).getName());

	}
}

class Order implements Cloneable {
	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}

class Cust implements Cloneable {
	private Long id;
	private String name;
	private List<Order> list;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Order> getList() {
		return list;
	}

	public void setList(List<Order> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Cust o = null;
		try {
			o = (Cust) super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println(e.getMessage());
		}

		List<Order> ordersClone = o.getList();
		List<Order> copy = new ArrayList<Order>(ordersClone.size());

		Iterator<Order> iterator = ordersClone.iterator();
		while (iterator.hasNext()) {
			copy.add((Order) iterator.next().clone());
		}
		o.setList(copy);

		return o;
	}
}