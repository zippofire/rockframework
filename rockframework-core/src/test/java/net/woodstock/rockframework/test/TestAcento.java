package net.woodstock.rockframework.test;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import junit.framework.TestCase;

public class TestAcento extends TestCase {

	public void test1() throws Exception {
		List<String> list = new ArrayList<String>();
		list.add("A");
		list.add("Á");
		list.add("U");
		list.add("Ú");
		list.add("É");
		list.add("E");
		Collections.sort(list, new Comparator<String>() {

			@Override
			public int compare(final String s1, final String s2) {
				Collator collator = Collator.getInstance();
				return collator.compare(s1, s2);
			}
		});
		for (String s : list) {
			System.out.println(s);
		}
	}

}
