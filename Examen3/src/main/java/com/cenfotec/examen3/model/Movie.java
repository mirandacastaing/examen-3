package com.cenfotec.examen3.model;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Movie implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String title;
	private double budget;
	private double runtime;
	private String language;
	private Date release;

	public String getFormattedDate() {
		return Date.from(getRelease().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1)
				.atStartOfDay(ZoneId.systemDefault()).toInstant()).toString();
	}
}
