package de.fraunhofer.iese.ids.odrl.pap.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="tbl_policy")
@Setter
@Getter
@ToString
public class Policy {
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
