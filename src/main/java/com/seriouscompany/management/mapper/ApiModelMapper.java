package com.seriouscompany.management.mapper;

import com.seriouscompany.management.api.model.EmployeeApiModel;
import com.seriouscompany.management.model.EmployeeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApiModelMapper {

    EmployeeApiModel toEmployeeApiModel(EmployeeEntity employee);

    EmployeeEntity toEmployee(EmployeeApiModel employee);
}
