package bean;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;
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
@Table(name = "Videos")
@NamedQueries({
	@NamedQuery(name ="video.findTitleByKeyWord", query = "select v from Video v where v.title like :key"),
	@NamedQuery(name ="video.findByPoster", query = "select v from Video v where v.author = :key")
})
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
			name="video.FindVideoById",
			procedureName ="FindVideoById",
			resultClasses = {Video.class},
			parameters = @StoredProcedureParameter(name="id",type=String.class)
			)
})
public class Video {
    @Id
    @Column(columnDefinition = "NVARCHAR(50)")
    private String id;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String title;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String poster;

    @Column(columnDefinition = "NVARCHAR(200)")
    private String description;

    private boolean active;

    @Column(columnDefinition = "NVARCHAR(500)") 
    private String link;

    private int views;

    @OneToMany(mappedBy = "video", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Favorite> favorites;
    
    @OneToMany(mappedBy = "video", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<History> history;
    
    private Date dateUpload;
    @ManyToOne
    @JoinColumn(name = "author")
    private User author;
    @OneToMany(mappedBy = "video", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Comment> comments;

}