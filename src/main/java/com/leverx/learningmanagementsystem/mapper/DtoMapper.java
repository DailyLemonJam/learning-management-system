package com.leverx.learningmanagementsystem.mapper;

import java.util.List;

public interface DtoMapper<Model, Dto> {
    Dto toDto(Model model);
    List<Dto> toDto(List<Model> models);
}
