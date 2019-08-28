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

import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.FromJsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.ToJsonNodeContext;

public final class WordSpacing extends LengthTextStylePropertyValue {

    public static WordSpacing parse(final String text) {
        return with(Length.parse(text));
    }

    public static WordSpacing with(final Length<?> length) {
        check(length);
        length.normalOrPixelOrFail();

        return new WordSpacing(length);
    }

    private WordSpacing(final Length<?> length) {
        super(length);
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof WordSpacing;
    }

    // JsonNodeContext..................................................................................................

    static WordSpacing fromJsonNode(final JsonNode node,
                                    final FromJsonNodeContext context) {
        return with(context.fromJsonNode(node, Length.class));
    }

    JsonNode toJsonNode(final ToJsonNodeContext context) {
        return context.toJsonNode(this.length);
    }

    static {
        JsonNodeContext.register("word-spacing",
                WordSpacing::fromJsonNode,
                WordSpacing::toJsonNode,
                WordSpacing.class);
    }
}
