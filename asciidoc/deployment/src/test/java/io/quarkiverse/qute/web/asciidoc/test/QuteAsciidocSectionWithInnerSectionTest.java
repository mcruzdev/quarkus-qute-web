package io.quarkiverse.qute.web.asciidoc.test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;

import jakarta.inject.Inject;

import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.qute.Template;
import io.quarkus.test.QuarkusUnitTest;

public class QuteAsciidocSectionWithInnerSectionTest {

    @RegisterExtension
    static final QuarkusUnitTest quarkusApp = new QuarkusUnitTest()
            .withApplicationRoot(
                    app -> app.addAsResource(new StringAsset(
                            """
                                    <h1>Quarkus and Qute</h1>
                                    {#asciidoc}
                                    == Qute and Roq
                                    Here is a list:
                                    {#for item in items}
                                    * an {item} as a list item
                                    {/for}
                                    {/asciidoc}
                                    """),
                            "templates/foo.txt"));

    @Inject
    Template foo;

    @Test
    void shouldConvertWithInnerSection() {
        String result = foo.data("items", List.of("apple", "banana", "cherry"))
                .render();

        assertThat(result).contains("""
                <h1>Quarkus and Qute</h1>
                 <div class="sect1" id="_qute_and_roq">
                  <h2>Qute and Roq</h2>
                 <div class="sectionbody">
                 <div class="paragraph">
                Here is a list: <div class="ulist">
                 <ul>
                  <li>
                 <p>
                an apple as a list item
                 </p>
                  </li>
                  <li>
                 <p>
                an banana as a list item
                 </p>
                  </li>
                  <li>
                 <p>
                an cherry as a list item
                 </p>
                  </li>
                 </ul>
                 </div>
                 </div>
                 </div>
                 </div>
                """, result);
    }
}