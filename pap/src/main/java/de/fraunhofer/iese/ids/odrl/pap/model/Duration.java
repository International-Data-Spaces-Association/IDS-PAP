package de.fraunhofer.iese.ids.odrl.pap.model;


import lombok.Data;
/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 *
 */
@Data
public class Duration {
 int value;
 TimeUnit timeUnit;

 public void setValue(){
  this.value = 1;
 }

 public int getValue() {
  return value;
 }

 public void setTimeUnit(){
  this.timeUnit = TimeUnit.HOURS;
 }

 public TimeUnit getTimeUnit() {
  return timeUnit;
 }
}
