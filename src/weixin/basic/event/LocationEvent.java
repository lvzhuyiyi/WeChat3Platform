package weixin.basic.event;

public class LocationEvent extends BaseEvent {
   private String Latitude;
   private String Longitude;
   private String Precision;
  
public void setMediaId(String latitude) {
	Latitude =latitude;
}
public String getLatitude() {
	return Latitude;
}
public void setLongitude(String longitude) {
	Longitude = longitude;
}
  public String getLongitude() {
	return Longitude;
}
public void setPrecision(String precision) {
	Precision = precision;
}
 public String getPrecision() {
	return Precision;
}
}
