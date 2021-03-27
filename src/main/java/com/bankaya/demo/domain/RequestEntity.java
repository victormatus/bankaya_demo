package com.bankaya.demo.domain;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "request_detail")
@Getter
@Setter
public class RequestEntity {
	
	@Id
	@GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
	@Column(name = "id")
	private UUID id;
	
	@Column(name="ip")
	private String ip;
	
	@Column(name="method")
	private String method;
	
	@Column(name = "date")
	private Timestamp date;

	public RequestEntity(String ip, String method, Timestamp date) {
		super();
		this.ip = ip;
		this.method = method;
		this.date = date;
	}
	
	
	
}
