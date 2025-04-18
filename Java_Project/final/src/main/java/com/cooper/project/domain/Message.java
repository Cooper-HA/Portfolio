package com.cooper.project.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="MESSAGES")
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class Message implements Serializable{
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="MESSAGE_ID", unique=true, nullable=false, length=3)
	private Long messageId;
	
    @Column(name = "CUSTOMER_NAME", length = 40)
    private String customerName;
    
    @Column(name = "CUSTOMER_EMAIL", length = 40)
    private String customerEmail;
    
    @Column(name = "MESSAGE_CONTENT", length = 1000)
    private String messageContent;
    
	
}
