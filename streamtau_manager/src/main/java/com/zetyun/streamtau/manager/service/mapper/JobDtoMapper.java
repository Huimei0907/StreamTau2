/*
 * Copyright 2020 Zetyun
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zetyun.streamtau.manager.service.mapper;

import com.zetyun.streamtau.manager.db.model.Job;
import com.zetyun.streamtau.manager.service.dto.JobDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JobDtoMapper {
    JobDtoMapper MAPPER = Mappers.getMapper(JobDtoMapper.class);

    @Mappings({
        @Mapping(target = "jobId", ignore = true),
        @Mapping(target = "projectId", ignore = true),
        @Mapping(source = "name", target = "jobName"),
        @Mapping(source = "appId", target = "appId"),
        @Mapping(target = "version", ignore = true),
        @Mapping(target = "jobDefinition", ignore = true),
        @Mapping(source = "jobStatus", target = "jobStatus"),
    })
    Job toModel(JobDto dto);

    @Mappings({
        @Mapping(source = "jobId", target = "id"),
        @Mapping(source = "jobName", target = "name"),
        @Mapping(source = "appId", target = "appId"),
        @Mapping(source = "jobStatus", target = "jobStatus"),
    })
    JobDto toDto(Job job);
}
