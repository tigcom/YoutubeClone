package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;

import DAO.UserDAO;
import DAO.UserDAOImpl;
import bean.messObj;
import jakarta.websocket.EncodeException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import websocket.MessageDecoder;
import websocket.MessageEncoder;

@ServerEndpoint(value="/chat/{idulog}",
				encoders = MessageEncoder.class, 
				decoders = MessageDecoder.class)
public class webSocket {
    private static Map<String, Set<Pair<String, Session>>> rooms = new HashMap<>();
    private static Map<String, Session> sessions = new HashMap<>();
    UserDAO uDAO = new UserDAOImpl();

    private void createOrAddToRoom(messObj message, Session session) {
//    	private void createOrAddToRoom(String senderId, String recipientId, Session session) {	
        String roomId1 = message.getNguoiGui() + "-" + message.getNguoiNhan();
        
        Set<Pair<String, Session>> newRoom = new HashSet<>();
        
        Session sesGui = sessions.get(message.getNguoiGui());
        Session sesNhan = sessions.get(message.getNguoiNhan());
        System.out.println("phòng đc tạo nhận vào 2 ses mới");
        newRoom.add(Pair.of( message.getNguoiGui(), sesGui));
        newRoom.add(Pair.of(message.getNguoiNhan(), sesNhan));
        rooms.put(roomId1, newRoom);
        System.out.println("1 phòng được tạo với id :"+roomId1);
 
    }

    private void broadcast(String roomId,messObj message) throws EncodeException, IOException {
        Set<Pair<String, Session>> room = rooms.get(roomId);
        if (room != null) {
            for (Pair<String, Session> pair : room) {
                Session session = pair.getRight();
                System.out.println("sb đang gửi tin tới ses có id : "+session.getId());
                if (session != null && session.isOpen()) {
                        message.setName(uDAO.findById(message.getNguoiGui()).getFullname());
                        message.setImage(uDAO.findById(message.getNguoiGui()).getImage());
                        session.getBasicRemote().sendObject(message);
                        System.out.println(message.getNoiDung());
                }
            }
        }
    }


    @OnOpen
    public void onOpen(@PathParam("idulog") String userId, Session session) {
        System.out.println("Socket đã mở cho userId: " + userId);
        System.out.println("Socket đã mở cho session: " + session);


        if (sessions.containsKey(userId)) {
            try {
                
                Session oldSession = sessions.get(userId);
                if (oldSession.isOpen()) {
                    oldSession.close(); 
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Thay thế session cũ của userId: " + userId);
        }

        sessions.put(userId, session);

        System.out.println("ID của session mới: " + session.getId());
        System.out.println("User " + userId + " đã kết nối thành công.");

        
        System.out.println("Danh sách các session hiện tại:");
        for (Map.Entry<String, Session> entry : sessions.entrySet()) {
            System.out.println("Key (userId): " + entry.getKey() + " - Value (Session): " + entry.getValue().getId());
        }
    }


    @OnMessage
    public void onMessage(messObj message, Session session) throws EncodeException, IOException {
    		System.out.println(message.getImageMes()+"---------------------------");
    		System.out.println( message.toString());
       if (message.getNguoiNhan() == null) {
    	   String roomId = this.findRoomBySenderId(message.getNguoiGui());
    	   this.broadcast(roomId, message);
       }else {
    	   this.createOrAddToRoom(message, session);
           String roomId = message.getNguoiGui() + "-" + message.getNguoiNhan();
           this.broadcast(roomId, message);
       }
    }

    @OnClose
    public void onClose(Session session) {
        String userId = (String) session.getUserProperties().get("userId");
        if (userId != null) {
            sessions.remove(userId);
            System.out.println("User " + userId + " disconnected.");
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String findRoomBySenderId(String senderId) {
        for (Map.Entry<String, Set<Pair<String, Session>>> entry : rooms.entrySet()) {
            String roomId = entry.getKey();
            Set<Pair<String, Session>> roomMembers = entry.getValue();

            for (Pair<String, Session> pair : roomMembers) {
                if (pair.getLeft().equals(senderId)) {
                    return roomId;
                }
            }
        }
        return null;
    }

}
