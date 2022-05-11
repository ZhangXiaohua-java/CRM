package cloud.huel.crm.domain;

import cloud.huel.crm.commons.enumeration.ResponseStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 张晓华
 * @version 1.0
 */
public class Message implements Serializable {

	private ResponseStatus responseStatus;
	private String message ;
	private Map<String,Object> map = new HashMap<>();

	public String getResponseStatus() {
		return responseStatus.getCode();
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public static Message processSuccessRequest(){
		Message message = new Message();
		message.setResponseStatus(ResponseStatus.SUCCESS);
		message.setMessage("请求处理成功");
		return message;
	}

	public static Message processFailedRequest(){
		Message message = new Message();
		message.setResponseStatus(ResponseStatus.FAIL);
		message.setMessage("请求处理失败,请检查提交的参数");
		return message;
	}

	public Message add(String key,Object value){
		Map<String, Object> map = this.getMap();
		map.put(key,value);
		return this;
	}



}
