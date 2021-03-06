package neo4j.domain;

import org.neo4j.ogm.annotation.*;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@JsonIdentityInfo(generator=JSOGGenerator.class)

@NodeEntity
public class Paper {
    @GraphId 
    Long id;

    String title;
    
    String year;
    
    String category;
    
    String channel;
    
    String bookTitle;
    
    String cite;

    @Relationship(type="PUBLISH", direction = Relationship.INCOMING) List<Publication> publications;

    public Paper() { }

    public String getTitle() {
        return title;
    }
    
    public String getYear() {
    	return year;
    }
    
    public String getCategory() {
    	return category;
    }
    
    public String getChannel() {
    	return channel;
    }
    
    public String getBookTitle() {
    	return bookTitle;
    }

    public Collection<Publication> getPublications() {
        return publications;
    }
    
    public String getCite() {
    	return cite;
    }
}

