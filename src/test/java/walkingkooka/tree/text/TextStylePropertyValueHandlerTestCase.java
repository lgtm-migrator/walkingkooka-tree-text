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
import walkingkooka.ToStringTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.TypeNameTesting;
import walkingkooka.text.CharSequences;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContexts;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContexts;

import java.math.MathContext;

import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class TextStylePropertyValueHandlerTestCase<P extends TextStylePropertyValueHandler<T>, T> extends TextNodeTestCase<P>
        implements ToStringTesting<P>,
        TypeNameTesting<P> {

    TextStylePropertyValueHandlerTestCase() {
        super();
    }

    @Test
    public final void testCheckNullValueFails() {
        this.checkFails(null,
                "Property " + this.propertyName().inQuotes() + " missing value");
    }

    @Test
    public final void testCheck() {
        this.check(this.propertyValue());
    }

    @Test
    public final void testRoundtripJson() {
        final T value = this.propertyValue();
        final P handler = this.handler();

        final JsonNode json = handler.marshall(value, this.marshallContext());

        this.checkEquals(value,
                handler.unmarshall(json, this.propertyName(), this.unmarshallContext()),
                () -> "value " + CharSequences.quoteIfChars(value) + " to json " + json);
    }

    final void check(final Object value) {
        final TextStylePropertyName<?> propertyName = this.propertyName();
        this.handler().check(value, propertyName);
        propertyName.check(value);
    }

    final void checkFails(final Object value, final String message) {
        final TextStylePropertyValueException thrown = assertThrows(TextStylePropertyValueException.class, () -> this.check(value));
        this.checkEquals(message, thrown.getMessage(), "message");

        final TextStylePropertyValueException thrown2 = assertThrows(TextStylePropertyValueException.class, () -> this.propertyName().check(value));
        this.checkEquals(message, thrown2.getMessage(), "message");
    }

    final void unmarshallAndCheck(final JsonNode node, final T value) {
        this.checkEquals(value,
                this.handler().unmarshall(node, this.propertyName(), this.unmarshallContext()),
                () -> "from JsonNode " + node);
    }

    final void marshallAndCheck(final T value, final JsonNode node) {
        this.checkEquals(node,
                this.handler().marshall(value, this.marshallContext()),
                () -> "marshall " + CharSequences.quoteIfChars(value));
    }

    // helper...........................................................................................................

    abstract P handler();

    abstract TextStylePropertyName<T> propertyName();

    abstract T propertyValue();

    abstract String propertyValueType();

    final JsonNodeUnmarshallContext unmarshallContext() {
        return JsonNodeUnmarshallContexts.basic(
                ExpressionNumberKind.DEFAULT,
                MathContext.DECIMAL32
        );
    }

    final JsonNodeMarshallContext marshallContext() {
        return JsonNodeMarshallContexts.basic();
    }

    final JsonNode marshall(final Object value) {
        return this.marshallContext().marshall(value);
    }

    // ClassTesting.....................................................................................................

    @Override
    public final JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }

    // TypeNameTesting...................................................................................................

    @Override
    public final String typeNamePrefix() {
        return TextStylePropertyValueHandler.class.getSimpleName();
    }
}
