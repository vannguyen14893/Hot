package com.shopping.vn.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.shopping.vn.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "size_color")
public class SizeColor implements Serializable {

  private static final long serialVersionUID = -5762536038061672177L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Integer number;

  @ManyToOne
  @JoinColumn(name = "color_id")
  private Color color;

  @ManyToOne
  @JoinColumn(name = "size_id")
  private Size size;
}
