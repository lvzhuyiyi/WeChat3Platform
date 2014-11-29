package weixin.basic.event;

public class MassSendJobFinishEvent extends BaseEvent {
     private String  MsgID;
     private String Status;
     private String  TotalCount;
     private String  FilterCount;
     private String SendCount;
     private String ErrorCount;
public void setMsgID(String latitude) {
	MsgID =latitude;
}
public String getMsgID() {
	return MsgID;
}
public void setStatus(String longitude) {
	Status = longitude;
}
  public String getStatus() {
	return Status;
}
public void setTotalCount(String precision) {
	TotalCount = precision;
}
 public String getTotalCount() {
	return TotalCount;
}
public void setFilterCount(String precision) {
	FilterCount = precision;
}
 public String getFilterCount() {
	return FilterCount;
}
public void setSendCount(String precision) {
	SendCount = precision;
}
 public String getSendCount() {
	return SendCount;
}
public void setErrorCount(String precision) {
	TotalCount = precision;
}
 public String getErrorCount() {
	return TotalCount;
}

}
