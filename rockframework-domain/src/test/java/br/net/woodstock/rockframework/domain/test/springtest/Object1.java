package br.net.woodstock.rockframework.domain.test.springtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Object1 {

	@Autowired(required = true)
	@Qualifier(value = "Object2")
	private Object2	object;

	public void hello() {
		System.out.println("Hello " + this.getClass().getCanonicalName() + "!!!");
		this.object.hello();
	}

}
