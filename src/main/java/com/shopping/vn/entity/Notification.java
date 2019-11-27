package com.shopping.vn.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Notification implements Serializable {

  private static final long serialVersionUID = -3553774885603127579L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String content;
  private Boolean status;
  private Date createdDate;
  @ManyToOne
  @JoinColumn(name = "receiver_id")
  @JsonIgnore
  private User receiver;
  @ManyToOne
  @JoinColumn(name = "sender_id")
  @JsonIgnore
  private User sender;
  @ManyToOne
  @JoinColumn(name = "comment_id")
  @JsonIgnore
  private Comment comment;

}
