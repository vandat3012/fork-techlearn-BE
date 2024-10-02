package com.techzen.techlearn.service.impl;

import com.techzen.techlearn.dto.response.StructResponseAIDTO;
import com.techzen.techlearn.entity.StructResponseAI;
import com.techzen.techlearn.enums.ErrorCode;
import com.techzen.techlearn.enums.TypeStructResponseAI;
import com.techzen.techlearn.exception.AppException;
import com.techzen.techlearn.mapper.StructResponseAIMapper;
import com.techzen.techlearn.repository.StructResponseRepository;
import com.techzen.techlearn.service.StructResponseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StructResponseServiceImpl implements StructResponseService {

    StructResponseRepository structResponseRepository;
    StructResponseAIMapper structResponseAIMapper;


    public  void deleteStruct(Long idStruct){


        try{
        structResponseRepository.deleteById(idStruct);}
        catch (Exception e){

            throw new AppException(ErrorCode.STRUCT_RESPONSE_NOT_EXISTED);
        }
    }

    public List<StructResponseAIDTO> getAll() {
        return structResponseRepository.
                findAll()
                .stream()
                .map(structResponseAIMapper::toStructResponseAIDTO)
                .collect(Collectors.toList());
    }

    public StructResponseAIDTO getById(Long idStruct) {
        return structResponseAIMapper
                .toStructResponseAIDTO(structResponseRepository
                        .findById(idStruct)
                        .orElseThrow(() -> new AppException(ErrorCode.STRUCT_RESPONSE_NOT_EXISTED)));
    }

    public StructResponseAIDTO saveStructResponse(String contentStruct, String struct) {
        if(contentStruct.trim().equals("")){
            throw new AppException(ErrorCode.CONTENT_STRUCT_RESPONSE_NOT_NULL);
        }
        try {
            struct = TypeStructResponseAI.valueOf(struct.toLowerCase()).getName();
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.TYPE_STRUCT_RESPONSE_NOT_EXISTED);
        }
        return structResponseAIMapper
                .toStructResponseAIDTO(structResponseRepository
                        .save(StructResponseAI.builder()
                                .contentStruct(contentStruct)
                                .type(struct)
                                .isActive(false)
                                .build()));
    }

    public StructResponseAIDTO updateStructResponse(String contentStruct, String struct, long idStruct) {

        if(contentStruct.trim().equals("")){
            throw new AppException(ErrorCode.CONTENT_STRUCT_RESPONSE_NOT_NULL);
        }
        try {
            struct = TypeStructResponseAI.valueOf(struct.toLowerCase()).getName();
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.TYPE_STRUCT_RESPONSE_NOT_EXISTED);
        }
        StructResponseAI structResponseAI = structResponseRepository
                .findById(idStruct)
                .orElseThrow(() -> new AppException(ErrorCode.STRUCT_RESPONSE_NOT_EXISTED));
        structResponseAI.setContentStruct(contentStruct);
        structResponseAI.setType(struct);
        structResponseAI.setIsActive(false);
        return structResponseAIMapper
                .toStructResponseAIDTO(structResponseRepository
                        .save(structResponseAI));
    }


    public StructResponseAIDTO activeStructResponse(long idStruct) {
        StructResponseAI structResponseAI = structResponseRepository.findById(idStruct)
                .orElseThrow(() -> new AppException(ErrorCode.STRUCT_RESPONSE_NOT_EXISTED));

        if (structResponseAI.getType().equalsIgnoreCase(TypeStructResponseAI.language.getName())) {
            return structResponseAIMapper.toStructResponseAIDTO(activeTypeLanguage(structResponseAI, structResponseAI.getType()));

        } else {
            return structResponseAIMapper.toStructResponseAIDTO(activeTypeStruct(structResponseAI));

        }

    }

    public StructResponseAI activeTypeLanguage(StructResponseAI structResponseAI, String Type) {
        structResponseRepository.findAllByTypeLike("%"+Type.toLowerCase()+"%").forEach(element -> {
            element.setIsActive(false);
            System.out.println( "alo day ne ");
            structResponseRepository.save(element);
        });
        structResponseAI.setIsActive(true);
        structResponseRepository.save(structResponseAI);
        return structResponseAI;
    }

    public StructResponseAI activeTypeStruct(StructResponseAI structResponseAI) {
        structResponseAI.setIsActive(!structResponseAI.getIsActive());
        structResponseRepository.save(structResponseAI);
        return structResponseAI;
    }
}
