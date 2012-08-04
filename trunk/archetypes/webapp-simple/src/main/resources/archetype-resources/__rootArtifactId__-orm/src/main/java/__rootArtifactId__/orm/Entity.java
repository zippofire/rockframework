package ${package}.${rootArtifactId}.orm;

import java.io.Serializable;

public interface Entity<T> extends Serializable {
	
	T getId();
	
	void setId(T id);
	
}