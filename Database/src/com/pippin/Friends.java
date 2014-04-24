package com.pippin;

import java.util.List;

public class Friends {
	List<Integer> user_id;
	List<Integer> connection_id;
	List<String> name;
	
	public Friends (List<Integer> user_ids, List<Integer> connection_ids, List<String> names){
		user_id = user_ids;
		connection_id = connection_ids;
		name = names;
	}
}
