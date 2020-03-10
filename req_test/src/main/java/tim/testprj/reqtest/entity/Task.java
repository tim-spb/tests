package tim.testprj.reqtest.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="tasks")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "description")
	private String description;

	public enum Status {
		NEW, IN_PROCESS, FIXED, REJECTED
	}
	@NotNull
	@Enumerated(EnumType.STRING)
	private Status status = Status.NEW ;
	
	@ElementCollection
	@CollectionTable(name="comment")
	@OrderColumn
	@Column(name="text")
	private List<String> comments = new ArrayList<String>() ;
	
	public Task() {
	}
	
	public Task( String description ) {
		this.description = description ;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<String> getComments() {
		return comments;
	}
	
	public void addComment( String comment ) {
		comments.add( comment ) ;
	}

	public String getDescription() {
		return description;
	}

	public Long getId() {
		return id;
	}
	
}
