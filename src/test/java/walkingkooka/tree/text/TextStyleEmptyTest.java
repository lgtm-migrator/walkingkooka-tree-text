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
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.map.Maps;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContexts;
import walkingkooka.visit.Visiting;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public final class TextStyleEmptyTest extends TextStyleTestCase<TextStyleEmpty> {

    @Test
    public void testEmpty() {
        assertSame(TextStyle.EMPTY, TextStyle.with(Maps.empty()));
    }

    @Test
    public void testValue() {
        assertSame(TextStyle.EMPTY.value(), TextStyle.EMPTY.value());
    }

    // merge............................................................................................................

    @Test
    public void testMergeNotEmpty() {
        final TextStyle notEmpty = TextStyle.with(Maps.of(TextStylePropertyName.FONT_STYLE, FontStyle.ITALIC));
        assertSame(notEmpty,
                TextStyle.EMPTY.merge(notEmpty));
    }

    // replace...........................................................................................................

    @Test
    public void testReplaceTextPlaceholderNode() {
        this.replaceAndCheck2(this.placeholder("place1"));
    }

    @Test
    public void testReplaceTextPlaceholderNodeWithParent() {
        this.replaceAndCheck2(makeStyleNameParent(this.placeholder("child-place1")));
    }

    @Test
    public void testReplaceTextStyle() {
        this.replaceAndCheck2(TextNode.style(this.children()));
    }

    @Test
    public void testReplaceTextStyle2() {
        final TextNode node = TextNode.style(children());

        this.replaceAndCheck(TextStyle.EMPTY,
                node.setAttributes(Maps.of(TextStylePropertyName.FONT_STYLE, FontStyle.ITALIC)),
                node);
    }

    @Test
    public void testReplaceTextStyleNodeWithParent() {
        final TextNode textStyleNode = TextNode.style(this.children());

        this.replaceAndCheck(TextStyle.EMPTY,
                makeStyleNameParent(textStyleNode.setAttributes(Maps.of(TextStylePropertyName.FONT_STYLE, FontStyle.ITALIC))),
                makeStyleNameParent(textStyleNode));
    }

    @Test
    public void testReplaceTextStyleName() {
        this.replaceAndCheck2(TextNode.styleName(TextStyleName.with("style123")));
    }

    @Test
    public void testReplaceTextStyleWithParent() {
        final TextStyleNameNode styled = makeStyleNameParent(this.styleName("child-textStyle-456"));

        this.replaceAndCheck2(styled);
    }

    @Test
    public void testReplaceText() {
        this.replaceAndCheck2(TextNode.text("abc123"));
    }

    @Test
    public void testReplaceTextWithParent() {
        this.replaceAndCheck2(makeStyleNameParent(TextNode.text("child-abc123")));
    }

    private void replaceAndCheck2(final TextNode textNode) {
        this.replaceAndCheck(TextStyle.EMPTY, textNode);
    }

    // set..............................................................................................................

    @Test
    public void testSet() {
        final TextStylePropertyName<FontFamilyName> propertyName = TextStylePropertyName.FONT_FAMILY_NAME;
        final FontFamilyName familyName = FontFamilyName.with("Antiqua");

        this.setAndCheck(TextStyle.EMPTY,
                propertyName,
                familyName,
                TextStyle.with(Maps.of(propertyName, familyName)));
    }

    // TextStyleVisitor.................................................................................................

    @Test
    public void testAccept() {
        final StringBuilder b = new StringBuilder();
        final List<TextStyle> visited = Lists.array();

        final TextStyleEmpty textStyle = TextStyleEmpty.instance();

        new FakeTextStyleVisitor() {
            @Override
            protected Visiting startVisit(final TextStyle n) {
                b.append("1");
                visited.add(n);
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final TextStyle n) {
                b.append("2");
                visited.add(n);
            }
        }.accept(textStyle);

        assertEquals("12", b.toString());
        assertEquals(Lists.of(textStyle, textStyle),
                visited,
                "visited");
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(TextStyle.EMPTY, "");
    }

    @Test
    public void testFromEmptyJsonObject() {
        assertSame(TextStyle.EMPTY, TextStyle.unmarshall(JsonNode.object(), JsonNodeUnmarshallContexts.basic()));
    }

    @Override
    public TextStyleEmpty createObject() {
        return Cast.to(TextStyle.EMPTY);
    }

    @Override
    Class<TextStyleEmpty> textStyleType() {
        return TextStyleEmpty.class;
    }
}
