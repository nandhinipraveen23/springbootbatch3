package com.example.demo.dto;

import java.util.List;

public class RequiredResponse {
	
	VaccinationDto vaccinationDto;
List<CitizenDto> citizenDto;

public RequiredResponse()
{
	
}
public RequiredResponse(VaccinationDto vaccinationDto, List<CitizenDto> citizenDto) {
	super();
	this.vaccinationDto = vaccinationDto;
	this.citizenDto = citizenDto;
}
public VaccinationDto getVaccinationDto() {
	return vaccinationDto;
}
public void setVaccinationDto(VaccinationDto vaccinationDto) {
	this.vaccinationDto = vaccinationDto;
}
public List<CitizenDto> getCitizenDto() {
	return citizenDto;
}
public void setCitizenDto(List<CitizenDto> citizenDto) {
	this.citizenDto = citizenDto;
}




}
