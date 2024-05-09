package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Order {
  private  String Order_id;
  private Date date;
  private double Price;
  private String Customer_id;
  private String User_id;
}
