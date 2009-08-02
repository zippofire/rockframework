package net.woodstock.rockframework.test;

import java.text.Normalizer;
import java.text.Normalizer.Form;

import junit.framework.TestCase;
import net.woodstock.rockframework.utils.StringUtils;

public class ColatorTest extends TestCase {

	public void test1() throws Exception {
		System.out.println(StringUtils.normalize("Júnior"));
	}

	public void xtest2() throws Exception {
		for (Form form : Form.values()) {
			System.out.println(form + " | " + Normalizer.normalize("Júnior", form));
		}
	}

}
