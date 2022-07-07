package de.fraunhofer.iese.ids.odrl.pap.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import de.fraunhofer.iese.ids.odrl.pap.model.ShortPolicy;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name="tbl_policy")
@Data
@ToString
public class Policy implements ShortPolicy{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String policyID;
	
	private String description;
	
	private String policyType;
	
	@Column(columnDefinition="text")
	private String fieldValues;
	
	@Column(columnDefinition="text")
	private String iDSPolicy;
}
