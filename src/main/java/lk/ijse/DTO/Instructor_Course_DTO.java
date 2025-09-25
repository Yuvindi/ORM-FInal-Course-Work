package lk.ijse.DTO;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lk.ijse.Entity.Course;
import lk.ijse.Entity.Instructor;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Data
public class Instructor_Course_DTO
{
    private int id;
    private InstructorDTO instructor;
    private String courseName;
    private Date courseDate;

}
