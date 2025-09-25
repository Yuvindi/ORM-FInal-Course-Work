package lk.ijse.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int instructorId;
    private String instructorName;
    @Column(unique = true)
    private String instructorEmail;
    private String instructorPhone;
    private String instructorAddress;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Instructor_Course> instructorCourses;

}
