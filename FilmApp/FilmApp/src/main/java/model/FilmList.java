package model;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
// This file is for XML format
@XmlRootElement (name = "filmlist")
@XmlAccessorType (XmlAccessType.FIELD)
public class FilmList{
	@XmlElement(name="film")
	private List<Film> films;
	public FilmList(List<Film> inFilms) {
		films = inFilms;
	}

public FilmList() {}

}