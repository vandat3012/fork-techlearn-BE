package com.techzen.techlearn.mapper;

import com.techzen.techlearn.dto.response.StructResponseAIDTO;
import com.techzen.techlearn.entity.StructResponseAI;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StructResponseAIMapper {

    StructResponseAIDTO toStructResponseAIDTO(StructResponseAI structResponseAI);

}
