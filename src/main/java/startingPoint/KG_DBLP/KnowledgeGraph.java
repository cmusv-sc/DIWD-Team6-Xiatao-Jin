package startingPoint.KG_DBLP;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import neo4j.domain.*;
import neo4j.repositories.*;
import neo4j.services.AuthorService;
import neo4j.services.DatasetService;
import neo4j.services.PaperService;

@Configuration
@Import(App.class)
@RestController("/")
public class KnowledgeGraph extends WebMvcConfigurerAdapter {
	//private String test = "'nodes':[{"cluster":"1","id":1,"label":"paper","title":"Integration of Fuzzy ERD Modeling to the Management of Global Contextual Data.","value":2,"group":"paper"}"; 
    public static void main(String[] args) throws IOException {
        SpringApplication.run(KnowledgeGraph.class, args);
    }
    
    @Autowired
    PaperService paperService;
    @Autowired
    DatasetService datasetService;
    @Autowired
    AuthorService authorService;

    @Autowired PaperRepository paperRepository;
    @Autowired DatasetRepository datasetRepository;

    @RequestMapping("/graph")
    public Map<String, Object> graph(@RequestParam(value = "limit",required = false) Integer limit) {
    	return paperService.graph(limit == null ? 100 : limit);
    }
    
    @RequestMapping("/graphPaper2Person")
    public String graphPaper2Person(@RequestParam(value = "limit",required = false) Integer limit) {
    	Map<String, Object> map = paperService.graphAlc(limit == null ? 10 : limit);
    	String json = "";
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		//convert map to JSON string
    		json = mapper.writeValueAsString(map);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return json;
    }
    @RequestMapping("/graphPaper2Paper")
    public String graphPaper2Paper(@RequestParam(value = "limit",required = false) Integer limit) {
    	Map<String, Object> map = paperService.graphPaper2Paper(limit == null ? 10 : limit);
    	String json = "";
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		//convert map to JSON string
    		json = mapper.writeValueAsString(map);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return json;
    }
    
    @RequestMapping("/graphTopKByKeyword")
    public String graphByKeyword(@RequestParam(value = "limit",required = false) Integer limit, @RequestParam(value = "name",required = false) String name) {
    	Map<String, Object> map = paperService.graphAlcByKeyword(limit == null ? 10 : limit, "test");
    	String json = "";
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		//convert map to JSON string
    		json = mapper.writeValueAsString(map);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return json;
    }
    
    @RequestMapping("/graphUserDataset")
    public String graphUserDataset(@RequestParam(value = "limit",required = false) Integer limit) {
    	Map<String, Object> map = authorService.getCoCoAuthor("Hao Cheng");
    	String json = "";
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		//convert map to JSON string
    		json = mapper.writeValueAsString(map);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
//    	System.out.println("=====================================");
//    	System.out.println(json);
    	return json;
    }
    
    @RequestMapping("/getCoAuthor")
    public String getCoAuthor(@RequestParam(value = "name", required = false) String name) {
    	Map<String, Object> map = authorService.getCoAuthor(name);
    	String json = "";
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		//convert map to JSON string
    		json = mapper.writeValueAsString(map);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return json;
    }
    
    @RequestMapping("/getPaperInfo")
    public String getPaperInfo(@RequestParam(value = "name", required = false) String name) {
    	Map<String, Object> map = paperService.getPaperInfo(name);
    	String json = "";
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		//convert map to JSON string
    		json = mapper.writeValueAsString(map);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return json;
    }
    
    
    
    @RequestMapping("/getAuthorStatus")
    public String getAuthorStatus(@RequestParam(value = "name", required = false) String name) {
    	Map<String, Object> map = authorService.getAuthorStatus(name);
    	String json = "";
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		//convert map to JSON string
    		json = mapper.writeValueAsString(map);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return json;
    }
    
    @RequestMapping("/journalGraph")
    public String journalGraph(@RequestParam(value = "name", required = false) String name) {
    	Map<String, Object> map = authorService.getJournalGraph(name);
    	String json = "";
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		//convert map to JSON string
    		json = mapper.writeValueAsString(map);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return json;
    }
    
    
    
    @RequestMapping("/findExpert")
    public String findExpert(@RequestParam(value = "limit", required = false) Integer limit,
    		@RequestParam(value = "keyword", required = false) String keyword) {
    	Map<String, Object> map = authorService.getExpertByKeyword(limit == null ? 10 : limit, keyword);
    	String json = "";
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		//convert map to JSON string
    		json = mapper.writeValueAsString(map);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return json;
    }
    
    @RequestMapping("/findCollaborators")
    public String findExpert(@RequestParam(value = "keyword", required = false) String keyword) {
    	Map<String, Object> map = authorService.getCollaboratorsByKeyword(keyword);
    	String json = "";
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		//convert map to JSON string
    		json = mapper.writeValueAsString(map);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return json;
    }
    
    @RequestMapping("/timelineOfAuthors")
    public String timelineOfAuthors(@RequestParam(value = "startYear", required = false) Integer startYear,
    		@RequestParam(value = "endYear", required = false) Integer endYear,
    		@RequestParam(value = "authorList", required = false) String[] authorList) {
    	Map<String, Object> map = authorService.getTimelineOfAuthors(startYear, endYear, authorList);
    	String json = "";
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		//convert map to JSON string
    		json = mapper.writeValueAsString(map);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return json;
    }
    
    @RequestMapping("/getJournalEvolution")
    public String getJournalEvolution(@RequestParam(value = "startYear", required = false) Integer startYear,
    		@RequestParam(value = "endYear", required = false) Integer endYear,
    		@RequestParam(value = "name", required = false) String name) {
    	Map<String, Object> map = paperService.getJournalEvolution(startYear, endYear, name);
    	String json = "";
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		//convert map to JSON string
    		json = mapper.writeValueAsString(map);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return json;
    }
    @RequestMapping("/getTopKCitedPaper")
    public String getTopKCitedPaper(@RequestParam(value = "year", required = false) Integer year,
    		@RequestParam(value = "name", required = false) String name) {
    	Map<String, Object> map = paperService.getTopKCitedPaper(year, name);
    	String json = "";
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		//convert map to JSON string
    		json = mapper.writeValueAsString(map);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return json;
    }
    
    @RequestMapping("/categorize")
    public String categorize(@RequestParam(value = "startYear", required = false) Integer startYear,
    		@RequestParam(value = "endYear", required = false) Integer endYear,
    		@RequestParam(value = "channel", required = false) String channel,
    		@RequestParam(value = "keywordList", required = false) String[] keywordList) {
    	Map<String, Object> map;
    	if (channel != null && keywordList != null) {
    		map = paperService.categorizeByTimeAndOther(startYear, endYear, channel, keywordList);
    	} else {
    		map = paperService.categorizeByTime(startYear, endYear);
    	}
    	String json = "";
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		//convert map to JSON string
    		json = mapper.writeValueAsString(map);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return json;
    }
    
    @RequestMapping("/getCoCoAuthor")
    public String getCoCoAuthor(@RequestParam(value = "name", required = false) String name) {
    	Map<String, Object> map = authorService.getCoCoAuthor(name);
    	String json = "";
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		//convert map to JSON string
    		json = mapper.writeValueAsString(map);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return json;
    }
    
    @RequestMapping("/graphPerson2Person")
    public String graphPerson2Person(@RequestParam(value = "limit", required = false) Integer limit) {
    	Map<String, Object> map = authorService.graphPerson2Person(limit == null ? 100 : limit);
    	String json = "";
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		//convert map to JSON string
    		json = mapper.writeValueAsString(map);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return json;
    }
    
    @RequestMapping("/getPapers")
    public Collection<Paper> getPapers(String title) {
    	return paperRepository.findByTitleContaining(title);
    	//return paperRepository.findByTitleLike(title);
    }
    
    @RequestMapping("/getPaper")
    public Paper getPaper(String title) {
    	//return movieRepository.findByTitleContaining(title);
    	return paperRepository.findByTitle(title);
    }
    
    @RequestMapping("/test")
    public String getTest(@RequestParam(value="content") String str) {
    	System.out.println("hello world! "+str);
    	return "Hello Test "+str;
    }
}