package net.woodstock.rockframework.domain.persistence.orm.util;

public abstract class PersistenceUtil {

	private static final String	HIBERNATE_PROXY_CLASS	= "org.hibernate.proxy.HibernateProxy";

	public static final String	OPENJPA_PROXY_CLASS		= "org.apache.openjpa.enhance.PersistenceCapable";

	private PersistenceUtil() {
		//
	}

	private static boolean isHibernateAvailable() {
		try {
			Class.forName(PersistenceUtil.HIBERNATE_PROXY_CLASS);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	private static boolean isJpaAvailable() {
		try {
			Class.forName(PersistenceUtil.OPENJPA_PROXY_CLASS);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public static boolean isProxy(final Object o) {
		boolean b = false;
		if (PersistenceUtil.isJpaAvailable()) {
			b = JPAUtil.isProxy(o);
		}
		if (!b) {
			if (PersistenceUtil.isHibernateAvailable()) {
				b = HibernateUtil.isProxy(o);
			}
		}
		return b;
	}

	@SuppressWarnings("unchecked")
	public static <E> Class<E> getRealClass(final E e) {
		if (PersistenceUtil.isProxy(e)) {
			if (PersistenceUtil.isJpaAvailable()) {
				return JPAUtil.getRealClass(e);
			}
			if (PersistenceUtil.isHibernateAvailable()) {
				return HibernateUtil.getRealClass(e);
			}
		}
		return (Class<E>) e.getClass();
	}

}
