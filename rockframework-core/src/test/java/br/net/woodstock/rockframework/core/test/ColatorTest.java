package br.net.woodstock.rockframework.core.test;

import java.text.Normalizer;
import java.text.Normalizer.Form;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.text.impl.NormalizerTransformer;

public class ColatorTest extends TestCase {

	public void test1() throws Exception {
		System.out.println(NormalizerTransformer.getInstance().transform("Júnior"));
	}

	public void xtest2() throws Exception {
		for (Form form : Form.values()) {
			System.out.println(form + " | " + Normalizer.normalize("Júnior", form));
		}
	}

}
