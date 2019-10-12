package com.jjo.h2.exception;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import lombok.Data;

@Data
@Entity
@Table(name = "Errors")
@EnableJpaAuditing
public class HError {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ERR_ID")
	private Long id;

	@Column(name="ERR_CODE")
	private int code;

	@Column(name="ERR_USER_MSG")
	private String userMessage;

	@Column(name="ERR_TECH_MSG")
	private String techMessage;

	@CreatedDate
	@Column(name="ERR_TIMESTAMP")
	private LocalDateTime eventTime;
}
