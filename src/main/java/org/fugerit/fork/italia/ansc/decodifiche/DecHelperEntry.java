package org.fugerit.fork.italia.ansc.decodifiche;

import java.io.Serializable;

import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.util.collection.KeyObject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class DecHelperEntry implements KeyObject<String>, Serializable {

	private static final long serialVersionUID = 1433238264418204845L;

	public DecHelperEntry( String key, String value ) {
		this( key, key, value );
	}
	
	@NonNull @Getter @Setter private String id;
	
	@NonNull @Getter @Setter private String key;
	
	@NonNull @Getter @Setter private String value;
	
	@Setter private String simpleValue;
	
	public String getSimpleValue() {
		return StringUtils.valueWithDefault( this.simpleValue , this.getValue() );		// se non impostato simpleValue è uguale a value
	}

}
