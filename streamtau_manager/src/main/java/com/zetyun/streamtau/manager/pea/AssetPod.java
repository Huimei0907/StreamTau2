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

package com.zetyun.streamtau.manager.pea;

import com.zetyun.streamtau.manager.db.mapper.AssetMapper;
import com.zetyun.streamtau.manager.db.model.Asset;
import com.zetyun.streamtau.manager.pea.generic.Pod;
import lombok.AllArgsConstructor;

import java.io.IOException;

@AllArgsConstructor
public class AssetPod implements Pod<String, String, AssetPea> {
    private final AssetMapper assetMapper;
    private final Long projectId;

    public static AssetPea fromModel(Asset model) throws IOException {
        AssetPea pea = AssetPeaFactory.INS.make(model.getAssetType());
        pea.setId(model.getProjectAssetId());
        pea.setName(model.getAssetName());
        pea.setDescription(model.getAssetDescription());
        pea.mapFrom(model);
        return pea;
    }

    public static Asset toModel(AssetPea pea) throws IOException {
        Asset model = new Asset();
        model.setAssetType(pea.getType());
        model.setAssetName(pea.getName());
        model.setAssetDescription(pea.getDescription());
        model.setProjectAssetId(pea.getId());
        pea.mapTo(model);
        return model;
    }

    @Override
    public AssetPea load(String id) throws IOException {
        Asset model = assetMapper.findByIdInProject(projectId, id);
        return fromModel(model);
    }

    @Override
    public void save(AssetPea pea) throws IOException {
        Asset model = toModel(pea);
        assetMapper.insert(model);
    }
}