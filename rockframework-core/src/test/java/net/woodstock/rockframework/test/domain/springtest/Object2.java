package net.woodstock.rockframework.test.domain.springtest;

import org.springframework.stereotype.Component;

@Component(value = "Object2")
public class Object2 {

	public void hello() {
		System.out.println("Hello " + this.getClass().getCanonicalName() + "!!!");
	}

}
