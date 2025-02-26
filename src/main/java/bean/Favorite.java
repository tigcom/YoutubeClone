package bean;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
	@NamedQuery(name ="favorites.findAll", query = "select f from Favorite f order by f.likeDate desc"),
	@NamedQuery(name ="favorites.findByUserId", query = "select  f from Favorite f where f.user = :key")
})
@Table(name = "Favorites", uniqueConstraints = { @UniqueConstraint(columnNames = { "userid", "videoid" }) })
public class Favorite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "Videold")
	@ToString.Exclude
	private Video video; 
	@ManyToOne
	@JoinColumn(name = "Userld")
	private User user;
	@Temporal(TemporalType.DATE)
	private Date likeDate;
}
