package com.yeshcp.tpush;

import java.util.HashSet;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;


public class MyPushReceiver extends XGPushBaseReceiver {
	public static final String LogTag = "TPushReceiver";

	private void show(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	private void fireEvent(String type, Object data){
		if(type=="registerResult"){
			TpushModule.handleRegisterResult(data);
		}else if(type=="unregisterResult"){
			TpushModule.handleUnregisterResult(data);
		}else if(type=="setTagResult"){
			TpushModule.handleSetTagResult(data);
		}else if(type=="deleteTagResult"){
			TpushModule.handleDeleteTagResult(data);
		}else if(type=="textMessage"){
			TpushModule.handleTextMessage(data);
		}else if(type=="notifactionClick"){
			TpushModule.handleNotifactionClick(data);
		}else if(type=="notifactionShow"){
			TpushModule.handleNotifactionShow(data);
		}  
	}

	
	@Override
	public void onRegisterResult(Context context, int errorCode,
			XGPushRegisterResult registerMessage) {
		if (context == null || registerMessage == null) {
			return;
		}
		String text = null;
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = registerMessage + "register success";
			
			String token = registerMessage.getToken();
		} else {
			text = registerMessage + "fail,errcode:" + errorCode;
		}
		Log.d(LogTag, text);
		//show(context, text);
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("errorCode",errorCode);
		fireEvent("registerResult",data);
	}

	
	@Override
	public void onUnregisterResult(Context context, int errorCode) {
		if (context == null) {
			return;
		}
		String text = null;
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = "unregister success";
		} else {
			text = "unregister fail:" + errorCode;
		}
		Log.d(LogTag, text);
		//show(context, text);
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("errorCode",errorCode);
		fireEvent("unregisterResult",data);
	}

	
	@Override
	public void onSetTagResult(Context context, int errorCode, String tagName) {
		if (context == null) {
			return;
		}
		//show(context, text);
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("errorCode",errorCode);
		data.put("tagName",tagName);
		fireEvent("setTagResult",data);
	}

	
	@Override
	public void onDeleteTagResult(Context context, int errorCode, String tagName) {
		if (context == null) {
			return;
		}
		//show(context, text);
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("errorCode",errorCode);
		data.put("tagName",tagName);
		fireEvent("deleteTagResult",data);
	}

	
	@Override
	public void onTextMessage(Context context, XGPushTextMessage message) {
		if (context == null || message == null) {
			return;
		}
		String content = message.getContent();
		String title = message.getTitle();
		String customContent = message.getCustomContent();
		//show(context, content);
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("title",title);
		data.put("content",content);
		data.put("customContent",customContent);
		fireEvent("textMessage",data);
	}

	
	@Override
	public void onNotifactionClickedResult(Context context,
			XGPushClickedResult message) {
		if (context == null || message == null) {
			return;
		}
		long msgId = message.getMsgId();
		String title = message.getTitle();
		String content = message.getContent();
		String activityName = message.getActivityName();
		String customContent = message.getCustomContent();
		
		Log.d(LogTag, content);
		// show(context, content);
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("msgId",msgId);
		data.put("title",title);
		data.put("content",content);
		data.put("customContent",customContent);
		data.put("activityName",activityName);
		fireEvent("notifactionClick",data);
	}

	@Override
	public void onNotifactionShowedResult(Context context,
			XGPushShowedResult message) {
		if (context == null || message == null) {
			return;
		}
		long msgId = message.getMsgId();
		String title = message.getTitle();
		String content = message.getContent();
		String customContent = message.getCustomContent();
		Log.d(LogTag, content);
		//show(context, content);
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("msgId",msgId);
		data.put("title",title);
		data.put("content",content);
		data.put("customContent",customContent);
		fireEvent("notifactionShow",data);
	}
}