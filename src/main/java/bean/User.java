package bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "Users")
@JsonIgnoreProperties({"favorites", "history", "subscribers", "subscribList", "videos", "comments"})
public class User {
    @Id
    @Column(columnDefinition = "NVARCHAR(50)")
    private String id;

    @Column(columnDefinition = "NVARCHAR(100)")
    private String password;

    @Column(columnDefinition = "NVARCHAR(100)")
    private String email;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String fullname;

    private Boolean admin;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<History> history;

    @OneToMany(mappedBy = "userAuth", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Subscribe> subscribers;

    @OneToMany(mappedBy = "userDk", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Subscribe> subscribList;

    private String image;
    private String phone;
    private boolean gender;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Video> videos;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Comment> comments;
}
