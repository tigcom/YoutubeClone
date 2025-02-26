package bean;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@NamedQueries({
	@NamedQuery(name ="subscribe.findAll", query = "select s from Subscribe s"),
	@NamedQuery(name ="subscribe.findByUserId", query = "select  s from Subscribe s where s.userAuth = :key")
})
@Table(name = "subscribers", uniqueConstraints = { @UniqueConstraint(columnNames = { "idAuth", "idDk" }) })
public class Subscribe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "idAuth")
	@ToString.Exclude
	private User userAuth; 
	@ManyToOne
	@JoinColumn(name = "idDk")
	@ToString.Exclude
	private User userDk;
}
