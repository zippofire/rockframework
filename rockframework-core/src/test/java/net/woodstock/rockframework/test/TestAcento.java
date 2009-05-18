package net.woodstock.rockframework.test;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class TestAcento {

	public static void main(String [] args) {
		List<String> list = new ArrayList<String>();
		list.add("A");
		list.add("Á");
		list.add("U");
		list.add("Ú");
		list.add("É");
		list.add("E");
		Collections.sort(list, new Comparator<String>() {
			public int compare(String s1, String s2) {
				Collator collator = Collator.getInstance();
				return collator.compare(s1, s2);
			}
		});
		for(String s: list) {
			System.out.println(s);
		}
	}
	
}
