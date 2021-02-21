package com.ofbusiness.task.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class GenUtil {

	public List getListWrapper(Object object) {
		List data = new ArrayList();
		data.add(object);
		return data;
	}
}
