package net.woodstoc.rockframework.jsf.test.persistence;

import java.util.ArrayList;
import java.util.List;

import net.woodstoc.rockframework.jsf.test.entity.Foo;

public class FooRepository {

	private static List<Foo> foos;

	private static int id;

	public FooRepository() {
		super();
		if (FooRepository.foos == null) {
			FooRepository.foos = new ArrayList<Foo>();
			for (FooRepository.id = 1; FooRepository.id <= 5; FooRepository.id++) {
				Foo foo = new Foo();
				foo.setId(new Integer(FooRepository.id));
				foo.setName("Test " + FooRepository.id);
				FooRepository.foos.add(foo);
			}
		}
	}

	public void delete(Foo foo) {
		int index = -1;
		int count = 0;
		for (Foo f : FooRepository.foos) {
			if (f.getId().equals(foo.getId())) {
				index = count;
				break;
			}
			count++;
		}
		if (index != -1) {
			FooRepository.foos.remove(index);
		}
	}

	public Foo get(Foo foo) {
		for (Foo f : FooRepository.foos) {
			if (f.getId().equals(foo.getId())) {
				return f;
			}
		}
		return null;
	}

	public List<Foo> list() {
		return FooRepository.foos;
	}

	public void save(Foo foo) {
		FooRepository.id++;
		foo.setId(new Integer(FooRepository.id));
		FooRepository.foos.add(foo);
	}

	public void update(Foo foo) {
		Foo f = this.get(foo);
		if (f != null) {
			f.setName(foo.getName());
		}
	}

}
