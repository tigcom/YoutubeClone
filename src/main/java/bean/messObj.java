package bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class messObj {
	String name;
	String nguoiGui;
	String nguoiNhan;
	String noiDung;
	String image;
	String imageMes;

}
