package fw.fileservice.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "files")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDB {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;
    private String type;
    @Lob
    private byte[] data;
    private Long partnershipId;

    public FileDB(String name, String type, byte[] data, Long partnershipId) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.partnershipId = partnershipId;
    }

}
