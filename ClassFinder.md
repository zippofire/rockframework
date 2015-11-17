# Introduction #

Esta página mostra como utilizar a classe ClassFinder para encontrar classes e interfaces em determinado pacote.


# Details #

Artefatos do Maven necessários.
  * [rockframework-core-2.x.x.jar](http://search.maven.org/#search|ga|1|g%3A%22br.net.woodstock.rockframework%22)

Importações necessárias.
```
import br.net.woodstock.rockframework.reflection.ClassFilter;
import br.net.woodstock.rockframework.reflection.ClassFinder;
import br.net.woodstock.rockframework.reflection.impl.AnnotationClassFilter;
import br.net.woodstock.rockframework.reflection.impl.AssignableClassFilter;
import br.net.woodstock.rockframework.reflection.impl.ClassFilterChain;
import br.net.woodstock.rockframework.reflection.impl.ClassFinderImpl;
```

Um exemplo listando todas as classes do pacote **javax**. Note que e feita uma varredura recursiva, incluindo todos os pacotes abaixo na hierarquia.
```
String pattern = "javax";
ClassFinder finder = new ClassFinderImpl(pattern, null);
for (Class c : finder.getClasses()) {
	System.out.println(c);
}
```

Agora somente as classes ou interfaces de determinado tipo.
```
String pattern = "net"
ClassFinder finder = new ClassFinderImpl(pattern, new AssignableClassFilter(Cloneable.class));
for (Class c : finder.getClasses()) {
	System.out.println(c);
}
```

Também podemos filtrar por anotações.
```
String pattern = "net";
ClassFinder finder = new ClassFinderImpl(pattern, new AnnotationClassFilter(Entity.class));

for (Class c : finder.getClasses()) {
	System.out.println(c);
}
```


Caso necessário pode-se implementar uma filtragem própria, implementando a interface **ClassFilter** ou usar vários filtros através da classe **ClassFilterChain**.