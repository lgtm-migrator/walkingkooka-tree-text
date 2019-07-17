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
import walkingkooka.collect.map.Maps;

public final class PaddingTest extends BorderMarginPaddingTestCase<Padding> {

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(Padding.with(Direction.BOTTOM,
                TextStyle.with(Maps.of(TextStylePropertyName.PADDING_BOTTOM, Length.pixel(12.5),
                        TextStylePropertyName.BORDER_RIGHT_STYLE, BorderStyle.DOTTED))),
                "BOTTOM {border-right-textStyle=DOTTED, padding-bottom=12.5px}");
    }

    // helpers..........................................................................................................

    @Override
    Padding createBorderMarginPadding(final Direction direction, final TextStyle textStyle) {
        return direction.padding(textStyle);
    }

    @Override
    TextStylePropertyName<Length<?>> widthPropertyName(final Direction direction) {
        return direction.paddingPropertyName();
    }

    @Override
    public Class<Padding> type() {
        return Padding.class;
    }
}
