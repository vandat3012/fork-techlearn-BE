package com.techzen.techlearn.service.impl;

import com.techzen.techlearn.enums.TypeStructResponseAI;
import com.techzen.techlearn.repository.StructResponseRepository;
import com.techzen.techlearn.service.StructResponseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TypeStructResponseServiceImpl implements StructResponseService {

   List<String> listTypeStruct = Arrays.asList(TypeStructResponseAI.language.getName(),
                                                TypeStructResponseAI.struct.getName());

    public List<String> getListTypeStruct() {
        return listTypeStruct;
    }


}
