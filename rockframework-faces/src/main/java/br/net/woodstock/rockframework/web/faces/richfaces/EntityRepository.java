package br.net.woodstock.rockframework.web.faces.richfaces;

import java.io.Serializable;

import br.net.woodstock.rockframework.persistence.orm.Page;
import br.net.woodstock.rockframework.persistence.orm.QueryResult;

public interface EntityRepository extends Serializable {

	QueryResult getResult(final Page page);

	Object getEntity(final Object id);

}
