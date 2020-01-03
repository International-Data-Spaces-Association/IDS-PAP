package de.fraunhofer.iese.ids.odrl.pap.model;


import lombok.Data;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 *
 */
@Data
public class Payment {
 int value;
 String contract;

 public Payment()
 {

 }

 public Payment(int v, String c)
 {
  value = v;
  contract = c;
 }

 public void setValue(){
  this.value = 1;
 }

 public int getValue() {
  return value;
 }

 public void setContract(){
  //http://dbpedia.org/page/Sale
  this.contract = "http://dbpedia.org/page/Rent";
 }

 public String getContract() {
  return contract;
 }
}
