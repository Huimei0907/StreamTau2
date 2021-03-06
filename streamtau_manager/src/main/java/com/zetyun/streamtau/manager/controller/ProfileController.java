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

package com.zetyun.streamtau.manager.controller;

import com.zetyun.streamtau.manager.service.ProfileService;
import com.zetyun.streamtau.manager.service.ProjectService;
import com.zetyun.streamtau.manager.service.dto.AssetTypeInfo;
import com.zetyun.streamtau.manager.service.dto.ElementProfile;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ProjectService projectService;

    @Operation(summary = "Get the profile of the specified element.")
    @GetMapping("/profile/{element}")
    public ElementProfile profile(
        @PathVariable("element") String element
    ) {
        return profileService.get(element);
    }

    @Operation(summary = "Get the profile of an asset type in specified project.")
    @GetMapping("/projects/{projectId}/profile/{element}")
    public ElementProfile profileInProject(
        @PathVariable("projectId") String projectId,
        @PathVariable("element") String element
    ) {
        Long pid = projectService.mapId(projectId);
        return profileService.getInProject(pid, element);
    }

    @Operation(summary = "Get the asset type list in specified project.")
    @GetMapping("/assetTypes")
    public List<AssetTypeInfo> assetTypesInProject() throws IOException {
        return profileService.listAssetTypes();
    }
}
