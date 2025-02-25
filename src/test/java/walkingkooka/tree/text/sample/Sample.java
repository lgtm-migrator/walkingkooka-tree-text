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

package walkingkooka.tree.text.sample;

import walkingkooka.collect.map.Maps;
import walkingkooka.color.Color;
import walkingkooka.text.Indentation;
import walkingkooka.text.LineEnding;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.text.printer.Printers;
import walkingkooka.tree.text.FakeTextNodeVisitor;
import walkingkooka.tree.text.Text;
import walkingkooka.tree.text.TextNode;
import walkingkooka.tree.text.TextStyleName;
import walkingkooka.tree.text.TextStyleNameNode;
import walkingkooka.tree.text.TextStyleNode;
import walkingkooka.tree.text.TextStylePropertyName;
import walkingkooka.visit.Visiting;

public final class Sample {
    public static void main(final String[] args) {
        final TextNode node = TextNode.styleName(TextStyleName.with("HTML"))
                .appendChild(TextNode.styleName(TextStyleName.with("head")).appendChild(TextNode.styleName(TextStyleName.with("TITLE")).appendChild(TextNode.text("title123"))))
                .appendChild(TextNode.styleName(TextStyleName.with("BODY"))
                        .appendChild(TextNode.text("before"))
                        .appendChild(TextNode.text("something gray").setAttributes(Maps.of(TextStylePropertyName.COLOR, Color.parse("#345"))).parentOrFail())
                        .appendChild(TextNode.text("after"))
                );

        final StringBuilder html = new StringBuilder();
        final LineEnding eol = LineEnding.SYSTEM;
        final IndentingPrinter printer = Printers.stringBuilder(html, eol)
                .indenting(Indentation.with("  "));

        // very simple html printer
        new FakeTextNodeVisitor(){
            @Override
            protected Visiting startVisit(final TextNode node) {
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final TextNode node) {
            }

            @Override
            protected Visiting startVisit(final TextStyleNode node) {
                printer.lineStart();
                printer.print("<SPAN style=\"");

                node.attributes()
                        .forEach((p, v) -> {
                            printer.print(p + ": " + v + ";");
                        });

                printer.print("\">" + eol);
                printer.indent();
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final TextStyleNode node) {
                printer.outdent();
                printer.lineStart();
                printer.print("</SPAN>" + eol);
            }

            @Override
            protected Visiting startVisit(final TextStyleNameNode node) {
                this.beginElement(node.styleName().value());
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final TextStyleNameNode node) {
                this.endElement(node.styleName().value());
            }

            @Override
            protected void visit(final Text node) {
                printer.print(node.value());
            }

            private void beginElement(final String element) {
                printer.lineStart();
                printer.print("<" + element + ">" + eol);
                printer.indent();
            }

            private void endElement(final String element) {
                printer.outdent();
                printer.lineStart();
                printer.print("</" + element + ">" + eol);
            }
        }.accept(node);

        // <pre>
        // <HTML>
        //   <head>
        //     <TITLE>
        //       title123
        //     </TITLE>
        //   </head>
        //   <BODY>
        //     before
        //     <SPAN style="color: #334455;">
        //       something gray
        //     </SPAN>
        //     after
        //   </BODY>
        // </HTML>
        // </pre>
        System.out.println(html);
    }
}
