package web.helloworld.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NeoProperties {
	
	public NeoProperties() {
		System.out.println("自定义配置初始化：" + title + ":" + description);
	}

	@Value("${com.ctv.title}")
	private String title;
	@Value("${com.ctv.description}")
	private String description;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
