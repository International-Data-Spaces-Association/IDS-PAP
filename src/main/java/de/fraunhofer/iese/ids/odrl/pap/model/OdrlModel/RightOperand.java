package de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel;


import lombok.Data;


@Data
public class RightOperand {
 RightOperandId id;
 String value;
 RightOperandType type;

 public RightOperand()
 {

 }

 public RightOperand(RightOperandId id)
 {
  this.id = id;
 }

 public RightOperand(String value, RightOperandType type)
 {
  this.value = value;
  this.type = type;
 }

    public RightOperand(RightOperandId id, String value, RightOperandType type) {
        this.id = id;
        this.value = value;
        this.type = type;
    }

    @Override
 public String toString() {
   if(this.id == null && this.value == null && this.type != null)
   {
    return "";
   } else if(this.id != null && this.value == null && this.type == null)
   {
    return "\"@id\": \""+ id.getIdsRightOperand() +"\"";
   }else if(this.id == null && this.value != null && this.type != null && this.type.equals(RightOperandType.DATETIME))
   {
       return "\"@value\": \""+ value +"Z\", \"@type\": \""+ type.getType() +"\"";
   }else if(this.id == null && this.value != null && this.type != null)
   {
    return "\"@value\": \""+ value +"\", \"@type\": \""+ type.getType() +"\"";
   }else if(this.id != null && this.value != null && this.type != null && this.type.equals(RightOperandType.DATETIME))
   {
       return "\"@id\": \""+ id.getIdsRightOperand() + "\"@value\": \""+ value +"Z\", \"@type\": \""+ type.getType() +"\"";
   }else
   {
    return "\"@id\": \""+ id.getIdsRightOperand() + "\"@value\": \""+ value +"\", \"@type\": \""+ type.getType() +"\"";
   }
 }

}
