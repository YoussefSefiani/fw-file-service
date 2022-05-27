package fw.fileservice.message;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFile {

    private String name;
    private String url;
    private String type;
    private long size;
    private Long partnershipId;

}
