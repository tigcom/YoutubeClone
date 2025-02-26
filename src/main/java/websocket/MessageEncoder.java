package websocket;

import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bean.messObj;

public class MessageEncoder implements Encoder.Text<messObj> {
	private ObjectMapper mapper = new ObjectMapper();
	@Override
	public void destroy() {}
	@Override
	public void init(EndpointConfig config) {}
	@Override
	public String encode(messObj message) throws EncodeException {
		try {
			return mapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			throw new EncodeException(message, "Unable to encode message");
		}
	}
}