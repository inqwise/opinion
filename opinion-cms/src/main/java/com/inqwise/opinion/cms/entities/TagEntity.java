package com.inqwise.opinion.cms.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.cms.common.ITag;

public class TagEntity implements ITag {

	private Long id;
	private String name;
	private Long countOfLinkedItems;
	private int gradeByFive;
	
	public TagEntity(ResultSet reader, Long total) throws SQLException {
		setId(ResultSetHelper.optLong(reader, "tag_id"));
		setName(ResultSetHelper.optString(reader, "tag_name"));
		setCountOfLinkedItems(ResultSetHelper.optLong(reader, "count_of_linked_items"));
		setGradeByFive(Math.round(5 * countOfLinkedItems / total) + 1);
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setCountOfLinkedItems(Long countOfLinkedItems) {
		this.countOfLinkedItems = countOfLinkedItems;
	}

	@Override
	public Long getCountOfLinkedItems() {
		return countOfLinkedItems;
	}

	public void setGradeByFive(int gradeByFive) {
		this.gradeByFive = gradeByFive;
	}

	@Override
	public int getGradeByFive() {
		return gradeByFive;
	}

}
