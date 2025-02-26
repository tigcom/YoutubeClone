package websocket;

import java.io.IOException;

import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

import bean.messObj;

public class MessageDecoder implements Decoder.Text<messObj> {
	private ObjectMapper mapper = new ObjectMapper();
	@Override
	public void destroy() {}
	@Override
	public void init(EndpointConfig config) {}
	@Override
	public messObj decode(String json) throws DecodeException {
		try {
			return mapper.readValue(json, messObj.class);
		} catch (IOException e) {
			throw new DecodeException(json, "Unable to decode json!");
		}
	}
	@Override
	public boolean willDecode(String json) {
		return json.contains("nguoiGui") ;
	}
}