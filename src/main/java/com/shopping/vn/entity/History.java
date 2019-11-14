package com.shopping.vn.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.shopping.vn.dto.HistoryDto;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class History implements Serializable {

	private static final long serialVersionUID = -5666361400795609065L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descrition;
	@Column(name = "created_date")
	private Date createdDate;
	@Column(name = "updated_date")
	private Date updatedDate;
	@Column(name = "deleted_date")
	private Date deletedDate;
	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;
	@ManyToOne
	@JoinColumn(name = "updated_by")
	private User updatedBy;

	@ManyToOne
	@JoinColumn(name = "deleted_by")
	private User deletedBy;

	public static final History convertSave(HistoryDto historyDto, User user) {
		History history = new History();
		history.setCreatedDate(new Date());
		history.setDescrition(historyDto.getDescrition());
		history.setCreatedBy(user);
		return history;
	}

	public static final History convertUpdate(HistoryDto historyDto, User user) {
		History history = new History();
		history.setUpdatedDate(new Date());
		history.setDescrition(historyDto.getDescrition());
		history.setUpdatedBy(user);
		return history;
	}

	public static final History convertDelete(HistoryDto historyDto, User user) {
		History history = new History();
		history.setDeletedDate(new Date());
		history.setDescrition(historyDto.getDescrition());
		history.setDeletedBy(user);
		return history;
	}
}
