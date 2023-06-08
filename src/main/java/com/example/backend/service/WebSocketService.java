package com.example.backend.service;


import com.example.backend.pojo.Result;
import com.example.backend.util.ContainerGetter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;

@Component
@ServerEndpoint("/webSocket")
public class WebSocketService {

    @Resource
    private TextService textService;

    private Session session;

    private int uid;

    private String textId;

    private static final ArrayList<WebSocketService> webSocketServices = new ArrayList<>();

    @OnOpen
    public Result onOpen(Session session)
    {
        this.session = session;
        try {
            uid = Integer.parseInt(session.getPathParameters().get("uid"));
            textId = session.getPathParameters().get("textId");
            webSocketServices.add(this);
            return new Result(true,200,"连接成功",1);
        }catch (Exception e)
        {
            return new Result(false,403,"参数异常",-1);
        }
    }

    @OnMessage
    public void onMessage(String s)
    {
        sendMessage(s,this.reqOtherSocket());
    }

    @OnClose
    public void onClose()
    {
        if(ifCanUse(uid,textId))
        {
            newUse(-1,textId);
        }
        webSocketServices.remove(this);
    }

    public void sendMessage(String s,ArrayList<WebSocketService> webSocketServices)
    {
        if(ifCanUse(uid,textId))
        {
            if(newUse(uid,textId) == 1)
            {
                if(s.equals("a[sl……73m开始编辑"))
                {
                    try {
                        this.session.getBasicRemote().sendText("a[sl……73m占锁成功");
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(s.equals("a[sl……73m结束编辑"))
                {
                    newUse(-1,textId);
                    return;
                }
                for(WebSocketService webSocketService : webSocketServices)
                {
                    try {
                        webSocketService.session.getBasicRemote().sendText(s);
                        return;
                    } catch (Exception e) {
                        System.out.println(ContainerGetter.getUserService().findUserBYU(webSocketService.getUid()).getF_userName()+"同步失败");
                    }
                }
            }
            try {
                this.session.getBasicRemote().sendText("a[sl……73m数据库异常");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.session.getBasicRemote().sendText("a[sl……73m键被占用");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof WebSocketService))
        {
            return false;
        }
        return ((WebSocketService)obj).getUid() == uid && ((WebSocketService)obj).getTextId().equals(textId);
    }

    public int getUid() {
        return uid;
    }

    public String getTextId() {
        return textId;
    }

    public ArrayList<WebSocketService> reqOtherSocket()
    {
        ArrayList<WebSocketService> result = new ArrayList<>();
        for(WebSocketService i : webSocketServices)
        {
            if(i.getTextId() == textId && i.getUid() != uid)
            {
                result.add(i);
            }
        }
        return result;
    }

    public int whoUse(String textId)
    {
        return textService.whoUse(textId);
    }

    public int newUse(int uid, String textId)
    {
        return textService.newUse(uid,textId);
    }

    public boolean ifCanUse(int uid,String textId)
    {
        return whoUse(textId) == -1 || whoUse(textId) == uid ;
    }
}
