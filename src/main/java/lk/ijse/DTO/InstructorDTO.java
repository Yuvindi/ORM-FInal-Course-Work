package lk.ijse.DTO;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Data
public class InstructorDTO {
    private int instructorId;
    private String instructorName;
    private String instructorEmail;
    private String instructorPhone;
    private String instructorAddress;

}
