package de.fraunhofer.iese.ids.odrl.pap.repository;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="tbl_policy")
@Setter
@Getter
@ToString
public class Policy {
	
	public Policy(String name, String policyType, String queryOrigin, String comment, String fieldValues) {
		this.name = name;
		this.policyType = policyType;
		this.queryOrigin = queryOrigin;
		this.comment = comment;
		this.fieldValues = fieldValues;
	}

	public Policy() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String policyType;
	
	private String queryOrigin;
	
	private String comment;
		
	@Column(columnDefinition="text")
	private String fieldValues;
	
	//@Column(columnDefinition="text")
	//private String iDSPolicy;
}
