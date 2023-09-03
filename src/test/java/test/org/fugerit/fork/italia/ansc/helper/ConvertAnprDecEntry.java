package test.org.fugerit.fork.italia.ansc.helper;

import org.fugerit.java.core.util.collection.KeyString;

import lombok.Data;

@Data
public class ConvertAnprDecEntry implements KeyString {

	private String id;
	
	private String tabella;
	
	private String mode;
	
	@Override
	public String getKey() {
		return this.getId();
	}
	
}
