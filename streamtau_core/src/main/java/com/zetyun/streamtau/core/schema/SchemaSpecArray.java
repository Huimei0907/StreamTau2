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

package com.zetyun.streamtau.core.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.zetyun.streamtau.runtime.schema.RtSchemaNode;
import com.zetyun.streamtau.runtime.schema.RtSchemaTuple;
import com.zetyun.streamtau.runtime.schema.RtSchemaTypes;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@JsonTypeName("array")
@JsonPropertyOrder({"type", "items"})
public final class SchemaSpecArray extends SchemaSpec {
    @JsonProperty("items")
    @Getter
    private SchemaArrayItems items;

    @JsonProperty("additionalItems")
    @Getter
    private Boolean additionalItems;

    @Override
    public @NotNull RtSchemaNode createRtNode() {
        if (additionalItems == null || additionalItems) {
            if (items == null) {
                return new RtSchemaNode(RtSchemaTypes.LIST);
            }
            Types type = items.getType();
            if (type == null) {
                return new RtSchemaNode(RtSchemaTypes.LIST);
            }
            switch (type) {
                case INTEGER:
                    return new RtSchemaNode(RtSchemaTypes.INT_ARRAY);
                case NUMBER:
                    return new RtSchemaNode(RtSchemaTypes.REAL_ARRAY);
                case STRING:
                    return new RtSchemaNode(RtSchemaTypes.STR_ARRAY);
                case BOOLEAN:
                    return new RtSchemaNode(RtSchemaTypes.BOOL_ARRAY);
                default:
                    throw new IllegalArgumentException("Invalid schema type \"" + type + "\".");
            }
        }
        SchemaSpec[] specs = items.getSpecs();
        RtSchemaNode[] children = new RtSchemaNode[specs.length];
        for (int i = 0; i < specs.length; i++) {
            children[i] = specs[i].createRtNode();
        }
        RtSchemaTuple tuple = new RtSchemaTuple();
        tuple.setChildren(children);
        return tuple;
    }
}