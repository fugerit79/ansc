package org.fugerit.fork.italia.ansc.decodifiche;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class DecRow extends ArrayList<String> implements Serializable {

	private static final long serialVersionUID = -1812976571249075007L;

	public DecRow() {
		super();
	}

	public DecRow(Collection<String> c) {
		super(c);
	}

}
