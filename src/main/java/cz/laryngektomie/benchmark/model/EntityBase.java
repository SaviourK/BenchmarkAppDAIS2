package cz.laryngektomie.benchmark.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class EntityBase implements Serializable {

	protected Long id;

	private LocalDateTime createDateTime;

	private LocalDateTime updateDateTime;

	protected EntityBase() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

	public LocalDateTime getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(LocalDateTime updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

}
