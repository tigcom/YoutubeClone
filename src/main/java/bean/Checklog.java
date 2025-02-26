package bean;



import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "checklog")
public class Checklog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Integer id;

    @Column(name = "accecsTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date accecsTime; 
  
    @Column(name = "userName", length = 255)
    private String userName;
    @Column(name = "device", length = 255)
    private String device;
    @Column(name = "URI", length = 255)
    private String URI;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_User")
    private User user;  
}
