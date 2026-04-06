package com.klef.fsad.sdp.dto;

public class PublishedCourseWithInstructorDTO
{
	private long id;
	private String title;
	private String description;
	private String category;
	private boolean published;
	private InstructorDTO instructor;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public InstructorDTO getInstructor() {
		return instructor;
	}

	public void setInstructor(InstructorDTO instructor) {
		this.instructor = instructor;
	}
}