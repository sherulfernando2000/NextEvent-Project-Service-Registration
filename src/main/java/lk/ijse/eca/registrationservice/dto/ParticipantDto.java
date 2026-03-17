package lk.ijse.eca.registrationservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantDto {

    @JsonIgnore
    private String nic;
    private String name;
    private String address;
    private String mobile;
    private String email;
    private String picture;
}
