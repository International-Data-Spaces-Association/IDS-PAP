package de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel;


import lombok.Data;
import org.springframework.util.StringUtils;

import java.net.URI;


@Data
public class Party {
 PartyType type;
 String name;
 URI uri;

 public Party()
 {

 }

 public Party(PartyType type, URI uri)
 {
  this.type = type;
  this.uri = uri;
 }

 public Party(PartyType type, String name, URI uri)
 {
  this.type = type;
  this.name = name;
  this.uri = uri;
 }

 public String getName() {
  if(null == name && StringUtils.isEmpty(name))
  {
   // sample: http://example.com/party/my-party
   String[] termSplit = uri.toString().split("/");
   this.name = termSplit[4];
  }
  return name;
 }

 public URI getUri() {
  return uri;
 }

 @Override
 public String toString() {
  if(null != uri && null != type)
  {
   return  "  \"" + type.getType() + "\": \"" + uri.toString() + "\",    \r\n";
  }
  return "";
 }

}
