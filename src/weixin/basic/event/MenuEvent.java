package weixin.basic.event;

public class MenuEvent extends BaseEvent {
     private String EventKey;
     public String getEventkey(){
        return EventKey;
     }
     public void setEventKey(String eventkey){
       EventKey=eventkey;
     }
}
