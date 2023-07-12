package googledrive.domain;

import googledrive.IndexerApplication;
import googledrive.domain.FileIndexed;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Index_table")
@Data
public class Index {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)

    private String indexName;

    private String fileName;

    @ElementCollection
    private List<String> keywords;

    @PostPersist
    public void onPostPersist() {
        FileIndexed fileIndexed = new FileIndexed(this);
        fileIndexed.publishAfterCommit();
    }

    public static IndexRepository repository() {
        IndexRepository indexRepository = IndexerApplication.applicationContext.getBean(
            IndexRepository.class
        );
        return indexRepository;
    }

    public static void fileCreatedPolicy(FileUploaded fileUploaded) {
        //implement business logic here:
        Index index = new Index();
        index.setFileName(fileUploaded.getFileName());
        index.setIndexName(fileUploaded.getFileName());
        index.setKeywords(Arrays.asList(fileUploaded.getFileName()));

        repository().save(index);

        
        /** Example 2:  finding and process
        
        repository().findById(fileUploaded.get???()).ifPresent(index->{
            
            index // do something
            repository().save(index);


         });
        */

    }
}
