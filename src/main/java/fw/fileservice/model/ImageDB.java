package fw.fileservice.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "images")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDB {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;
    private String type;
    @Lob
    private byte[] data;
    private Long userId;

    public ImageDB(String name, String type, byte[] data, Long userId) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.userId = userId;
    }

    public void update(ImageDB image) {
        this.name = image.getName();
        this.type = image.getType();
        this.data = image.getData();
        this.userId = image.getUserId();
    }

}
