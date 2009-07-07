package net.woodstock.rockapi.ejb.test;

import java.io.InputStream;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import net.woodstock.rockapi.ejb.Calculator;

public class TestEJB {

	public static void main(String[] args) {
		try {
			Properties properties = new Properties();
			InputStream inputStream = TestEJB.class.getClassLoader().getResourceAsStream(
					"ejb-client-login.properties");
			properties.load(inputStream);
			Context context = new InitialContext(properties);
			Calculator calculator = (Calculator) context.lookup("Calculator/remote");
			System.out.println(calculator.sum(new Integer(1), new Integer(2)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
