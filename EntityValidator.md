# Introduction #

Esta página mostra como utilizar a validação usando apenas as anotações padrões do JPA.


# Details #

Primeiro as nossas entidades.
```
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import net.woodstock.rockframework.domain.Entity;
...
@Entity
public class Bar {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer				id;

	@Column(name = "value", length = 10, nullable = false)
	private String				value;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_foo", referencedColumnName = "id")
	private Foo					foo;
	// Getters and Setters
}
...
@Entity
public class Foo {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer				id;

	@Column(name = "name", length = 10, nullable = false)
	private String				name;

	@OneToMany(mappedBy = "foo")
	private Set<Bar>			bars;
	// Getters and Setters
}
```

As classes criadas acima são Pojos comuns, apenas com alguns campos e seus métodos de acesso.

Com a classe que queremos validar em mãos, o processo de validação é bastante simples.
```
import net.woodstock.rockframework.domain.validator.jpa.EntityValidator;
import net.woodstock.rockframework.domain.validator.jpa.Operation;
import net.woodstock.rockframework.domain.validator.jpa.ValidationResult;
import net.woodstock.rockframework.domain.validator.jpa.impl.EntityValidatorImpl;
...
Foo foo = new Foo();
foo.setId(new Integer(1));

Bar bar = new Bar();
bar.setValue("1234567890");
bar.setFoo(foo);
...
// Vamos validar a operacao MERGE(ou INSERT)
// Note o segundo parametro no construtor, indica que TODOS os
// resultados de validacao serao retornados, por padrao apenas
// os erros sao retornados
EntityValidator validator = new EntityValidatorImpl(Operation.PERSIST, true);

// Neste ponto todos as validacoes estarao corretas
for (ValidationResult result : validator.validate(bar)) {
	System.out.println(result);
}
...
// Se alterarmos para validar a operacao MERGE(ou UPDATE),
// o campo ID estara invalido, pois nao pode ser nulo no MERGE
validator = new EntityValidatorImpl(Operation.MERGE, true);

// Neste ponto o campo ID estara invalido
for (ValidationResult result : validator.validate(bar)) {
	System.out.println(result);
}
```

Abaixo temos a enumeração Operation.
```
public enum Operation {

	PERSIST, MERGE, REMOVE, FIND;

}
```

Para as operações REMOVE e FIND, apenas as propriedades anotadas com @Id serão validadas.