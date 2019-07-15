/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
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
 *
 */

package walkingkooka.tree.text;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.collect.set.Sets;
import walkingkooka.test.ConstantsTesting;
import walkingkooka.test.HashCodeEqualsDefinedTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonNodeException;

import java.util.Set;

public abstract class TextOverflowTestCase<T extends TextOverflow> extends TextStylePropertyValueTestCase3<TextOverflow>
        implements ConstantsTesting<TextOverflow>,
        HashCodeEqualsDefinedTesting<TextOverflow> {

    TextOverflowTestCase() {
        super();
    }

    @Test
    public final void testFromJsonArrayNodeFails() {
        this.fromJsonNodeFails(JsonNode.array(), JsonNodeException.class);
    }

    @Test
    public final void testFromJsonBooleanNodeFails() {
        this.fromJsonNodeFails(JsonNode.booleanNode(true), JsonNodeException.class);
    }

    @Test
    public final void testFromJsonNumberNodeFails() {
        this.fromJsonNodeFails(JsonNode.number(12.5), JsonNodeException.class);
    }

    @Test
    public final void testFromJsonStringNodeEmptyFails() {
        this.fromJsonNodeFails(JsonNode.string(""), JsonNodeException.class);
    }

    @Test
    public final void testFromJsonObjectNodeFails() {
        this.fromJsonNodeFails(JsonNode.object(), JsonNodeException.class);
    }

    @Override
    final TextStylePropertyName<TextOverflow> textStylePropertyName() {
        return TextStylePropertyName.TEXT_OVERFLOW;
    }

    // HasJsonNodeTesting...............................................................................................

    @Override
    public TextOverflow fromJsonNode(final JsonNode from) {
        return TextOverflow.fromJsonNode(from);
    }

    // ClassTesting.....................................................................................................

    @Override
    public final Class<TextOverflow> type() {
        return Cast.to(this.textOverflowType());
    }

    abstract Class<T> textOverflowType();

    // ConstantTesting..................................................................................................

    @Override
    public final Set<TextOverflow> intentionalDuplicateConstants() {
        return Sets.empty();
    }

    // HashCodeEqualsDefinedTesting.....................................................................................

    @Override
    public final TextOverflow createObject() {
        return this.createTextStylePropertyValue();
    }
}
