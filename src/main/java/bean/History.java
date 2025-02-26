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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@NamedQueries({
	@NamedQuery(name ="history.findAll", query = "select h from History h order by h.dateVisti desc"),
	@NamedQuery(name ="history.findByUserId", query = "select  h from History h where h.user = :key")
})
@Table(name = "History", uniqueConstraints = { @UniqueConstraint(columnNames = { "idUser", "idVideo" }) })
public class History {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "idVideo")
	private Video video; 
	@ManyToOne
	@JoinColumn(name = "idUser")
	private User user;
	@Temporal(TemporalType.DATE)
	private Date dateVisti;
}
